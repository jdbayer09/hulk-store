package com.softcaribbean.hulkstore.api.models.domain;

import com.softcaribbean.hulkstore.api.models.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = -8966571657721276416L;

    private Long id;
    private ProductType type;
    private String name;
    private Double price;
    private int totalPieces;
    private ZonedDateTime createdAt;
    private List<ProductStock> transactions;
}
