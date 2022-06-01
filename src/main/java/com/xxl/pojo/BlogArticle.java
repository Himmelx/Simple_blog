package com.xxl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author xxl
 * @create 2021-11-26 9:40
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogArticle {
    int articleId;
    int userId;
    String articleTitle;
    String articleType;
    String articleAbstract;
    int love;
    int view;
    Date writeDate;
    String userName;
}
