package br.luanfx.todolist.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SobreController {

	 	@FXML
	    private Button aboutBt;
	   
	    @FXML
	    public void btSair(ActionEvent event) {
	    	System.exit(0);
	    }
}
