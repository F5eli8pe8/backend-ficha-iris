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
@Table(name = "estabilidade_mental")
public class EstabilidadeMental extends BaseEntity {

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "personagem_id", nullable = false, unique = true)
	private Personagem personagem;

	@Column(name = "valor_atual", nullable = false)
	private Integer valorAtual;

	@Column(name = "valor_maximo", nullable = false)
	private Integer valorMaximo;
}
