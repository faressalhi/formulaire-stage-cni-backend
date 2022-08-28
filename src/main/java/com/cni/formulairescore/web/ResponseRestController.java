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

import com.cni.formulairescore.dtos.ChoicesResponseDTO;
import com.cni.formulairescore.dtos.ChoicesResponseSavingFormatDTO;
import com.cni.formulairescore.dtos.ResponseDTO;
import com.cni.formulairescore.dtos.SimpleResponseDTO;
import com.cni.formulairescore.dtos.SimpleResponseSavingFormatDTO;
import com.cni.formulairescore.entities.SimpleResponse;
import com.cni.formulairescore.enums.ChoicesResponseType;
import com.cni.formulairescore.enums.SimpleResponseType;
import com.cni.formulairescore.exceptions.QuestionNotFoundException;
import com.cni.formulairescore.exceptions.ResponseNotFoundException;
import com.cni.formulairescore.services.FormulaireService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
@RequestMapping(path = "/responses")
public class ResponseRestController {

	private FormulaireService formulaireService;
	
	@GetMapping
	public List<ResponseDTO> listResponses() {
		log.info("Getting the list of responses");
		return formulaireService.getAllResponses();
	}
	
	@GetMapping("/{id}")
	public ResponseDTO getResponse(@PathVariable String id) throws ResponseNotFoundException {
		log.info("getting the response with id : "+id);
		return formulaireService.getResponse(id);
	}
	
	@PostMapping("/simpleresponse")
	public SimpleResponseDTO saveSimpleResponse(@RequestBody SimpleResponseSavingFormatDTO srdto) throws QuestionNotFoundException {
		log.info("saving a Response");
		String questionId = srdto.getQuestionId();
		SimpleResponseType simpleResponseType = srdto.getSimpleResponseType();
		return formulaireService.addSimpleResponse(simpleResponseType, questionId);
	}

	@PostMapping("/choicesresponse")
	public ChoicesResponseDTO saveChoicesResponse(@RequestBody ChoicesResponseSavingFormatDTO crdto) throws QuestionNotFoundException {
		log.info("saving a Response");
		String questionId = crdto.getQuestionId();
		ChoicesResponseType choicesResponseType = crdto.getChoicesResponseType();
		return formulaireService.addChoicesResponse(choicesResponseType, questionId);
	}
	
	@PutMapping("/simpleresponse/{responseId}")
	public SimpleResponseDTO updateSimpleResponse(@PathVariable String responseId, @RequestBody SimpleResponseSavingFormatDTO srdto) throws QuestionNotFoundException, ResponseNotFoundException {
		log.info("Updating a Simple Response with Id : "+responseId);
		// 1. Set the id of the element that appears on the path
		String questionId = srdto.getQuestionId();
		SimpleResponseType simpleResponseType = srdto.getSimpleResponseType();
		return (SimpleResponseDTO) formulaireService.updateSimpleResponse(responseId, simpleResponseType, questionId); 		
	
	}
		
	@PutMapping("/choicesresponse/{responseId}")
	public ChoicesResponseDTO updateChoicesResponse(@PathVariable String responseId, @RequestBody ChoicesResponseSavingFormatDTO crdto) throws QuestionNotFoundException, ResponseNotFoundException {
		log.info("Updating a Choices Response with Id : "+responseId);
		// 1. Set the id of the element that appears on the path
		String questionId = crdto.getQuestionId();
		ChoicesResponseType choicesResponseType = crdto.getChoicesResponseType();
		return (ChoicesResponseDTO) formulaireService.updateChoicesResponse(responseId, choicesResponseType, questionId); 		
	
	}
	
	
	@DeleteMapping("/{id}")
	public void deleteResponse(@PathVariable String id) throws ResponseNotFoundException {
		log.info("Deleting a response");
		formulaireService.deleteResponse(id);
	}
	
	
}
