package com.softcaribbean.hulkstore.api.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class UserEntity implements Serializable {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_at", nullable = false)
    private ZonedDateTime createdAt;

    @NonNull
    @Column(name = "email", nullable = false, length = 150, unique = true)
    private String email;

    @NonNull
    @Column(name = "password", nullable = false, length = 300)
    private String pass;

    @NonNull
    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @NonNull
    @Column(name = "last_name", nullable = false, length = 150)
    private String lastName;

    @Column(name = "active", nullable = false)
    private boolean active;

    @PrePersist
    private void prePersist() {
        this.setCreatedAt(ZonedDateTime.now());
    }
}
