package com.cni.formulairescore.dtos;

import com.cni.formulairescore.enums.SimpleResponseType;

import lombok.Data;

@Data
public class SimpleResponseSavingFormatDTO {

	private SimpleResponseType simpleResponseType;
	private String questionId;

}
