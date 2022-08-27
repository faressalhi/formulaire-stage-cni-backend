package com.cni.formulairescore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cni.formulairescore.entities.Response;

public interface ResponseRepository extends JpaRepository<Response, String> {
	
	
	
}
