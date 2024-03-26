package com.yashsales.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yashsales.entity.ActionType;


@Repository
public interface ActionTypeRepository extends JpaRepository<ActionType, Long> {

	@Query("FROM ActionType at WHERE at.status =:status")
	List<ActionType> findAllByStatus(@Param(value="status") String actionTypeActive);
	
}
