package com.atguigu.gulimall.member.exception;

/**
 * @author FutureSoldiers
 * @create 2020-11-21 10:20
 */
public class UserNameExistException extends RuntimeException {

    public UserNameExistException() {
        super("用户名存在");
    }
}
