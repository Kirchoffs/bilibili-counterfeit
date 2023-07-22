package com.syh.bilibili.domain;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfo {
    private Long id;

    private Long userId;

    private String nickname;

    private String avatar;

    private String sign;

    private String gender;

    private String birthday;

    private Date createTime;

    private Date updateTime;
}
