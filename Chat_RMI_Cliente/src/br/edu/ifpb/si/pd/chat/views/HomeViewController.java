package br.edu.ifpb.si.pd.chat.views;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

import br.edu.ifpb.si.pd.chat.MainApp;
import br.edu.ifpb.si.pd.chat.utils.MensagemUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class HomeViewController {

	private MainApp mainApp;
	@FXML
	private ListView<String> usuarioListView;
	@FXML
	private ObservableList<String> usuarios;
	@FXML
	private ListView<String> mensagemListView;
	@FXML
	private ObservableList<String> mensagens;
	@FXML
	private TextArea textoTextArea;
	
	public HomeViewController() {
		this.usuarios = FXCollections.observableArrayList();
		this.mensagens = FXCollections.observableArrayList();
	}
	
	@FXML
	public void initialize(){
		this.addListeners();
		this.mensagemListView.setItems(this.mensagens);
		this.usuarios.add("Todos");
	}
	
	public void addListeners(){
		/*this.usuarioListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(final MouseEvent mouseEvent) {
		    	MensagemUtil.alertMessage(usuarioListView.getSelectionModel().getSelectedItem());
		    }
		});*/
	}
	
	public void popularUsuarios(){
		try {
			this.usuarios.addAll(this.mainApp.getServidorIR().getUsuariosOnline());
			this.usuarioListView.setItems(this.usuarios);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void atualizarListaUsuarios(){
		this.usuarios = FXCollections.observableArrayList();
		this.usuarios.add("Todos");
		this.popularUsuarios();
	}
	
	@FXML
	public void enviar(){
		if(this.textoTextArea.getText() != null){
			if(this.usuarioListView.getSelectionModel().getSelectedItem() != null){
				if(this.usuarioListView.getSelectionModel().getSelectedItem().equals("Todos")){
					this.enviarParaTodos();
				}else{
					this.sendUser(this.usuarioListView.getSelectionModel().getSelectedItem());
				}
				this.mensagens.add("Eu -> "+this.textoTextArea.getText());
				this.textoTextArea.setText(null);
			}else{
				MensagemUtil.alertMessage("Selecione o modo/pessoa para enviar a mensagem!");
			}
		}
	}
	
	public void enviarParaTodos(){
		try {
			this.mainApp.getUsuario().setMsg(this.textoTextArea.getText());
			this.mainApp.getServidorIR().enviarParaTodos(this.mainApp.getUsuario());
		} catch (RemoteException | ServerNotActiveException e) {
			e.printStackTrace();
		}
	}
	
	public void sendUser(String nome){
		try {
			this.mainApp.getUsuario().setMsg(this.textoTextArea.getText());
			this.mainApp.getServidorIR().sendUser(this.mainApp.getUsuario(), nome);
		} catch (RemoteException | ServerNotActiveException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void rename(){
		try {
			this.mainApp.getServidorIR().rename(this.mainApp.getUsuario(), "novoNome");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void bye(){
		try {
			this.mainApp.getServidorIR().bye(this.mainApp.getUsuario());
			this.mainApp.setUsuario(null);
			this.mainApp.getRootLayoutController().getRenomear().setDisable(true);
			this.mainApp.showNameView();
		} catch (RemoteException | ServerNotActiveException e) {
			e.printStackTrace();
		}
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.mainApp.getUsuario().setMensagens(this.mensagens);
		this.popularUsuarios();
	}
	
	// função para deixar dinâmico
	/*Platform.runLater(new Runnable() {

	    @Override
	    public void run() {
	        listView.scrollTo(N);
	        listView.getSelectionModel().select(N);
	    }
	});*/
	
}
