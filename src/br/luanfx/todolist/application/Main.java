package br.luanfx.todolist.application;
	
import br.luanfx.todolist.io.TarefaIO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			TarefaIO.createFiles();
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/br/luanfx/todolist/view/Index.fxml"));
			Scene scene = new Scene(root,1032,505);
			scene.getStylesheets().add(getClass().getResource("../view/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Todo Do List");
			primaryStage.getIcons().add (new Image(getClass().getResourceAsStream("/br/luanfx/todolist/imagens/iconeTD.jpg")));
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
