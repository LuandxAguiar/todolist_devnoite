package br.luanfx.todolist.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.luanfx.todolist.io.TarefaIO;
import br.luanfx.todolist.model.StatusTarefa;
import br.luanfx.todolist.model.Tarefa;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class IndexController implements Initializable, ChangeListener<Tarefa> {
	@FXML
	private TextField tfID;

	@FXML
	private DatePicker dataR;
	@FXML
	private DatePicker dtConcluida;

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
		if (tarefa != null) {
			int dias = Integer.parseInt(JOptionPane.showInputDialog(null, "Quantos dias você deseja adiar",
					"Informe quantos dias ", JOptionPane.QUESTION_MESSAGE));
			// criou uma nova data e usou o plus day para para adicionar os dias
			LocalDate novaData = tarefa.getDataLimite().plusDays(dias);
			tarefa.setDataLimite(novaData);
			tarefa.setStatus(StatusTarefa.ADIADA);
			try {
				TarefaIO.saveTarefas(tarefas);
				DateTimeFormatter day = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
				JOptionPane.showMessageDialog(null, "a data foi adiada para" + day + " e sua realização é " + novaData);
				carregarTarefas();
				limpar();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "A data não deve ser anterior a data atual", "Data Inválida",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}

		}

		limpar();
		dataR.setDisable(false);
	}

	@FXML
	void btClickCo(ActionEvent event) {
		if (tarefa != null) {
			tarefa.setStatus(StatusTarefa.CONCLUIDA);
			LocalDate dataHoje = LocalDate.now();
			tarefa.setDataConcluida(dataHoje);

			limpar();
			try {
				TarefaIO.saveTarefas(tarefas);
				JOptionPane.showMessageDialog(null, "Sua tarefa Foi concluida");
				limpar();
				carregarTarefas();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erro");

				e.printStackTrace();
			}

		}

	}

	@FXML
	void btClickEx(ActionEvent event) {
		if (tarefa != null) {
			int resposta = JOptionPane.showConfirmDialog(null, "Deseja excluir essa tarefa " + tarefa.getId() + "?",
					"Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (resposta == 0) {
				tarefas.remove(tarefa);
				try {
					TarefaIO.saveTarefas(tarefas);
					carregarTarefas();
					limpar();
					JOptionPane.showMessageDialog(null, "Tarefa Excluída");
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Erro ao Excluir a tarefa" + JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		}

		limpar();
		dataR.setDisable(false);
	}

	@FXML
	void btClickLi(ActionEvent event) {
		limpar();
		dataR.setDisable(false);

	}

	@FXML
	void btClickSa(ActionEvent event) {
		// validando os campos
		if (dataR.getValue() == null) {
			JOptionPane.showMessageDialog(null, "Informe uma data", "Informe", JOptionPane.ERROR_MESSAGE);
			dataR.requestFocus();
		} else if (descTarefa.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe uma tarefa", "Informe", JOptionPane.ERROR_MESSAGE);
			descTarefa.requestFocus();
		} else if (comenta.getText().isEmpty()) {

			JOptionPane.showMessageDialog(null, "Digite o seu Comentario", "Informe", JOptionPane.ERROR_MESSAGE);
			comenta.requestFocus();
		} else if (dataR.getValue().isBefore(LocalDate.now())) {
			JOptionPane.showMessageDialog(null, "A data não deve ser anterior a data atual", "Data Inválida",
					JOptionPane.ERROR_MESSAGE);
			dataR.requestFocus();
		} else {
			// verifica se a tarefa e nula
			if (tarefa == null) {
				// instanciando a tarefa
				tarefa = new Tarefa();
				tarefa.setStatus(StatusTarefa.ABERTA);
				tarefa.setDataCriacao(LocalDate.now());
			}
			// popular a tarefa fazer com que ele carregue as informações do fourmulario
			tarefa.setDataLimite(dataR.getValue());
			tarefa.setDescricao(descTarefa.getText());
			tarefa.setComentarios(comenta.getText());

			// TODO inserir no banca de dados

			System.out.println(tarefa.formatToSave());
			try {
				if (tarefa.getId() == 0) {
					TarefaIO.insert(tarefa);
				} else {

					TarefaIO.saveTarefas(tarefas);

				}
				// limpar os campos
				limpar();
				carregarTarefas();
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Arquivo não encontrado" + e.getMessage(), "Erro",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erro ao Gravar" + e.getMessage(), "Erro",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}

	}

	@FXML
	void miExport(ActionEvent event) {
		FileFilter filter = new FileNameExtensionFilter("Arquivos Html", "html", "htm");

		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(filter);
		chooser.showSaveDialog(null);
		if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File arqSelecionado = chooser.getSelectedFile();

			if (!arqSelecionado.getAbsolutePath().endsWith(".html")) {
				arqSelecionado = new File(arqSelecionado + ".html");

			}
			try {
				TarefaIO.exportHtml(tarefas, arqSelecionado);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erro ao exportar Html " + e.getMessage(), "Erro",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}

	@FXML
	void miSair(ActionEvent event) {
		System.exit(0);
		JOptionPane.showConfirmDialog(null, "Deseja Fechar ?");
	}

	@FXML
	void miSobre(ActionEvent event) throws IOException {
		try {
			AnchorPane root = (AnchorPane) FXMLLoader
					.load(getClass().getResource("/br/luanfx/todolist/view/Sobre.fxml"));
			Scene scene = new Scene(root, 360, 400);
			scene.getStylesheets().add(getClass().getResource("../view/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initStyle(StageStyle.UNDECORATED);
			// modo modal 
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
		///
	}

	private void limpar() {
		tarefa = null;

		dataR.setValue(null);
		descTarefa.setText(null);
		comenta.setText(null);
		statusT.setText(null);
		dataR.requestFocus();
		btExcluir.setDisable(true);
		btAdiar.setDisable(true);
		btConcluir.setDisable(true);
		tfID.setDisable(true);
		tfID.setText(null);
		btSalvar.setDisable(false);
		descTarefa.setDisable(false);
		comenta.setDisable(false);
		dtConcluida.setDisable(true);
		dtConcluida.setValue(null);
		leitor();

		// para quando clicar no limpar ele parar de selecionar a tarefa no TableView//
		tvTarefa.getSelectionModel().clearSelection();
		// formas de limpar tipo texto
		// setText("") 2 setText(null) 3 .clear()
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btExcluir.setDisable(true);
		btAdiar.setDisable(true);
		btConcluir.setDisable(true);
		// definir os parametros da colunas do tabView
		tcData.setCellValueFactory(new PropertyValueFactory<>("dataLimite"));
		tcTarefa.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		// ajusta a formatação da data da tarefa. em sua coluna data!!!! importante/!!!!
		tcData.setCellFactory(call -> {
			return new TableCell<Tarefa, LocalDate>() {
				@Override
				protected void updateItem(LocalDate item, boolean empty) {
					DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
					if (!empty) {
						setText(item.format(fmt));
					} else
						setText("");
				}
			};

		});
		tvTarefa.setRowFactory(call -> new TableRow<Tarefa>() {
			protected void updateItem(Tarefa item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null) {
					setStyle("");
				} else if (item.getStatus() == StatusTarefa.CONCLUIDA) {
					setStyle("-fx-background-color: #0f0");
				} else if (item.getDataLimite().isBefore(LocalDate.now())) {
					setStyle("-fx-background-color: tomato");
				} else if (item.getStatus() == StatusTarefa.ADIADA) {
					setStyle("-fx-backgroun-color: #ff5");
				} else {
					setStyle("-fx-backgroun-color: cyan");
				}
			};
		});

		// evento de seleção item na tabela
		tvTarefa.getSelectionModel().selectedItemProperty().addListener(this);
		dtConcluida.setOpacity(1);
		carregarTarefas();
		leitor();
	}

	public void carregarTarefas() {
		try {
			tarefas = TarefaIO.read();
			tvTarefa.setItems(FXCollections.observableArrayList(tarefas));
			tvTarefa.refresh();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao carregar as tareafas" + e.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	@Override
	public void changed(ObservableValue<? extends Tarefa> observable, Tarefa oldValue, Tarefa newValue) {
		// passar a referencia para a variável Global!!!
		tarefa = newValue;
		if (tarefa != null) {
			descTarefa.setText(tarefa.getDescricao());
			comenta.setText(tarefa.getComentarios());
			dataR.setValue(tarefa.getDataCriacao());
			dtConcluida.setValue(tarefa.getDataConcluida());
			dtConcluida.setVisible(true);

			statusT.setText(tarefa.getStatus() + "");
			tfID.setText(tarefa.getId() + " ");
			dataR.setDisable(true);
			dataR.setOpacity(1);
			btExcluir.setDisable(false);

			btExcluir.setDisable(false);
			btAdiar.setDisable(false);
			btConcluir.setDisable(false);
			btSalvar.setDisable(false);
			if (tarefa.getStatus() == StatusTarefa.ADIADA) {
				btAdiar.setDisable(true);

				descTarefa.setDisable(false);
				comenta.setDisable(false);
				descTarefa.setOpacity(1);
				comenta.setOpacity(1);
				return;
			} else if (tarefa.getStatus() == StatusTarefa.CONCLUIDA) {
				btSalvar.setDisable(true);
				btAdiar.setDisable(true);
				btConcluir.setDisable(true);
				descTarefa.setDisable(true);
				comenta.setDisable(true);
				descTarefa.setOpacity(1);
				comenta.setOpacity(1);

			} else {
				btExcluir.setDisable(false);
				btAdiar.setDisable(false);
				btConcluir.setDisable(false);
			}
		}

	}

	public void leitor() {

		try {
			tfID.setText(TarefaIO.codId() + "");
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	}
}
