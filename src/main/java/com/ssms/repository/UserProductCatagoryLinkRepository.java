package com.ssms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssms.entity.UserProductCatagoryLink;
@Repository
public interface UserProductCatagoryLinkRepository extends JpaRepository<UserProductCatagoryLink, Long> {

	@Query("FROM UserProductCatagoryLink upcl WHERE upcl.user.userId=:userId")
	public UserProductCatagoryLink findByUserId(@Param(value="userId")  Long userId);

}
