package com.ataoglan.todo_app.domain.entity;

import com.ataoglan.todo_app.domain.enums.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Document
public class UserEntity {
    @Id
    private String id = UUID.randomUUID().toString();
    @Field
    private String username;
    @Field
    private String password;
    @Field
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @CreationTimestamp
    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    @UpdateTimestamp
    private LocalDateTime lastModifiedDate;
}
