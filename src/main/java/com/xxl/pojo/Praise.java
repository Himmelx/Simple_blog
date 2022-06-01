package com.xxl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author xxl
 * @create 2021-12-09 11:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Praise {
    int praiseId;
    int articleId;
    int userId;
}
