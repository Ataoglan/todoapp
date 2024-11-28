package com.ataoglan.todo_app.repository;

import com.ataoglan.todo_app.domain.entity.UserEntity;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CouchbaseRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(String username);
}
