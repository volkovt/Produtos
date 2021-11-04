package com.volkovt.administrativo.entity;

import java.math.BigDecimal;

import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.*;

@Entity
@Table(name = "PRODUCT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
	private String id;
	
    @Column(name = "NAME")
    @NotEmpty(message = "{field.name.required}")
	private String name;
    
    @Column(name = "DESCRIPTION")
    @NotEmpty(message = "{field.description.required}")
	private String description;
    
    @Column(name = "PRICE")
    @NotNull(message = "{field.price.required}")
	private BigDecimal price;

}
