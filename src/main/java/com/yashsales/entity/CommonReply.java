package com.yashsales.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "common_replies")
public class CommonReply {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long commonReplyId;
	private String commonReply;
}
