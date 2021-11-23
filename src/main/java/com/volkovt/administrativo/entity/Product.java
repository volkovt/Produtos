package com.volkovt.administrativo.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_product")
public class Product implements Serializable {
	private static final long serialVersionUID = 7306093162287354748L;

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
