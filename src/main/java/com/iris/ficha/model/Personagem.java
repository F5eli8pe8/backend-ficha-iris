package com.iris.ficha.model;

import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "personagens")
public class Personagem extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String raca;

	@Column(nullable = false)
	private String arquetipo;

	@Column(name = "imagem_url")
	private String imagemUrl;

	@Column(name = "pontos_conhecimento")
	private Integer pontosConhecimento = 0;

	@Column(columnDefinition = "TEXT")
	private String anotacoes;

	@OneToOne(mappedBy = "personagem", cascade = CascadeType.ALL, orphanRemoval = true)
	private Vida vida;

	@OneToOne(mappedBy = "personagem", cascade = CascadeType.ALL, orphanRemoval = true)
	private Energia energia;

	@OneToOne(mappedBy = "personagem", cascade = CascadeType.ALL, orphanRemoval = true)
	private Sanidade sanidade;

	@OneToOne(mappedBy = "personagem", cascade = CascadeType.ALL, orphanRemoval = true)
	private EstabilidadeMental estabilidadeMental;

	@OneToMany(mappedBy = "personagem", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Atributo> atributos = new ArrayList<>();

	@OneToMany(mappedBy = "personagem", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Habilidade> habilidades = new ArrayList<>();

	@OneToMany(mappedBy = "personagem", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Ritual> rituais = new ArrayList<>();

	@OneToMany(mappedBy = "personagem", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Poder> poderes = new ArrayList<>();

	@OneToMany(mappedBy = "personagem", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Item> itens = new ArrayList<>();
}
