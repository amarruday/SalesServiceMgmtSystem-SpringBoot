package com.ssms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssms.entity.CommonReply;

@Repository
public interface CommonReplyRepository extends JpaRepository<CommonReply, Long> {

}
