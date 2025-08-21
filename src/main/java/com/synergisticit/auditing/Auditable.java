package com.synergisticit.auditing;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@MappedSuperclass
@EntityListeners(value=AuditingEntityListener.class)
public abstract class Auditable {
	
	@CreatedBy
	protected String createdBy;
	
	@CreatedDate
	protected LocalDateTime createdDate;
	
	@LastModifiedBy
	protected String lastModifiedBy;
	
	@LastModifiedDate
	protected LocalDateTime lastModifiedDate;
	
	
	
}
