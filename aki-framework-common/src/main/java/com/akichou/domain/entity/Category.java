package com.akichou.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Category Entity
 *
 * @author Aki Chou
 * @date 2024/06/19 Wed.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "aki_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String name ;

    private Long pid ;

    private String description ;

    private String status ;

    private Long createBy ;

    private Date createTime ;

    private Long updateBy ;

    private Date updateTime ;

    private Integer delFlag ;
}
