package dev.codescreen.persist;

import dev.codescreen.models.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserEntityRepository extends CrudRepository<UserEntity, String> {
    UserEntity findByUserId(String userId);
}
