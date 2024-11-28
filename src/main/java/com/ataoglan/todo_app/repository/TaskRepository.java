package com.ataoglan.todo_app.repository;

import com.ataoglan.todo_app.domain.entity.TaskEntity;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CouchbaseRepository<TaskEntity, String> {
    List<TaskEntity> findByUserId(String userId);
}
