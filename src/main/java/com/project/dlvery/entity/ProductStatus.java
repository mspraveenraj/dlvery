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
@Table(name="productStatus")
public class ProductStatus {
	
	@Id
	@Column(name="productStatus_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	private String status;

	@OneToMany(mappedBy = "productStatus", fetch = FetchType.LAZY)
    private Set<Inventory> inventory;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
