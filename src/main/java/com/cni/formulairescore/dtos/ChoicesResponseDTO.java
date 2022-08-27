package com.cni.formulairescore.dtos;

import com.cni.formulairescore.enums.ChoicesResponseType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ChoicesResponseDTO extends ResponseDTO {

	private String idR;
	private ChoicesResponseType choicesResponseType;
	private QuestionDTO questionDTO;

} 
