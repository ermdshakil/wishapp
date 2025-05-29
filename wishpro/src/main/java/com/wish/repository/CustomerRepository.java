package com.wish.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wish.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	// Customers with matching birthday
	List<Customer> findByDob(LocalDate date);

	// Customers with matching anniversary
	List<Customer> findByAnniversary(LocalDate date);

	// Customers with either birthday or anniversary today
	@Query("SELECT c FROM Customer c WHERE c.dob = :date OR c.anniversary = :date")
	List<Customer> findUpcomingEvents(@Param("date") LocalDate date);

	@Query("SELECT c FROM Customer c WHERE FUNCTION('MONTH', c.dob) = :month AND FUNCTION('DAY', c.dob) = :day")
	List<Customer> findByDobMonthAndDay(@Param("month") int month, @Param("day") int day);

	@Query("SELECT c FROM Customer c WHERE FUNCTION('MONTH', c.anniversary) = :month AND FUNCTION('DAY', c.anniversary) = :day")
	List<Customer> findByAnniversaryMonthAndDay(@Param("month") int month, @Param("day") int day);
}
