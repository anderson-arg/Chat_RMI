package br.edu.ifpb.si.pd.chat.views;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

import br.edu.ifpb.si.pd.chat.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class RootLayoutController {

	private MainApp mainApp;
	
	@FXML
	private MenuItem renomear;

	@FXML
	public void fechar(){
		if(this.mainApp.getUsuario() != null){
			try {
				this.mainApp.getServidorIR().bye(this.mainApp.getUsuario());
			} catch (RemoteException | ServerNotActiveException e) {
				e.printStackTrace();
			}
		}
		System.exit(0);
		
	}
	
	@FXML
	public void renomear(){
		this.mainApp.showRenameDialog();
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public MenuItem getRenomear() {
		return renomear;
	}
	
}
