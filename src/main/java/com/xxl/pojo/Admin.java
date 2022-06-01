package com.xxl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xxl
 * @create 2021-11-25 14:18
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    int adminId;
    String adminName;
    String adminPassword;
}
