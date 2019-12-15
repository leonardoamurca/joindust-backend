package com.joindust.joindustbackend.models.audits;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@JsonIgnoreProperties(value = { "createdBy", "updatedBy" }, allowGetters = true)
@Getter
@Setter
public abstract class UserDateAudit extends DateAudit {

  private static final long serialVersionUID = 964161340939243280L;

  @CreatedBy
  @Column(updatable = false)
  private Long createdBy;

  @LastModifiedBy
  private Long updatedBy;

}