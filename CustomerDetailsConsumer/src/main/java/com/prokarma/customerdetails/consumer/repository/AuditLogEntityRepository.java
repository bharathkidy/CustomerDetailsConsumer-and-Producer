package com.prokarma.customerdetails.consumer.repository;

import org.springframework.data.repository.CrudRepository;
import com.prokarma.customerdetails.consumer.entity.AuditLogEntity;


public interface AuditLogEntityRepository extends CrudRepository<AuditLogEntity, Long> {


}
