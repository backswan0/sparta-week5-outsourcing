package com.example.outsourcingproject.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Table(name = "menu_categories")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class MenuCategory {

    @Comment("메뉴용 카테고리 식별자")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

    @Comment("이름")
    @Column(
        name = "name",
        nullable = false
    )
    private String name;

    protected MenuCategory() {
    }

    public MenuCategory(
        String name
    ) {
        this.name = name;
    }
}
