package com.cni.formulairescore;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import com.cni.formulairescore.dtos.ChoiceDTO;
import com.cni.formulairescore.dtos.ChoicesResponseDTO;
import com.cni.formulairescore.dtos.QuestionDTO;
import com.cni.formulairescore.dtos.SimpleResponseDTO;
import com.cni.formulairescore.entities.Choice;
import com.cni.formulairescore.entities.ChoicesResponse;
import com.cni.formulairescore.entities.Question;
import com.cni.formulairescore.entities.SimpleResponse;
import com.cni.formulairescore.enums.ChoicesResponseType;
import com.cni.formulairescore.enums.SimpleResponseType;
import com.cni.formulairescore.mappers.FormulaireScoreMapperImpl;
import com.cni.formulairescore.repositories.ResponseRepository;
import com.cni.formulairescore.services.FormulaireService;

@SpringBootApplication
public class StageFormulaireCondidatureApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(StageFormulaireCondidatureApplication.class, args);
	}

	@Autowired 
	FormulaireService formulaireService;
	
	/**
	 * @param formulaireService
	 * @return
	 */
	//@Bean
	

		/*
		 * bankAccountService.listCustomers().forEach(customer -> { try {
		 * bankAccountService.saveCurrentBankAccount(Math.random()*90000, 9000,
		 * customer.getId());
		 * bankAccountService.saveSavingBankAccount(Math.random()*90000, 5.5,
		 * customer.getId());
		 * 
		 * List<BankAccountDTO> bankAccountDTOs = bankAccountService.bankAccountList();
		 * 
		 * for(BankAccountDTO bankAccount : bankAccountDTOs) {
		 * 
		 * for (int i = 0; i < 10; i++) { String accountId; if (bankAccount instanceof
		 * CurrentBankAccountDTO) { accountId = ((CurrentBankAccountDTO)
		 * bankAccount).getId(); } else { accountId = ((SavingBankAccountDTO)
		 * bankAccount).getId(); }
		 * 
		 * bankAccountService.credit(accountId, 10000+Math.random()*120000, "Credit");
		 * bankAccountService.debit(accountId, 1000+Math.random()*9000, "Debit"); }
		 * 
		 * }
		 * 
		 * } catch (CustomerNotFoundException | BankAccountNotFoundException |
		 * UnsufficientBalanceException e) { e.printStackTrace(); } });
		 */

	

	@Override
	public void run(String... args) throws Exception {
		
		// Add questions
		QuestionDTO question_1 = new QuestionDTO();
		question_1.setOrdreQ(1);
		question_1.setContentQ("What's your name : ");
		QuestionDTO savedQuestionDTO_1 = formulaireService.addQuestion(question_1);

		QuestionDTO question_2 = new QuestionDTO();
		question_2.setOrdreQ(2);
		question_2.setContentQ("What's your birth date : ");
		QuestionDTO savedQuestionDTO_2 = formulaireService.addQuestion(question_2);

		QuestionDTO question_3 = new QuestionDTO();
		question_3.setOrdreQ(3);
		question_3.setContentQ("What's your diplomas : ");
		QuestionDTO savedQuestionDTO_3 = formulaireService.addQuestion(question_3);
		
		/*
		 * SimpleResponseDTO simpleResponse_1 = new SimpleResponseDTO();
		 * simpleResponse_1.setQuestionDTO(question_1);
		 * simpleResponse_1.setSimpleResponseType(SimpleResponseType.TEXT_INPUT);
		 * responseRepository.save(dtoMapper.fromSimpleResponseDTO(simpleResponse_1));
		 * 
		 * ChoicesResponseDTO choicesResponseDTO = new ChoicesResponseDTO();
		 * choicesResponseDTO.setQuestionDTO(question_1);
		 * choicesResponseDTO.setChoicesResponseType(ChoicesResponseType.DROPDOWN);
		 * responseRepository.save(dtoMapper.fromChoicesResponseDTO(choicesResponseDTO))
		 * ;
		 */
		
		  // Simple Response -- TEXT_INPUT -- //
		SimpleResponseType simpleResponseType_1 = SimpleResponseType.TEXT_INPUT;
		String questionId_1 = savedQuestionDTO_1.getIdQ();
		SimpleResponseDTO simpleResponseDTO_1 = formulaireService.addSimpleResponse(simpleResponseType_1, questionId_1);
		  
		// Simple Response -- DATE -- //
		
		SimpleResponseType simpleResponseType_2 = SimpleResponseType.DATE;
		String questionId_2 = savedQuestionDTO_2.getIdQ();
		SimpleResponseDTO simpleResponseDTO_2 = formulaireService.addSimpleResponse(simpleResponseType_2, questionId_2);
		 
		// ChoicesResponse //
		
		ChoicesResponseType choicesResponseType = ChoicesResponseType.MULTIPLE_CHOICE;
		String questionId = savedQuestionDTO_3.getIdQ(); 
		ChoicesResponseDTO choicesResponse = formulaireService.addChoicesResponse(choicesResponseType, questionId);
		  
		// Adding relative choices 
			
		/*
		 * ChoiceDTO choice_1_q3 = new ChoiceDTO(); choice_1_q3.setContentC("Licence");
		 * choice_1_q3.setScoreC(5); choice_1_q3.setChoicesResponseDTO(choicesResponse);
		 * formulaireService.addChoice(choice_1_q3);
		 */

		String contentC_1 = "Licence";
		int scoreC_1 = 10;
		String choicesResponseId_1 = choicesResponse.getIdR();
		formulaireService.addChoice(contentC_1, scoreC_1, choicesResponseId_1);

		
		
		String contentC_2 = "Master";
		int scoreC_2 = 15;
		String choicesResponseId_2 = choicesResponse.getIdR();
		formulaireService.addChoice(contentC_2, scoreC_2, choicesResponseId_2);

		
		
		String contentC_3 = "Doctorat";
		int scoreC_3 = 25;
		String choicesResponseId_3 = choicesResponse.getIdR();
		formulaireService.addChoice(contentC_3, scoreC_3, choicesResponseId_3);

		/*
		 * 
		 * ChoiceDTO choice_2_q3 = new ChoiceDTO(); choice_2_q3.setContentC("Master");
		 * choice_2_q3.setScoreC(10);
		 * choice_2_q3.setChoicesResponseDTO(choicesResponse);
		 * formulaireService.addChoice(choice_2_q3);
		 * 
		 * ChoiceDTO choice_3_q3 = new ChoiceDTO(); choice_3_q3.setContentC("Doctorat");
		 * choice_3_q3.setScoreC(20);
		 * choice_3_q3.setChoicesResponseDTO(choicesResponse);
		 * formulaireService.addChoice(choice_3_q3);
		 * 
		 */
	};
	
	
	
	

}
