package com.jforce.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name; //Enum Role Pre defined
		
	public Role() {
	}

//	public Role(Integer id, ERole name) {
//		this.id = id;
//		this.name = name;
//	}

	public Role(ERole name) {
		this.name = name;
	}

//	@Override
//	public String toString() {
//		return "Role [id=" + id + ", name=" + name + "]";
//	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ERole getName() {
		return name;
	}

	public void setName(ERole name) {
		this.name = name;
	}
	
}
