package br.luanfx.todolist.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SobreController {

	 	@FXML
	    private Button aboutBt;
	   
	    @FXML
	    public void btSair(ActionEvent event) {
	    	//metodo para fechar a janela sobre
	    	Stage stage = (Stage)aboutBt.getScene().getWindow();
	    	stage.close();
	    	
	    }
}
