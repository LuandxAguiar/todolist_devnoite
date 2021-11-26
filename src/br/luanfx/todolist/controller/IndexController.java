
package br.luanfx.todolist.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.luanfx.todolist.io.TarefaIO;
import br.luanfx.todolist.model.StatusTarefa;
import br.luanfx.todolist.model.Tarefa;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class IndexController implements Initializable {

    @FXML
    private DatePicker dataR;

    @FXML
    private TextField descTarefa;

    @FXML
    private TextField statusT;

    @FXML
    private TextArea comenta;

    @FXML
    private Button btSalvar;

    @FXML
    private Button btAdiar;

    @FXML
    private Button btConcluir;

    @FXML
    private Button btExcluir;

    @FXML
    private Button btLimpar;
    @FXML
    private TableColumn<Tarefa, LocalDate> tcData;
    @FXML
    private TableColumn<Tarefa, String> tcTarefa;
    @FXML
    private TableView<Tarefa> tvTarefa;
    
    private List<Tarefa> tarefas;
    private Tarefa tarefa;
    
    
    
    @FXML
    void btClickAd(ActionEvent event) {

    }

    @FXML
    void btClickCo(ActionEvent event) {

    }

    @FXML
    void btClickEx(ActionEvent event) {

    }

    @FXML
    void btClickLi(ActionEvent event) {
    	limpar();
    }

    @FXML
    void btClickSa(ActionEvent event) {
    		//validando os campos
    	if(dataR.getValue() == null) {
    		JOptionPane.showMessageDialog(null, "Informe uma data", "Informe", JOptionPane.ERROR_MESSAGE);
    		dataR.requestFocus();
    	}else if(descTarefa.getText().isEmpty()) {
    		JOptionPane.showMessageDialog(null, "Informe uma tarefa", "Informe", JOptionPane.ERROR_MESSAGE);
    		descTarefa.requestFocus();
    	}else if(comenta.getText().isEmpty()){
    		
    		JOptionPane.showMessageDialog(null, "Digite o seu Comentario", "Informe", JOptionPane.ERROR_MESSAGE);
    		comenta.requestFocus();	
    	}else if(dataR.getValue().isBefore(LocalDate.now())) {
    			JOptionPane.showMessageDialog(null, "A data não deve ser anterior a data atual", "Data Inválida", JOptionPane.ERROR_MESSAGE);
    			dataR.requestFocus();
    	}else {
    		//instanciando a tarefa
			tarefa = new Tarefa();
			//popular a tarefa fazer com que ele carregue as informações do fourmulario
			tarefa.setDataCriacao(LocalDate.now());
			tarefa.setStatus(StatusTarefa.ABERTA);
			tarefa.setDataLimite(dataR.getValue());
			tarefa.setDescricao(descTarefa.getText());
			tarefa.setComentarios(comenta.getText());
			
			// TODO inserir no banca de dados
			
			System.out.println(tarefa.formatToSave());
			try {
			TarefaIO.insert(tarefa);
				//limpar os campos
				limpar();
			}catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Arquivo não encontrado" + e.getMessage(),"Erro", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}catch(IOException e) {
				JOptionPane.showMessageDialog(null, "Erro ao Gravar" + e.getMessage(),"Erro", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
			}
			
		
    	
    }
    private void limpar() {
		tarefa = null;
		dataR.setValue(null);
		descTarefa.setText(null);
		comenta.setText(null);
		statusT.setText(null);
		dataR.requestFocus();
		// formas de limpar tipo texto 
		//setText("") 2 setText(null) 3 .clear()
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//definir os parametros da colunas do tabView
		tcData.setCellValueFactory
				(new PropertyValueFactory<>("dataLimite"));
		tcTarefa.setCellValueFactory
				(new PropertyValueFactory<>("descricao"));
		carregarTarefas();
		
	}
	public void carregarTarefas() {
		try {
			tarefas = TarefaIO.read();
			tvTarefa.setItems(FXCollections.observableArrayList(tarefas));
			tvTarefa.refresh();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao carregar as tareafas"+ e.getMessage(),"Erro",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}


