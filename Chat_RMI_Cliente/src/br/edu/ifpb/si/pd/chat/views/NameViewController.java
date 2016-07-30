package br.edu.ifpb.si.pd.chat.views;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

import br.edu.ifpb.si.pd.chat.MainApp;
import br.edu.ifpb.si.pd.chat.models.Usuario;
import br.edu.ifpb.si.pd.chat.utils.MensagemUtil;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class NameViewController {

	private MainApp mainApp;
	@FXML
	private TextField nome;
	
	public NameViewController() {
	}
	
	@FXML
	public void initialize(){
	}
	
	@FXML
	public void adicionarUsuario(){
		if(!this.nome.getText().isEmpty()){
			try {
				this.mainApp.setUsuario(new Usuario(this.nome.getText()));
				this.mainApp.getRootLayoutController().getRenomear().setDisable(false);
				this.mainApp.showHomeView();
			} catch (RemoteException e) {
				MensagemUtil.alertMessage("Nome existente, digite outro!");
			} catch (ServerNotActiveException e) {
				e.printStackTrace();
			}
		}else{
			MensagemUtil.alertMessage("Digite um nome!");
		}
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
}
