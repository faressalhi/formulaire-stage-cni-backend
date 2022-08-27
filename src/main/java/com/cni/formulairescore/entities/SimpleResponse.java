package com.cni.formulairescore.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.cni.formulairescore.enums.SimpleResponseType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("Simple")
@Data @NoArgsConstructor 
public class SimpleResponse extends Response{
	
	@Enumerated(EnumType.STRING)
	private SimpleResponseType simpleResponseType  ;
	
}
