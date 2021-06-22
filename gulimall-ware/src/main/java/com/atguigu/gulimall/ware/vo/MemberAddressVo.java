package com.atguigu.gulimall.ware.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author FutureSoldiers
 * @create 2020-12-07 15:07
 */
@Data
public class MemberAddressVo {
    @TableId
    private Long id;
    /**
     * member_id
     */
    private Long memberId;
    /**
     * ?ջ???????
     */
    private String name;
    /**
     * ?绰
     */
    private String phone;
    /**
     * ???????
     */
    private String postCode;
    /**
     * ʡ??/ֱϽ?
     */
    private String province;
    /**
     * ???
     */
    private String city;
    /**
     * ??
     */
    private String region;
    /**
     * ??ϸ??ַ(?ֵ?)
     */
    private String detailAddress;
    /**
     * ʡ???????
     */
    private String areacode;
    /**
     * ?Ƿ?Ĭ?
     */
    private Integer defaultStatus;
}
