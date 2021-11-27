package br.luanfx.todolist.model;

public enum StatusTarefa {
		//constantes para enumerar cada tarefa !!!!
	ABERTA("Aberta"), 
	ADIADA("Adiada"), 
	CONCLUIDA("Concluída");
	
	private StatusTarefa(String descricao) {
		this.descricao = descricao;
		
	}
	String descricao;
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return descricao;
	}
}
