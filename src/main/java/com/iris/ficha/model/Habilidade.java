package com.iris.ficha.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "habilidades")
public class Habilidade extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "personagem_id", nullable = false)
	private Personagem personagem;

	@Column(nullable = false)
	private String nome;

	@Column(columnDefinition = "TEXT")
	private String descricao;
}
