package com.topics.appointment.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.topics.appointment.model.bean.ServicePackage;

@Repository
public interface ServicePackageRepository extends JpaRepository<ServicePackage, Integer> {

	

}
