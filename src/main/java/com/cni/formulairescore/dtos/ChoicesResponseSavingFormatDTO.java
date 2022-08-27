package com.cni.formulairescore.dtos;

import com.cni.formulairescore.enums.ChoicesResponseType;

import lombok.Data;

@Data
public class ChoicesResponseSavingFormatDTO  {

	private ChoicesResponseType choicesResponseType;
	private String questionId;

	
}
