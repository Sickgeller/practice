package com.toy.practice.category.model;

import com.toy.practice.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "BOARD_CATEGORY"
)
@SequenceGenerator(
        name = "CATEGORY_SEQ_GENERATOR",
        sequenceName = "CATEGORY_SEQ"
)
public class BoardCategory extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_CATEGORY_ID",nullable = false)
    private Long categoryId;

    @Column(name = "BOARD_CATEGORY_NAME", nullable = false, unique = true, length = 50)
    private String categoryName;
}
