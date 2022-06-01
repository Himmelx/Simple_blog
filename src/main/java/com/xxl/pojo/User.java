package com.xxl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xxl
 * @create 2021-11-25 14:18
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    int userId;
    String userName;
    String userPassword;
    String userJpg;
    String userSign;
    String qq;
    String wechat;
}
