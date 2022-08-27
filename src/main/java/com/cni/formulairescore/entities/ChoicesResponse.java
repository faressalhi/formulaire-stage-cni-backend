package com.cni.formulairescore.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ForeignKey;

import com.cni.formulairescore.enums.ChoicesResponseType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("withChoices")
@Data @NoArgsConstructor 
public class ChoicesResponse extends Response{
	
	@Enumerated(EnumType.STRING)
	private ChoicesResponseType choicesResponseType;
	
	@OneToMany(mappedBy = "choicesResponse", fetch=FetchType.LAZY)
	private List<Choice> choices;
	
}
