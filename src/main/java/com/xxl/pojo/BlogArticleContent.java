package com.xxl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xxl
 * @create 2021-11-26 9:40
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogArticleContent {
    int contentId;
    int articleId;
    String articleContent;
}
