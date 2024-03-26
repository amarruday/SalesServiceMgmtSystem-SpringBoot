package com.yashsales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yashsales.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket>{
	@Query("FROM Ticket at WHERE at.customer.customerId =:customerId AND at.product.productId=:productId ORDER BY at.addedDate DESC")
	public List<Ticket> findAllByCustomerAndProduct(@Param(value="customerId") Long customerId, @Param(value="productId") Long productId);

	@Query("FROM Ticket at WHERE at.machineSerialNumber =:machineSerialNumber ORDER BY at.ticketId DESC")
	public List<Ticket> findByMachineSerialNumber(@Param(value="machineSerialNumber") String machineSerialNumber);
}
