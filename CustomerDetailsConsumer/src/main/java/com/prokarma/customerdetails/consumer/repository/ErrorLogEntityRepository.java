package com.prokarma.customerdetails.consumer.repository;

import org.springframework.data.repository.CrudRepository;
import com.prokarma.customerdetails.consumer.entity.ErrorLogEntity;


public interface ErrorLogEntityRepository extends CrudRepository<ErrorLogEntity, Long> {


}
