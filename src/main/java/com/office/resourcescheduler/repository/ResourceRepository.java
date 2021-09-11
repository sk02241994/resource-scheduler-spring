package com.office.resourcescheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.office.resourcescheduler.model.Resource;

public interface ResourceRepository extends CrudRepository<Resource, Long>, JpaRepository<Resource, Long>{

}
