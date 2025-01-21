package com.example.outsourcingproject.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Comment("생성일")
    @CreatedDate
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(
        name = "created_at",
        nullable = false,
        updatable = false,
        columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Comment("수정일")
    @LastModifiedDate
    @Column(
        name = "updated_at",
        nullable = false,
        columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @Comment("삭제 여부")
    @Column(
        name = "is_deleted",
        columnDefinition = "TINYINT(1) DEFAULT 0"
    )
    private Integer isDeleted = 0;

    @Comment("삭제일")
    @Column(
        name = "deleted_at",
        columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime deletedAt;

    protected BaseEntity() {
    }

    public void markAsDeleted() {
        this.isDeleted = 1;
        this.deletedAt = LocalDateTime.now();
    }
}