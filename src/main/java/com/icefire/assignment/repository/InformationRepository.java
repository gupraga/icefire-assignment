package com.icefire.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.icefire.assignment.entity.Information;

@Repository
public interface InformationRepository extends JpaRepository<Information, Long>{
}
