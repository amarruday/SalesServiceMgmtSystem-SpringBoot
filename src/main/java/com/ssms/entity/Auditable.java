package com.ssms.entity;

import java.sql.Timestamp;

import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> {

	@CreatedBy
	@OneToOne
	@JoinColumn(name = "userId")
	protected U createdBy;

	@CreatedDate
	protected Timestamp createdDate;

	@LastModifiedBy
	@OneToOne
	@JoinColumn(name = "userId")
	protected U updatedBy;

	@LastModifiedDate
	protected Timestamp updatedDate;
}
