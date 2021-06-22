package com.atguigu.gulimall.seckill.service.impl;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.atguigu.common.to.mq.SeckillOrderTo;
import com.atguigu.common.utils.R;
import com.atguigu.common.vo.MemberRespVo;
import com.atguigu.gulimall.seckill.feign.CouponFeignService;
import com.atguigu.gulimall.seckill.feign.ProductFeignService;
import com.atguigu.gulimall.seckill.interceptor.LoginUserInterceptor;
import com.atguigu.gulimall.seckill.service.SeckillService;
import com.atguigu.gulimall.seckill.to.SeckillSkuRedisTo;
import com.atguigu.gulimall.seckill.vo.SeckillSessionWithSkus;
import com.atguigu.gulimall.seckill.vo.SeckillSkuVo;
import com.atguigu.gulimall.seckill.vo.SkuInfoVo;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author FutureSoldiers
 * @create 2021-04-01 16:40
 */
@Slf4j
@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private CouponFeignService couponFeignService;

    @Autowired
    private ProductFeignService productFeignService;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    private final String SESSIONS_CACHE_PREFIX = "seckill:sessions";
    private final String SKUKILL_CACHE_PREFIX = "seckill:skus";
    private final String SKU_STOCK_SEMAPHORE = "seckill:stock:";//+商品随机码

    @Override
    public void uploadSeckillSkuLatest3Days() {
        //1、扫描最近3天需要参加秒杀的活动
        R session = couponFeignService.getLates3DaySession();
        if (session.getCode() == 0) {
            //上架商品
            List<SeckillSessionWithSkus> sessionData = session.getData(new TypeReference<List<SeckillSessionWithSkus>>() {
            });
            //缓存到redis
            //1、缓存活动信息
            saveSessionInfos(sessionData);

            //2、缓存活动的关联商品信息
            saveSessionSkuInfos(sessionData);
        }
    }

    public List<SeckillSkuRedisTo> blockHandler(BlockException e){
        log.error("getCurrentSeckillSkusResource被限流了;"+e.getMessage());
        return null;
    }

    /**
     * 返回当前时间可以参与的秒杀商品信息
     *
     * blockHandler ="blockHandler": 针对指定的方法被执行的时候调用
     * fallback: 针对出现异常 兜底的方法
     *    fallbackClass: 表示你写在了哪个类中(最好写在本类)
     * @return
     */
    @SentinelResource(value = "getCurrentSeckillSkusResource",blockHandler ="blockHandler")
    @Override
    public List<SeckillSkuRedisTo> getCurrentSeckillSkus() {
        //1、确定当前时间属于哪个秒杀场次。
        long time = new Date().getTime();

        try (Entry entry = SphU.entry("seckillSkus")){
            Set<String> keys = redisTemplate.keys(SESSIONS_CACHE_PREFIX + "*");
            for (String key : keys) {
                String replace = key.replace(SESSIONS_CACHE_PREFIX, "");
                String[] split = replace.split("_");
                Long start = Long.parseLong(split[0]);
                Long end = Long.parseLong(split[1]);
                if (time >= start && time <= end) {
                    //2、获取这个秒杀场次需要的所有商品信息
                    List<String> range = redisTemplate.opsForList().range(key, -100, 100);
                    BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);

                    List<String> list = hashOps.multiGet(range);
                    if (list != null) {
                        List<SeckillSkuRedisTo> collect = list.stream().map(item -> {
                            SeckillSkuRedisTo redis = JSON.parseObject((String) item, SeckillSkuRedisTo.class);
                            // redis.setRandomCode(null); 当前秒杀开始就需要随机码
                            return redis;
                        }).collect(Collectors.toList());
                        return collect;
                    }
                    break;

                }

            }

        }catch (BlockException e){
            log.error("资源被限流:"+e.getMessage());
        }


        //2、获取这个秒杀场次需要的所有商品信息

        return null;
    }

    @Override
    public SeckillSkuRedisTo getSkuSeckillInfo(Long skuId) {

        //1、找到所有需要参与秒杀的商品的key
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);

        Set<String> keys = hashOps.keys();
        if (keys != null && keys.size() > 0) {
            String regx = "\\d_" + skuId;
            for (String key : keys) {
                if (Pattern.matches(regx, key)) {
                    String json = hashOps.get(key);
                    SeckillSkuRedisTo skuRedisTo = JSON.parseObject(json, SeckillSkuRedisTo.class);

                    //随机码
                    long current = new Date().getTime();
                    if (current >= skuRedisTo.getStartTime() && current <= skuRedisTo.getEndTime()) {

                    } else {
                        skuRedisTo.setRandomCode(null);
                    }
                    return skuRedisTo;
                }
            }
        }
        return null;
    }

    //TODO 上架秒杀商品的时候,每一个数据都有过期时间.
    //TODO 秒杀后续的流程,简化了收货地址等信息
    @Override
    public String kill(String killId, String key, Integer num) {

        MemberRespVo memberRespVo = LoginUserInterceptor.loginUser.get();
        //1、获取当前秒杀商品的详细信息
        BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);

        String json = hashOps.get(killId);
        if (StringUtils.isEmpty(json)) {
            return null;
        } else {
            SeckillSkuRedisTo skuRedisTo = JSON.parseObject(json, SeckillSkuRedisTo.class);
            //校验合法性
            Long startTime = skuRedisTo.getStartTime();
            Long endTime = skuRedisTo.getEndTime();
            long time = new Date().getTime();

            long ttl = endTime - startTime;
            //1、校验时间的合法性
            if (time >= startTime && time <= endTime) {
                //2、校验随机码和商品id
                String randomCode = skuRedisTo.getRandomCode();
                String skuId = skuRedisTo.getPromotionSessionId() + "_" + skuRedisTo.getSkuId();
                if (randomCode.equals(key) && killId.equals(skuId)) {
                    //3、验证购物数量是否合理
                    if (num < skuRedisTo.getSeckillLimit()) {
                        //4、验证这个人是否已经购买过了。幂等性;如果指套秒杀成功就去redis占位。 userId_sessionId_skuId
                        //SETNX
                        String redisKey = memberRespVo.getId() + "_" + skuId;
                        //设置自动过期
                        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(redisKey, num.toString(), ttl, TimeUnit.MILLISECONDS);
                        if (aBoolean) {
                            //占位成功说明从来没有买过
                            RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + randomCode);
                            boolean b = semaphore.tryAcquire(num);
                            if (b) {
                                //秒杀成功;
                                //快速下单.发送MQ消息 10ms
                                String timeId = IdWorker.getTimeId();
                                SeckillOrderTo orderTo = new SeckillOrderTo();
                                orderTo.setOrderSn(timeId);
                                orderTo.setMemberId(memberRespVo.getId());
                                orderTo.setNum(num);
                                orderTo.setPromotionSessionId(skuRedisTo.getPromotionSessionId());
                                orderTo.setSkuId(skuRedisTo.getSkuId());
                                orderTo.setSeckillPrice(skuRedisTo.getSeckillPrice());
                                rabbitTemplate.convertAndSend("order-event-exchange", "order.seckill.order", orderTo);
                                return timeId;
                            }
                            return null;
                        } else {
                            //说明已经买过了
                            return null;
                        }
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }

        return null;
    }

    private void saveSessionInfos(List<SeckillSessionWithSkus> sessionWithSkus) {
        sessionWithSkus.stream().forEach(session -> {
            Long startTime = session.getStartTime().getTime();
            Long endTime = session.getEndTime().getTime();
            String key = startTime + "_" + endTime;
            List<String> collect = session.getRelationSkus().stream().map(item -> item.getPromotionSessionId() + "_" + item.getSkuId().toString()).collect(Collectors.toList());

            //缓存活动信息
            redisTemplate.opsForList().leftPushAll(key, collect);


        });
    }

    private void saveSessionSkuInfos(List<SeckillSessionWithSkus> sessions) {

        if(sessions!=null)
        sessions.stream().forEach(session -> {
            //准备hash操作
            BoundHashOperations ops = redisTemplate.boundHashOps(SKUKILL_CACHE_PREFIX);
            session.getRelationSkus().stream().forEach(seckillSkuVo -> {
                //4、随机码? seckill?skuId=1&key=dadlajldj
                String token = UUID.randomUUID().toString().replace("-", "");

                if (!ops.hasKey(seckillSkuVo.getPromotionSessionId().toString() + "_" + seckillSkuVo.getSkuId().toString())) {
                    //缓存商品
                    SeckillSkuRedisTo redisTo = new SeckillSkuRedisTo();
                    //1、sku的基本数据
                    R r = productFeignService.getSkuInfo(seckillSkuVo.getSkuId());
                    if (r.getCode() == 0) {
                        SkuInfoVo skuInfo = r.getData("skuInfo", new TypeReference<SkuInfoVo>() {
                        });
                        redisTo.setSkuInfoVo(skuInfo);
                    }
                    //2、sku的秒杀信息
                    BeanUtils.copyProperties(seckillSkuVo, redisTo);

                    //3、设置上当前商品的秒杀时间信息
                    redisTo.setStartTime(session.getStartTime().getTime());
                    redisTo.setEndTime(session.getEndTime().getTime());

                    redisTo.setRandomCode(token);
                    String string = JSON.toJSONString(redisTo);
                    ops.put(seckillSkuVo.getPromotionSessionId().toString() + "_" + seckillSkuVo.getSkuId().toString(), string);

                    //如果当前这个场次的商品的库存信息已经上架就不需要上架
                    //5、使用库存作为分布式的信号量 限流;
                    RSemaphore semaphore = redissonClient.getSemaphore(SKU_STOCK_SEMAPHORE + token);
                    //商品可以秒杀的数量作为信号量
                    semaphore.trySetPermits(seckillSkuVo.getSeckillCount());
                }


            });
        });
    }
}
