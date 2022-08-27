package com.cni.formulairescore.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cni.formulairescore.entities.Choice;

public interface ChoicesRepository extends JpaRepository<Choice, String> {

	List<Choice> findByChoicesResponseIdR(String responseId);

	
}
