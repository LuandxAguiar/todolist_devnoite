package br.luanfx.todolist.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.luanfx.todolist.model.StatusTarefa;
import br.luanfx.todolist.model.Tarefa;
import sun.invoke.empty.Empty;

public class TarefaIO {
	private static final String FOLDER = 
			System.getProperty("user.home")+"/todlist";
	private static final String FILE_ID =
				FOLDER + "/id.csv";
	private static final String FILE_TAREFA =
			FOLDER + "/tarefa.csv";
	public static void createFiles() {
		try {
		File folder = new  File(FOLDER);
		File fileId = new File(FILE_ID);
		File fileTarefa = new File(FILE_TAREFA);
		
		if(!folder.exists()) {
			folder.mkdir();
			fileTarefa.createNewFile();
			fileId.createNewFile();
			FileWriter writer = new FileWriter(fileId);
			//writer pra escrever no txt = banco de dados, usar aspa para dizer o numero. pois se nao usa-la ele pegara o caracter que e correspondente com (ASC I..)
			writer.write("1");
			writer.close();
			
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void insert (Tarefa tarefa) throws FileNotFoundException, IOException {
		File arqTarefa = new File(FILE_TAREFA);
		File arqId = new File (FILE_ID);
		Scanner sc = new Scanner(arqId);
		tarefa.setId(sc.nextLong());
		sc.close();
		FileWriter writer = new FileWriter(arqTarefa, true);
		writer.append(tarefa.formatToSave());
		writer.close();
		// gravar novo id no arquivo
		writer = new FileWriter(arqId);
		long proxId = tarefa.getId()+1;
		writer.write(proxId+"");
		writer.close();
		
		
	}
	
	public static List<Tarefa> read() throws IOException{
		File arqTarefa = new File(FILE_TAREFA);
		List<Tarefa> tarefas = new ArrayList<>();
		FileReader reader = new FileReader(arqTarefa);
		BufferedReader buff = new BufferedReader(reader);
		String linha;
		while((linha = buff.readLine()) !=null) {
			//transformando linha em em vetor de string
			String[] vetor = linha.split(";");
			Tarefa t = new Tarefa();
			// conversão de string para long = Long.parseLong
			t.setId(Long.parseLong(vetor[0]));
			DateTimeFormatter padraoData 
				= DateTimeFormatter.ofPattern("dd/MMM/yyyy");
			t.setDataCriacao(LocalDate.parse(vetor[1], padraoData));
			// dataLimite  
			
			t.setDataLimite(LocalDate.parse(vetor[2], padraoData));
			//dataconcluida
			if(!vetor[3].isEmpty()) {
				t.setDataConcluida(LocalDate.parse(vetor[3], padraoData));
			
			}
			//descrição 
			t.setDescricao(vetor[4]);
			//comentarios
			t.setComentarios(vetor[5]);
			//status
			int indStatus = Integer.parseInt(vetor[6]);
			t.setStatus(StatusTarefa.values()[indStatus]);
			tarefas.add(t);
					
		}
		reader.close();
		buff.close();
		System.out.println(tarefas.size());
		return tarefas;
	}
}
