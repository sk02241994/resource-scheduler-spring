package com.office.resourcescheduler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.office.resourcescheduler.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, CrudRepository<Reservation, Long> {

	@Query(value = "SELECT rs FROM Reservation rs where rs.resourceId = :resourceId")
	List<Reservation> findByResourceId(@Param(value = "resourceId") Long resourceId);
}
