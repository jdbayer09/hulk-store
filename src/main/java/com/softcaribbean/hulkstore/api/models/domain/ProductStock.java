package com.softcaribbean.hulkstore.api.models.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ProductStock implements Serializable {

    @Serial
    private static final long serialVersionUID = -8705472030766018744L;

    private Long id;
    private User user;
    private ZonedDateTime createdAt;
    private int quantity;
}