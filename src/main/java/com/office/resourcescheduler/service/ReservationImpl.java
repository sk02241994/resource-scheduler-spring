package com.office.resourcescheduler.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.office.resourcescheduler.config.SpringContext;
import com.office.resourcescheduler.model.Reservation;
import com.office.resourcescheduler.repository.ReservationRepository;

@Service
@Transactional
public class ReservationImpl {

	private ReservationRepository reservationRepository;

	public static ReservationImpl getInstance() {
		return SpringContext.getBean(ReservationImpl.class);
	}

	@Autowired
	public ReservationImpl(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}

	public List<Reservation> findAll() {
		return reservationRepository.findAll();
	}

	public Optional<Reservation> findById(Long reservationId) {
		return reservationRepository.findById(reservationId);
	}

	public Reservation save(Reservation reservation) {
		return reservationRepository.save(reservation);
	}

	public List<Reservation> findAllByResourceId(Long resourceId) {
		return reservationRepository.findByResourceId(resourceId);
	}
	
	public void delete(Long reservationId) {
		reservationRepository.deleteById(reservationId);
	}
}
