package com.akichou.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Link Entity
 *
 * @author Aki Chou
 * @date 2024/06/19 Wed.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "aki_link")
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String name ;

    private String logo ;

    private String description ;

    private String address ;

    private String status ;

    private Long createBy ;

    private Date createTime ;

    private Long updateBy ;

    private Date updateTime ;

    private Integer delFlag ;
}
