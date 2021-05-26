package com.project.dlvery.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Table(name="productCategory")
public class ProductCategory {
	
	@Id
	@Column(name="productCategory_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	private String name;

	@OneToMany(mappedBy = "productCategory", fetch = FetchType.LAZY)
    private Set<Inventory> inventory;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
