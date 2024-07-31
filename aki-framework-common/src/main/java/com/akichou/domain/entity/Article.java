package com.akichou.domain.entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Article Entity
 *
 * @author Aki Chou
 * @date 2024/06/19 Wed.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "aki_article")
public class Article{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String summary;

    private Long categoryId;

    // Only exists in Class Article, and doesn't exist in table column.
    @Transient
    private String categoryName;

    private String thumbnail;

    private String isTop;

    private String status;

    private Long viewCount;

    private String isComment;

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;

    private Integer delFlag;

    public Article(Long id, Long viewCount) {
        this.id = id ;
        this.viewCount = viewCount;
    }
}

