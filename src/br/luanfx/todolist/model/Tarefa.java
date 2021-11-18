package br.luanfx.todolist.model;

import java.time.LocalDate;

public class Tarefa {
	private long id;
	private LocalDate dataCriacao;
	private LocalDate dataLimite;
	private LocalDate dataConcluida;
	private String descricao;  
	private String comentarios;
	private StatusTarefa status;
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the dataCriacao
	 */
	public LocalDate getDataCriacao() {
		return dataCriacao;
	}
	/**
	 * @param dataCriacao the dataCriacao to set
	 */
	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	/**
	 * @return the dataLimite
	 */
	public LocalDate getDataLimite() {
		return dataLimite;
	}
	/**
	 * @param dataLimite the dataLimite to set
	 */
	public void setDataLimite(LocalDate dataLimite) {
		this.dataLimite = dataLimite;
	}
	/**
	 * @return the dataConcluida
	 */
	public LocalDate getDataConcluida() {
		return dataConcluida;
	}
	/**
	 * @param dataConcluida the dataConcluida to set
	 */
	public void setDataConcluida(LocalDate dataConcluida) {
		this.dataConcluida = dataConcluida;
	}
	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}
	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	/**
	 * @return the comentarios
	 */
	public String getComentarios() {
		return comentarios;
	}
	/**
	 * @param comentarios the comentarios to set
	 */
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	/**
	 * @return the status
	 */
	public StatusTarefa getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(StatusTarefa status) {
		this.status = status;
	}
	

	
	
	
}
