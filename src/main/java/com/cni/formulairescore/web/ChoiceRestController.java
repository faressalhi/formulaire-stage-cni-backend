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

import com.cni.formulairescore.dtos.ChoiceDTO;
import com.cni.formulairescore.dtos.ChoiceSavingFormatDTO;
import com.cni.formulairescore.dtos.SimpleResponseDTO;
import com.cni.formulairescore.dtos.SimpleResponseSavingFormatDTO;
import com.cni.formulairescore.entities.Choice;
import com.cni.formulairescore.entities.Response;
import com.cni.formulairescore.enums.ChoicesResponseType;
import com.cni.formulairescore.enums.SimpleResponseType;
import com.cni.formulairescore.exceptions.ChoiceNotFoundException;
import com.cni.formulairescore.exceptions.QuestionNotFoundException;
import com.cni.formulairescore.exceptions.ResponseNotFoundException;
import com.cni.formulairescore.services.FormulaireService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
@RequestMapping(path = "/choices")
public class ChoiceRestController {

	private FormulaireService formulaireService;

	@GetMapping
	public List<ChoiceDTO> listChoices() {
		log.info("Getting the list of choices");
		return formulaireService.getAllChoices();
	}
	
	@GetMapping("/{choiceId}")
	public ChoiceDTO getChoice(@PathVariable String choiceId) throws ChoiceNotFoundException {
		log.info("getting the choice with id : "+choiceId);
		return formulaireService.getChoice(choiceId);
	}
	

	@PostMapping
	public ChoiceDTO saveChoice(@RequestBody ChoiceSavingFormatDTO cdto) throws ResponseNotFoundException {
		log.info("saving a choice");		
		String responseId = cdto.getChoicesResponseId();
		String contentC = cdto.getContentC();
		int scoreC = cdto.getScoreC();	
		return formulaireService.addChoice(contentC, scoreC, responseId);
	}

	
	@PutMapping("/{choiceId}")
	public ChoiceDTO updateChoice(@PathVariable String choiceId, @RequestBody ChoiceSavingFormatDTO cdto) throws ChoiceNotFoundException, ResponseNotFoundException {
		log.info("Updating a Choice with Id : "+choiceId);		
		String contentC = cdto.getContentC();
		int scoreC = cdto.getScoreC(); 
		String choicesResponseId = cdto.getChoicesResponseId();
		// 2. update the Choice
		return formulaireService.updateChoice(choiceId, contentC, scoreC, choicesResponseId);
	}
	
	@DeleteMapping("/{choiceId}")
	public void deleteChoice(@PathVariable String choiceId) throws ChoiceNotFoundException {
		log.info("Deleting a Choice");
		formulaireService.deleteChoice(choiceId);
	}
	
	
}
