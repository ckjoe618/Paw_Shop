package com.topics.appointment.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.topics.member.model.entity.MemberBean;

public interface OwnerRepository extends JpaRepository<MemberBean, Integer> {

}
