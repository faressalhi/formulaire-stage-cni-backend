package com.cni.formulairescore.dtos;

import com.cni.formulairescore.enums.SimpleResponseType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class SimpleResponseDTO extends ResponseDTO {

	private String idR;
	private SimpleResponseType simpleResponseType;
	private QuestionDTO questionDTO;

}
