package org.generation.lojaGames.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity  // a annotation do Srping Entity indica que aqui será uma tabela
@Table(name="tb_categoria") // é equivalente a create table tb_categoria
public class Categoria {
	
	@Id // chave primária
	@GeneratedValue(strategy=GenerationType.IDENTITY) // A estratégia de incremento é o auto-increment cuja responsabilidade é atribuida ao banco de dados
	private Long id;
	
	@NotNull
	private String menu;
	
	@UpdateTimestamp // formata a data no padrão do notebook
	private LocalDate data;
	
	private Boolean preVenda;
	
	@OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("categoria")
	private List<Game> game;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Boolean getPreVenda() {
		return preVenda;
	}

	public void setPreVenda(Boolean preVenda) {
		this.preVenda = preVenda;
	}

	public List<Game> getGame() {
		return game;
	}

	public void setGame(List<Game> game) {
		this.game = game;
	}
	
	

	}
