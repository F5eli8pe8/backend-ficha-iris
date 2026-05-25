package com.iris.ficha.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "atributos")
public class Atributo extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "personagem_id", nullable = false)
	private Personagem personagem;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private Integer valor;

	@OneToMany(mappedBy = "atributo", orphanRemoval = true)
	private List<Pericia> pericias = new ArrayList<>();
}
