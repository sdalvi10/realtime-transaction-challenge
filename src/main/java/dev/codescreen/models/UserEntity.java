package dev.codescreen.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class UserEntity {
    @Id
    private String userId;
    private String balance;

    public UserEntity() {
    }
}
