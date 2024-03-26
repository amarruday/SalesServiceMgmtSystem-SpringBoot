package com.yashsales.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "common_replies")
public class CommonReply {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long commonReplyId;
	private String commonReply;

	public Long getCommonReplyId() {
		return commonReplyId;
	}

	public void setCommonReplyId(Long commonReplyId) {
		this.commonReplyId = commonReplyId;
	}

	public String getCommonReply() {
		return commonReply;
	}

	public void setCommonReply(String commonReply) {
		this.commonReply = commonReply;
	}

}
