package br.luanfx.todolist.controller;
import javax.swing.JOptionPane;

import br.luanfx.todolist.io.TarefaIO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Login {
	
		
	    @FXML
	    private TextField tfUsuario;
	    
	    
	    @FXML
	    private PasswordField pfSenha;


	    @FXML
	    private Button logarBt;

	    @FXML
	    void btLog(ActionEvent event) {
	    	
	    	String login, senha;
	    	
	    	login = tfUsuario.getText();
	    	senha = pfSenha.getText();
	    	
	    	if(!login.equals("admin") || !senha.equals("senha") ){
	    		JOptionPane.showMessageDialog(null, "Erro no Login ou na senha ","Erro",
	    				JOptionPane.ERROR_MESSAGE);
	    				tfUsuario.requestFocus();
	    	}else {
	    	
	    	try {
				TarefaIO.createFiles();
				AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/br/luanfx/todolist/view/Index.fxml"));
				Scene scene = new Scene(root,1032,505);
				scene.getStylesheets().add(getClass().getResource("/br/luanfx/todolist/view/application.css").toExternalForm());
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.initStyle(StageStyle.UNDECORATED);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.showAndWait();
				stage.getIcons().add (new Image(getClass().getResourceAsStream("/br/luanfx/todolist/imagens/iconeTD.jpg")));
				stage.show();
				Stage stager = (Stage)tfUsuario.getScene().getWindow();
				stager.close();
				
			} catch(Exception e) {
				e.printStackTrace();
			}
	    	}
	    }
	    
	    public void Usuario() {
	    	tfUsuario.getText();
	    	pfSenha.getText();
	    }

	}


