package br.edu.ifpb.si.pd.chat.views;

import java.rmi.RemoteException;

import br.edu.ifpb.si.pd.chat.MainApp;
import br.edu.ifpb.si.pd.chat.utils.MensagemUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RenameDialogController {
	private MainApp mainApp;
	private Stage janelaRenameDialog;
	@FXML
	private TextField nome;
	
	@FXML
	public void salvar(){
		if(!this.nome.getText().isEmpty()){
			try {
				this.mainApp.getServidorIR().rename(this.mainApp.getUsuario(), this.nome.getText());
				this.janelaRenameDialog.close();
			} catch (RemoteException e) {
				MensagemUtil.alertMessage("Nome existente, digite outro!");
			}
		}else{
			MensagemUtil.alertMessage("Digite um novo nome!");
		}
	}
	
	@FXML
	public void cancelar(){
		this.janelaRenameDialog.close();
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	public void setStage(Stage janelaRenameDialog) {
		this.janelaRenameDialog = janelaRenameDialog;
	}
}
