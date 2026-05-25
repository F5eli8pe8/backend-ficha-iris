package com.iris.ficha.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sanidade")
public class Sanidade extends BaseEntity {

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "personagem_id", nullable = false, unique = true)
	private Personagem personagem;

	@Column(nullable = false)
	private Integer atual;
	@Column(nullable = false)
	private Integer maximo;
}
