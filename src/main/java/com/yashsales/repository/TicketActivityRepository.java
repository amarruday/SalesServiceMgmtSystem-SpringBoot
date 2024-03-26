package com.yashsales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yashsales.entity.Ticket;
import com.yashsales.entity.TicketActivity;

@Repository
public interface TicketActivityRepository extends JpaRepository<TicketActivity, Long> {

	/*@Query("FROM TicketActivity ta WHERE ta.ticketId=:ticketId")
	public List<TicketActivity> findByTicketId(@Param(value="ticketId") Long ticketId);
	*/
	
	public List<TicketActivity> findByTicket(Ticket ticket);

}
