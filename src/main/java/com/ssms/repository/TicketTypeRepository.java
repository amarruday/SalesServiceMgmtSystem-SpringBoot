package com.ssms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssms.entity.TicketType;

public interface TicketTypeRepository extends JpaRepository<TicketType, Long> {

	@Query("FROM TicketType at WHERE at.status =:status")
	public List<TicketType> findAllByStatus(@Param(value="status") String status);

}
