package com.ataoglan.todo_app.domain.entity;

import com.ataoglan.todo_app.domain.enums.TaskStatusEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document
@Builder
public class TaskEntity {

    @Id
    private String id;

    @Field
    private String userId;

    @Field
    private String title;

    @Field
    private String description;

    @Field
    @Enumerated(EnumType.STRING)
    private TaskStatusEnum completed;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}

