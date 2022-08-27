package com.cni.formulairescore.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cni.formulairescore.dtos.ChoiceDTO;
import com.cni.formulairescore.dtos.ChoicesResponseDTO;
import com.cni.formulairescore.dtos.QuestionDTO;
import com.cni.formulairescore.dtos.ResponseDTO;
import com.cni.formulairescore.dtos.SimpleResponseDTO;
import com.cni.formulairescore.entities.Choice;
import com.cni.formulairescore.entities.ChoicesResponse;
import com.cni.formulairescore.entities.Question;
import com.cni.formulairescore.entities.Response;
import com.cni.formulairescore.entities.SimpleResponse;
import com.cni.formulairescore.enums.ChoicesResponseType;
import com.cni.formulairescore.enums.SimpleResponseType;
import com.cni.formulairescore.exceptions.ChoiceNotFoundException;
import com.cni.formulairescore.exceptions.QuestionNotFoundException;
import com.cni.formulairescore.exceptions.ResponseNotFoundException;
import com.cni.formulairescore.mappers.FormulaireScoreMapperImpl;
import com.cni.formulairescore.repositories.ChoicesRepository;
import com.cni.formulairescore.repositories.QuestionRepository;
import com.cni.formulairescore.repositories.ResponseRepository;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service 
@Transactional
@AllArgsConstructor
@Slf4j
public class FormulaireServiceImpl implements FormulaireService {

	private QuestionRepository questionRepository;
	private ResponseRepository responseRepository;
	private ChoicesRepository choicesRepository;
	// mapper les entites JPA en documents JSON
	private FormulaireScoreMapperImpl dtoMapper;
	
	
	/**************** Question Section *******************/
	
	@Override
	public QuestionDTO addQuestion(QuestionDTO questionDTO) {
		log.info("Saving new question with content : "+questionDTO.getContentQ()
				+" and order : "+questionDTO.getOrdreQ());
		// 1. convert questionDTO to question
		Question question = dtoMapper.fromQuestionDTO(questionDTO);
		// 2. save the question in the database
		Question savedQuestion = questionRepository.save(question);
		// 3. convert the saved question to questionDTO
		QuestionDTO savedQuestionDTO = dtoMapper.fromQuestion(savedQuestion);
		// 4. return the questionDTO
		return savedQuestionDTO;
	}
	
	@Override
	public QuestionDTO updateQuestion(String questionId, QuestionDTO questionDTO) throws QuestionNotFoundException {
		
		Question question = questionRepository.findById(questionId)
				.orElseThrow(() -> new QuestionNotFoundException("Question Not Found !"));

		if ((questionDTO.getContentQ() != null) && (questionDTO.getContentQ() != "null") && (questionDTO.getContentQ() != "")) {
			log.info("Updating question with Id :"+questionId+" to content : "+questionDTO.getContentQ());
			question.setContentQ(questionDTO.getContentQ());
		} 
		
		if ((questionDTO.getOrdreQ() != 0)) {
			log.info("Updating a Choice with Id :"+questionId+" to score : "+questionDTO.getOrdreQ());
			question.setOrdreQ(questionDTO.getOrdreQ());
		} 
			

		//
		
		log.info("Updating the question with Id : "+questionId);  
		// 2. save the question in the database
		Question savedQuestion = questionRepository.save(question);
		// 3. convert the saved question to questionDTO
		QuestionDTO savedQuestionDTO = dtoMapper.fromQuestion(savedQuestion);
		// 4. return the questionDTO
		return savedQuestionDTO;
	}
	
	@Override
	public void deleteQuestion(String questionId) {
		log.info("Deleting a question with Id : "+questionId);
		questionRepository.deleteById(questionId);
	}
	
	@Override
	public QuestionDTO getQuestion(String questionId) throws QuestionNotFoundException {
		log.info("Searching for the question with Id : "+questionId);
		Question question = questionRepository.findById(questionId)
				.orElseThrow(() -> new QuestionNotFoundException("Question Not Found !"));
		return dtoMapper.fromQuestion(question);
	}
	
	@Override
	public List<QuestionDTO> getAllQuestions() {
		log.info("Getting all the questions");
		// 1. Get all the questions from the database
		List<Question> listQuestions = questionRepository.findAll();
		// 2. map each question and convert it to questionDTO 
		List<QuestionDTO> listQuestionsDTO = listQuestions.stream().map(quest -> {
		
			return dtoMapper.fromQuestion(quest);
		// 3. Collect all the questionDTOs
		}).collect(Collectors.toList());
		// 4. Return the list of questionDTOs
		return listQuestionsDTO;
		
	}

	
	/**************** Response Section  *******************/
	
	@Override
	public SimpleResponseDTO addSimpleResponse(SimpleResponseType simpleResponseType, String questionId) throws QuestionNotFoundException {
		log.info("Saving a Simple Response for the question with Id :"+questionId);
		// 1. convert simpleResponseDTO to simpleResponse
		Question question = new Question();
		question = questionRepository.findById(questionId).orElse(null);
		if (question == null) throw new QuestionNotFoundException("Question Not Found!");
		SimpleResponse simpleResponse = new SimpleResponse();
		simpleResponse.setSimpleResponseType(simpleResponseType);		
		simpleResponse.setQuestion(question);
		// 2. save the simpleResponse in the database
		SimpleResponse savedsimpleResponse = responseRepository.save(simpleResponse);
		// 3. convert the saved simpleResponse to simpleResponseDTO
		
		SimpleResponseDTO savedsimpleResponseDTO = new SimpleResponseDTO();
		savedsimpleResponseDTO.setType("Simple");
		savedsimpleResponseDTO = dtoMapper.fromSimpleResponse(savedsimpleResponse);
		// 4. return the simpleResponseDTO
		return savedsimpleResponseDTO;
	}
	
	@Override
	public ChoicesResponseDTO addChoicesResponse(ChoicesResponseType choicesResponseType, String questionId) throws QuestionNotFoundException {
		log.info("Saving a Choices Response for the question with Id :"+questionId);  
		// 1. convert choicesResponseDTO to choicesResponse
		Question question = questionRepository.findById(questionId).orElseThrow(() -> new QuestionNotFoundException("Question Not Found!") );
		ChoicesResponse choicesResponse = new ChoicesResponse();
		choicesResponse.setChoicesResponseType(choicesResponseType);
		choicesResponse.setQuestion(question);
		// 2. save the choicesResponse in the database
		ChoicesResponse savedChoicesResponse = responseRepository.save(choicesResponse);
		// 3. convert the saved choicesResponse to choicesResponseDTO
		ChoicesResponseDTO savedChoicesResponseDTO = dtoMapper.fromChoicesResponse(savedChoicesResponse);
		// 4. return the choicesResponseDTO
		return savedChoicesResponseDTO;
	}
	
	@Override
	public ResponseDTO updateSimpleResponse(String responseId, SimpleResponseType simpleResponseType, String questionId) throws QuestionNotFoundException, ResponseNotFoundException {	
		
		Response response = responseRepository.findById(responseId)
				.orElseThrow(() -> new ResponseNotFoundException("Response Not found !"));
		
		if (response instanceof SimpleResponse) {
			
			if ((simpleResponseType != null) && (simpleResponseType != ((SimpleResponse)response).getSimpleResponseType())) {
				log.info("Updating a Simple Response with Id :"+responseId+" to type : "+simpleResponseType);
				((SimpleResponse) response).setSimpleResponseType(simpleResponseType);
			} 
			
			if ((questionId != "") && (questionId != null) && (questionId != ((SimpleResponse)response).getQuestion().getIdQ() )) {
				log.info("Updating a Simple Response with Id :"+responseId+" to questionId : "+questionId);
				Question question = questionRepository.findById(questionId)
						.orElseThrow(() -> new QuestionNotFoundException("Question Not found !"));
				((SimpleResponse) response).setQuestion(question);
			}
		}
		
		SimpleResponse savedSimpleResponse = (SimpleResponse) responseRepository.save(response);
		SimpleResponseDTO savedSimpleResponseDTO = dtoMapper.fromSimpleResponse(savedSimpleResponse);
		return savedSimpleResponseDTO;
			
	}
	
	@Override
	public ResponseDTO updateChoicesResponse(String responseId, ChoicesResponseType choicesResponseType, String questionId) throws QuestionNotFoundException, ResponseNotFoundException {	

		Response response = responseRepository.findById(responseId)
				.orElseThrow(() -> new ResponseNotFoundException("Response Not found !"));
		
		if (response instanceof ChoicesResponse) {
			
			if ((choicesResponseType != null) && (choicesResponseType != ((ChoicesResponse)response).getChoicesResponseType())) {
				log.info("Updating a Choices Response with Id :"+responseId+" to type : "+choicesResponseType);
				((ChoicesResponse) response).setChoicesResponseType(choicesResponseType);
			} 
			
			if (questionId != "" && questionId != null && questionId != ((ChoicesResponse)response).getQuestion().getIdQ() ) {
				log.info("Updating a Choices Response with Id :"+responseId+" to questionId : "+questionId);
				Question question = questionRepository.findById(questionId).orElse(null);
				if (question == null) throw new QuestionNotFoundException("Question Not Found!");
				((ChoicesResponse) response).setQuestion(question);
			}
			
		}
		
		ChoicesResponse savedChoicesResponse = (ChoicesResponse) responseRepository.save(response);
		ChoicesResponseDTO savedChoicesResponseDTO = dtoMapper.fromChoicesResponse(savedChoicesResponse);
		return savedChoicesResponseDTO;
		
	}
	
	
	@Override
	public void deleteResponse(String responseId) throws ResponseNotFoundException {
	
		log.info("Deleting a response with Id : "+responseId);
		responseRepository.deleteById(responseId);
		
	}
	
	@Override
	public ResponseDTO getResponse(String responseId) throws ResponseNotFoundException {
		log.info("Getting the response with Id : "+responseId);
		Response response = responseRepository.findById(responseId)
				.orElseThrow(() -> new ResponseNotFoundException("Response Not Found !"));
		
		if(response instanceof SimpleResponse) {
			SimpleResponse simpleResponse = (SimpleResponse) response;
			return dtoMapper.fromSimpleResponse(simpleResponse);
		} else {
			ChoicesResponse choicesResponse = (ChoicesResponse) response;
			return dtoMapper.fromChoicesResponse(choicesResponse);
		}
		
	}
	
	@Override
	public List<ResponseDTO> getAllResponses() {
		log.info("Getting all the responses");
		// 1. Get all the responses from the database
		List<Response> listResponses = responseRepository.findAll();
		// 2. map each response and convert it to DTO 
		List<ResponseDTO> listResponsesDTO = listResponses.stream().map(resp -> {
	
			if (resp instanceof SimpleResponse) {
				return dtoMapper.fromSimpleResponse((SimpleResponse)resp);
			} 
			else {
				return dtoMapper.fromChoicesResponse((ChoicesResponse)resp);
			}
			
		// 3. Collect all the DTOs
		}).collect(Collectors.toList());
		// 4. Return the list of DTOs
		return listResponsesDTO;
	}
	
	/*
	 * @Override public void updateQuestionOfResponse(Long questionId, Long
	 * responseId) throws ResponseNotFoundException, QuestionNotFoundException {
	 * ResponseDTO responseDTO = getResponse(questionId); QuestionDTO questionDTO =
	 * getQuestion(responseId); responseDTO.setQuestionDTO(questionDTO);
	 * updateResponse(responseDTO); }
	 */
	
	/**************** Choice Section 
	 * @throws ResponseNotFoundException *******************/
	
	
	@Override
	public ChoiceDTO addChoice(String contentC, int scoreC, String choicesResponseId) throws ResponseNotFoundException {
		log.info("Adding new choice to Choices Response with Id : "+choicesResponseId
					+" with content : "+ contentC + " and score : "+scoreC);
				
		// 1. convert simpleResponseDTO to simpleResponse
		ChoicesResponse choicesResponse = new ChoicesResponse();
		choicesResponse = (ChoicesResponse) responseRepository.findById(choicesResponseId).orElse(null);
		if (choicesResponse == null) throw new ResponseNotFoundException("Response Not Found!");
		
		Choice choice = new Choice();
		choice.setContentC(contentC);
		choice.setScoreC(scoreC);
		choice.setChoicesResponse(choicesResponse);
		
		// 2. save the simpleResponse in the database
		Choice savedChoice = choicesRepository.save(choice);
		// 3. convert the saved simpleResponse to simpleResponseDTO
		ChoiceDTO savedChoiceDTO = dtoMapper.fromChoice(savedChoice);
		// 4. return the simpleResponseDTO
		return savedChoiceDTO;
		
	}
	
	
	@Override
	public ChoiceDTO updateChoice(String choiceId, String contentC, int scoreC, String choicesResponseId) throws ChoiceNotFoundException, ResponseNotFoundException {
	
		Choice choice = choicesRepository.findById(choiceId)
				.orElseThrow(() -> new ChoiceNotFoundException("Choice Not found !"));

		if ((contentC != null) && (contentC != "null") && (contentC != "") && (contentC != choice.getContentC())) {
			log.info("Updating a Choice with Id :"+choiceId+" to content : "+contentC);
			choice.setContentC(contentC);
		} 
		
		if ((scoreC != 0) &&(scoreC != choice.getScoreC())) {
			log.info("Updating a Choice with Id :"+choiceId+" to score : "+scoreC);
			choice.setScoreC(scoreC);
		} 
		
		if ((choicesResponseId != "") && (choicesResponseId != "null") && (choicesResponseId != null) && (choicesResponseId != choice.getChoicesResponse().getIdR() )) {
			log.info("Updating a Choice with Id :"+choiceId+" to Choices Response with Id : : "+choicesResponseId);
			Response response = responseRepository.findById(choicesResponseId)
					.orElseThrow(() -> new ResponseNotFoundException("Choices Response Not found !"));
			choice.setChoicesResponse((ChoicesResponse) response);
		}
		
		Choice savedChoice = choicesRepository.save(choice);
		ChoiceDTO savedChoiceDTO = dtoMapper.fromChoice(savedChoice);
		return savedChoiceDTO;
	}
	
	
	@Override
	public void deleteChoice(String choiceId) {
		log.info("Deleting a choice with Id : "+choiceId);
		choicesRepository.deleteById(choiceId);
	}
	
	@Override
	public ChoiceDTO getChoice(String choiceId) throws ChoiceNotFoundException {
		log.info("Searching for the choice with Id : "+choiceId);
		Choice choice = choicesRepository.findById(choiceId)
				.orElseThrow(() -> new ChoiceNotFoundException("Choice Not Found !"));
		return dtoMapper.fromChoice(choice);
	}
	
	@Override
	public List<ChoiceDTO> getAllChoices() {
		log.info("Getting all the choices");
		// 1. Get all the choices from the database
		List<Choice> listChoices = choicesRepository.findAll();
		// 2. map each choice and convert it to choiceDTO 
		List<ChoiceDTO> listChoicesDTO = listChoices.stream().map(ch -> {
		
			return dtoMapper.fromChoice(ch);
		// 3. Collect all the choiceDTOs
		}).collect(Collectors.toList());
		// 4. Return the list of choiceDTOs
		return listChoicesDTO;
		
	}

	@Override
	// The role of this function is to get only the choices of a question
	public List<ChoiceDTO> getAllChoicesOfQuestion(String questionId) {
		log.info("Getting all the choices of the question with Id : "+questionId);
		// 1. Get all the choices from the database
		List<Choice> listChoices = choicesRepository.findAll();
		// 2. map each choice and convert it to choiceDTO 
		List<ChoiceDTO> listChoicesDTO = listChoices.stream().map(ch -> {
			if(ch.getChoicesResponse().getQuestion().getIdQ()==questionId) 
				return dtoMapper.fromChoice(ch);
			else 
				return null;
		// 3. Collect all the choiceDTOs
		}).collect(Collectors.toList());
		// 4. Return the list of choiceDTOs
		return listChoicesDTO;
	}





	
	/*
	 * 
	 * @Override
	public void addQuestion(Question question) {
		questionRepository.save(question);
		
	}

	@Override
	public void updateQuestion(Question question) {
		questionRepository.save(question);
		
	}

	@Override
	public void deleteQuestion(Long questionId) {
		questionRepository.deleteById(questionId);
		
	}

	
	@Override
	public Question getQuestion(Long questioId) throws QuestionNotFoundException {
		Question question = questionRepository.findById(questioId)
				.orElseThrow(() -> new QuestionNotFoundException("Question Not Found !"));
		return question;
	}

	
	@Override
	public List<Question> getAllQuestions() {
		List<Question> listQuestions = questionRepository.findAll();
		return listQuestions;
	}

	@Override
	public void addResponse(Response response) {
		responseRepository.save(response);
		
	}

	@Override
	public void updateResponse(Response response) {
		responseRepository.save(response);
		
	}

	@Override
	public void deleteResponse(Long responseId) {
		responseRepository.deleteById(responseId);
		
	}

	@Override
	public Response getResponse(Long responseId) throws ResponseNotFoundException {
		Response response = responseRepository.findById(responseId)
				.orElseThrow(() -> new ResponseNotFoundException("Response Not Found !"));
		return response;
	}


	@Override
	public List<Response> getAllResponses() {
		List<Response> listResponses = responseRepository.findAll();
		return listResponses;
	}

	@Override
	public void addChoice(Choice choice) {
		choicesRepository.save(choice);
		
	}

	@Override
	public void updateChoice(Choice choice) {
		choicesRepository.save(choice);
		
	}

	@Override
	public void deleteChoice(Long choiceId) {
		choicesRepository.deleteById(choiceId);
		
	}


	@Override
	public Choice getChoice(Long choiceId) throws ChoiceNotFoundException {
		Choice choice = choicesRepository.findById(choiceId)
				.orElseThrow(() -> new ChoiceNotFoundException("Choice Not Found !"));
		return null;
	}
	
	@Override
	public List<Choice> getAllChoices() {
		List<Choice> listChoices = choicesRepository.findAll();
		return listChoices;
	}

	 * 
	 * */
	
	

}
