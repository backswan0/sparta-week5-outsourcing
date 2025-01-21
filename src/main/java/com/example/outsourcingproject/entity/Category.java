package com.example.outsourcingproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Table(name = "categories")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Category {

    @Comment("카테고리 식별자")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private Long id;

    @Comment("카테고리 이름")
    @Column(
        name = "name",
        nullable = false
    )
    private String name;

    protected Category() {
    }

    public Category(
        String name
    ) {
        this.name = name;
    }
}