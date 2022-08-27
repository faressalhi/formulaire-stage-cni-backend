package com.cni.formulairescore.web;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cni.formulairescore.dtos.QuestionDTO;
import com.cni.formulairescore.entities.Question;
import com.cni.formulairescore.exceptions.QuestionNotFoundException;
import com.cni.formulairescore.services.FormulaireService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
@RequestMapping(path = "/questions")
public class QuestionRestController {
	private FormulaireService formulaireService;
	
	@GetMapping
	public List<QuestionDTO> listQuestions() {
		log.info("Getting the list of questions");
		return formulaireService.getAllQuestions();
	}
	
	@GetMapping("/{id}")
	public QuestionDTO getQuestion(@PathVariable String id) throws QuestionNotFoundException {
		log.info("getting the question with id : "+id);
		return formulaireService.getQuestion(id);
	}
	
	@PostMapping
	public QuestionDTO saveQuestion(@RequestBody QuestionDTO questionDTO) {
		log.info("saving a question");
		return formulaireService.addQuestion(questionDTO);
	}
	
	@PutMapping("/{id}")
	public QuestionDTO updateQuestion(@PathVariable String id, @RequestBody QuestionDTO questionDTO) throws QuestionNotFoundException {
		log.info("Updating a question with id : "+id);
		// 1. Set the id of the element that appears on the path
		questionDTO.setIdQ(id);
		// 2. update the question
		return formulaireService.updateQuestion(id, questionDTO);
	}
	
	/*
	 * @PutMapping("/questions/{order}") public QuestionDTO
	 * updateQuestionOfResponse(@PathVariable int order, @RequestBody QuestionDTO
	 * questionDTO) { log.info("Updating the order of a question to : "+order); //
	 * 1. Set the order of the element that appears on the path
	 * questionDTO.setOrdreQ(order); // 2. update the question return
	 * formulaireService.updateQuestion(questionDTO); }
	 */
	
	@DeleteMapping("/{id}")
	public void deleteQuestion(@PathVariable String id) throws QuestionNotFoundException {
		log.info("Deleting a question with Id : "+id);
		formulaireService.deleteQuestion(id);
	}
	
	
}
