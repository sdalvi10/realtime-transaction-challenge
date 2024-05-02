package dev.codescreen.persist;

import dev.codescreen.models.MessageIdEntity;
import org.springframework.data.repository.CrudRepository;

public interface MessageIdEntityRepository extends CrudRepository<MessageIdEntity, String> {
    MessageIdEntity findByMessageId(String messageId);
}
