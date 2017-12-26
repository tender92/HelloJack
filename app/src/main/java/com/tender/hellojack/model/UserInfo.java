package com.tender.hellojack.model;

import com.tender.hellojack.model.enums.GenderEnum;

import java.io.Serializable;

/**
 * Created by boyu on 2017/12/25.
 */

public class UserInfo implements Serializable {
    public String account;//账号
    public String name;//昵称
    public String avatar;//头像路径
    public String signatur;//签名
    public GenderEnum gender;//性别
    public String email;//邮箱
    public String birthDay;//生日 格式yyyy-MM-dd
    public String mobile;//手机号
    public String address;//地址
    public String region;//地区
}
