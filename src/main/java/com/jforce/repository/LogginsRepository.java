package com.jforce.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jforce.entity.Loggins;

@Repository
public interface LogginsRepository extends JpaRepository<Loggins, Long> {

	@Query(value = "SELECT * FROM loggins WHERE loggins.date = CURDATE() and loggins.loggerid = ?1 ;", nativeQuery = true)
	List<Loggins> findIfLoginToday(Integer id);
	
	@Query(value = " SELECT * FROM loggins WHERE loggins.loggerid = ?1 ;", nativeQuery = true)
	List<Loggins> findByLoggins(Integer id);
		
	@Query(value = "SELECT * FROM loggins WHERE loggins.loggerid = ?1 and loggins.date = ?2 ;", nativeQuery = true)
	List<Loggins> findByDate(Integer id, LocalDate date);
}