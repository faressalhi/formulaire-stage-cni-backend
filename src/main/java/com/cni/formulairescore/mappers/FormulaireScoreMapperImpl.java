package com.cni.formulairescore.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.cni.formulairescore.dtos.ChoiceDTO;
import com.cni.formulairescore.dtos.ChoicesResponseDTO;
import com.cni.formulairescore.dtos.QuestionDTO;
import com.cni.formulairescore.dtos.SimpleResponseDTO;
import com.cni.formulairescore.entities.Choice;
import com.cni.formulairescore.entities.ChoicesResponse;
import com.cni.formulairescore.entities.Question;
import com.cni.formulairescore.entities.SimpleResponse;

import lombok.NoArgsConstructor;

@Service
public class FormulaireScoreMapperImpl {

	
	/*
	 * public ChoicesResponseDTO mapToChoicesResponseDTO(ChoicesResponse
	 * choicesResponse) {
	 * 
	 * ChoicesResponseDTO choicesResponseDTO = new ChoicesResponseDTO();
	 * choicesResponseDTO.setIdR(choicesResponse.getIdR());
	 * choicesResponseDTO.setChoicesResponseType(choicesResponse.
	 * getChoicesResponseType());
	 * choicesResponseDTO.setQuestionId(choicesResponse.getQuestion().getIdQ());
	 * 
	 * return choicesResponseDTO; }
	 */
	
	
	// convert QuestionDTO to Question to save it to the database
	public Question fromQuestionDTO(QuestionDTO questionDTO) {
		
		Question question = new Question();
		BeanUtils.copyProperties(questionDTO, question);
		
		return question;
	}
	
	// convert Question to QuestionDTO to give it to the controller and front part
	public QuestionDTO fromQuestion(Question question) {
	
		QuestionDTO questionDTO = new QuestionDTO();
		BeanUtils.copyProperties(question, questionDTO);
		
		return questionDTO;
		
	}
	
	
	// convert SimpleResponseDTO to SimpleResponse to save it to the database
	public SimpleResponse fromSimpleResponseDTO(SimpleResponseDTO simpleResponseDTO) {
		
		SimpleResponse simpleResponse = new SimpleResponse();
		BeanUtils.copyProperties(simpleResponseDTO, simpleResponse);
		simpleResponse.setQuestion(fromQuestionDTO(simpleResponseDTO.getQuestionDTO()));
		return simpleResponse;
	}
	
	// convert SimpleResponse to SimpleResponseDTO to give it to the controller and front part
	public SimpleResponseDTO fromSimpleResponse(SimpleResponse simpleResponse) {
	
		SimpleResponseDTO simpleResponseDTO = new SimpleResponseDTO();
		BeanUtils.copyProperties(simpleResponse, simpleResponseDTO);
		simpleResponseDTO.setQuestionDTO(fromQuestion(simpleResponse.getQuestion()));
		simpleResponseDTO.setType(SimpleResponse.class.getSimpleName());
		return simpleResponseDTO;
		
	}
	
	// convert ChoicesResponseDTO to ChoicesResponse to save it to the database
	public ChoicesResponse fromChoicesResponseDTO(ChoicesResponseDTO choicesResponseDTO) {
		
		ChoicesResponse choicesResponse = new ChoicesResponse();
		BeanUtils.copyProperties(choicesResponseDTO, choicesResponse);
		choicesResponse.setQuestion(fromQuestionDTO(choicesResponseDTO.getQuestionDTO()));
		return choicesResponse;
	}
	
	// convert ChoicesResponse to ChoicesResponseDTO to give it to the controller and front part
	public ChoicesResponseDTO fromChoicesResponse(ChoicesResponse choicesResponse) {
	
		ChoicesResponseDTO choicesResponseDTO = new ChoicesResponseDTO();
		BeanUtils.copyProperties(choicesResponse, choicesResponseDTO);
		choicesResponseDTO.setQuestionDTO(fromQuestion(choicesResponse.getQuestion()));
		choicesResponseDTO.setType(ChoicesResponse.class.getSimpleName());
		return choicesResponseDTO;
		
	}
	

	// convert ChoiceDTO to Choice to save it to the database
	public Choice fromChoiceDTO(ChoiceDTO choiceDTO) {
		
		Choice choice = new Choice();
		BeanUtils.copyProperties(choiceDTO, choice);
		choice.setChoicesResponse(fromChoicesResponseDTO(choiceDTO.getChoicesResponseDTO()));
		return choice;
	}
	
	// convert Choice to ChoiceDTO to give it to the controller and front part
	public ChoiceDTO fromChoice(Choice choice) {
	
		ChoiceDTO choiceDTO = new ChoiceDTO();
		Assert.notNull(choice, "Source must not be null");
		Assert.notNull(choiceDTO, "Target must not be null");
		BeanUtils.copyProperties(choice, choiceDTO);
		choiceDTO.setChoicesResponseDTO(fromChoicesResponse(choice.getChoicesResponse()));
		
		return choiceDTO;
		
	}
	

}
