package com.geopokrovskiy.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table("users")
public class UserEntity {
    @Id
    private Long id;
    @Column("username")
    private String username;
    @Column("password")
    private String password;
    @Column("api_key")
    private String apiKey;
    @Column("status")
    private Status status;
    @Column("created_at")
    private LocalDateTime createdAt;
    @Column("updated_at")
    private LocalDateTime updatedAt;
    @Column("is_blocked")
    private boolean isBlocked;
    @ToString.Include(name = "password")
    private String maskPassword() {
        return "********";
    }
}
