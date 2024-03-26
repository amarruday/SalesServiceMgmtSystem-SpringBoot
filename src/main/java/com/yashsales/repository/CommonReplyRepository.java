package com.yashsales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yashsales.entity.CommonReply;

@Repository
public interface CommonReplyRepository extends JpaRepository<CommonReply, Long> {

}
