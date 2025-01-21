package com.example.outsourcingproject.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "mapping_menu_category")
@Getter
public class MappingMenuCategory {

    @Comment("메뉴 중간 테이블 식별자")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "menu_category_id",
        nullable = false
    )
    private MenuCategory menuCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "menu_id",
        nullable = false
    )
    private Menu menu;

    protected MappingMenuCategory() {
    }

    public MappingMenuCategory(
        MenuCategory menuCategory,
        Menu menu
    ) {
        this.menuCategory = menuCategory;
        this.menu = menu;
    }
}