package com.cni.formulairescore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cni.formulairescore.entities.Question;

public interface QuestionRepository extends JpaRepository<Question, String> {

}
