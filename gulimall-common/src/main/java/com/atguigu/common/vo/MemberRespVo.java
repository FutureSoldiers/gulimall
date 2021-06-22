package com.atguigu.common.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author FutureSoldiers
 * @create 2020-11-23 14:50
 */
@ToString
@Data
public class MemberRespVo implements Serializable {
    private Long id;
    /**
     * ??Ա?ȼ?id
     */
    private Long levelId;
    /**
     * ?û???
     */
    private String username;
    /**
     * ???
     */
    private String password;
    /**
     * ?ǳ
     */
    private String nickname;
    /**
     * ?ֻ????
     */
    private String mobile;
    /**
     * ???
     */
    private String email;
    /**
     * ͷ?
     */
    private String header;
    /**
     * ?Ա
     */
    private Integer gender;
    /**
     * ???
     */
    private Date birth;
    /**
     * ???ڳ??
     */
    private String city;
    /**
     * ְҵ
     */
    private String job;
    /**
     * ????ǩ??
     */
    private String sign;
    /**
     * ?û???Դ
     */
    private Integer sourceType;
    /**
     * ???
     */
    private Integer integration;
    /**
     * ?ɳ?ֵ
     */
    private Integer growth;
    /**
     * ????״̬
     */
    private Integer status;
    /**
     * ע??ʱ?
     */
    private Date createTime;

    private String socialUid;
    private String accessToken;
    private Long expiresIn;
}
