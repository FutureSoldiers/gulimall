package com.atguigu.common.exception;

/**
 * @author FutureSoldiers
 * @create 2021-03-16 15:59
 */
public class NoStockException extends RuntimeException {

    private Long skuId;
    public NoStockException(Long skuId){
        super("商品id:"+skuId+"没有足够的库存了");
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }
}
