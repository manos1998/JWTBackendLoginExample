package com.jforce.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Loggins {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Integer loggerid;

	private LocalDate date;

	private LocalTime login;

	private LocalTime logout;

	public Long getId() {
		return id;
	}
	
	public Integer getLoggerid() {
		return loggerid;
	}

	public void setLoggerid(Integer loggerid) {
		this.loggerid = loggerid;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getLogin() {
		return login;
	}

	public void setLogin(LocalTime login) {
		this.login = login;
	}

	public LocalTime getLogout() {
		return logout;
	}

	public void setLogout(LocalTime logout) {
		this.logout = logout;
	}
	
	public Loggins(Integer loggerid, LocalDate date, LocalTime loggin) {
		this.loggerid = loggerid;
		this.date = date;
		this.login = loggin;
	}

	public Loggins() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Loggins other = (Loggins) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}