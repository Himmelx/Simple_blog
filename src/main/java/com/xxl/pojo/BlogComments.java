package com.xxl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author xxl
 * @create 2021-11-26 9:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogComments {
    int commentId;
    int articleId;
    String commentContent;
    int userId;
    Date commentDate;
    String userJpg;
    String userName;
}
