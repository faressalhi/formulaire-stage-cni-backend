package com.cni.formulairescore.services;

import java.util.List;

import com.cni.formulairescore.dtos.ChoiceDTO;
import com.cni.formulairescore.dtos.ChoicesResponseDTO;
import com.cni.formulairescore.dtos.QuestionDTO;
import com.cni.formulairescore.dtos.ResponseDTO;
import com.cni.formulairescore.dtos.SimpleResponseDTO;
import com.cni.formulairescore.enums.ChoicesResponseType;
import com.cni.formulairescore.enums.SimpleResponseType;
import com.cni.formulairescore.exceptions.ChoiceNotFoundException;
import com.cni.formulairescore.exceptions.QuestionNotFoundException;
import com.cni.formulairescore.exceptions.ResponseNotFoundException;

public interface FormulaireService {
	
	// Question services
	QuestionDTO addQuestion(QuestionDTO questionDTO);
	QuestionDTO updateQuestion(String questionId, QuestionDTO questionDTO) throws QuestionNotFoundException;
	void deleteQuestion(String questionId) throws QuestionNotFoundException;
	QuestionDTO getQuestion(String questionId) throws QuestionNotFoundException;
	List<QuestionDTO> getAllQuestions();
	
	// Response services
	SimpleResponseDTO addSimpleResponse(SimpleResponseType simpleResponseType, String questionId) throws QuestionNotFoundException;
	ChoicesResponseDTO addChoicesResponse(ChoicesResponseType choicesResponseType, String questionId) throws QuestionNotFoundException;
	ResponseDTO updateSimpleResponse(String responseId, SimpleResponseType simpleResponseType
			,String questionId) throws QuestionNotFoundException, ResponseNotFoundException;
	ResponseDTO updateChoicesResponse(String responseId, ChoicesResponseType choicesResponseType
			, String questionId) throws QuestionNotFoundException, ResponseNotFoundException;

	void deleteResponse(String responseId) throws ResponseNotFoundException;
	ResponseDTO getResponse(String responseId) throws ResponseNotFoundException;
	List<ResponseDTO> getAllResponses();
	// void updateQuestionContent(Long idQuestion, String newContent) throws QuestionNotFoundException;

	
	// Choice service
	ChoiceDTO addChoice(String contentC, int scoreC, String choicesResponseId) throws ResponseNotFoundException;
	public ChoiceDTO updateChoice(String choiceId, String contentC, int scoreC, String choicesResponseId) throws ChoiceNotFoundException, ResponseNotFoundException;
	void deleteChoice(String choiceId) throws ChoiceNotFoundException;
	ChoiceDTO getChoice(String choiceId) throws ChoiceNotFoundException;
	List<ChoiceDTO> getAllChoices();
	List<ChoiceDTO> getAllChoicesOfQuestion(String questionId);
	

	
	

}
