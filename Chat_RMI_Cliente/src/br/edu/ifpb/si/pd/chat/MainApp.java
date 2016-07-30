package br.edu.ifpb.si.pd.chat;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

import br.edu.ifpb.si.pd.chat.interfaces.ServidorIR;
import br.edu.ifpb.si.pd.chat.models.Usuario;
import br.edu.ifpb.si.pd.chat.utils.MensagemUtil;
import br.edu.ifpb.si.pd.chat.views.HomeViewController;
import br.edu.ifpb.si.pd.chat.views.NameViewController;
import br.edu.ifpb.si.pd.chat.views.RenameDialogController;
import br.edu.ifpb.si.pd.chat.views.RootLayoutController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private ServidorIR servidorIR;
	private Usuario usuario;
	private RootLayoutController rootLayoutController;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("MSN");
		this.primaryStage.setResizable(false);
		
		this.initEvent();
		
		this.initServer();
		
		this.initRootLayout();
		
		this.showNameView();
	}
	
	public void initEvent(){
		this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                t.consume();
                if(usuario != null){
                	try {
						servidorIR.bye(usuario);
					} catch (RemoteException | ServerNotActiveException e) {
						e.printStackTrace();
					}
                }
                System.exit(0);
            }
        });
	}
	
	public void initServer(){
		try {
			this.servidorIR = (ServidorIR) Naming.lookup("servidor");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			MensagemUtil.alertMessage("Sem conexão com o servidor!");
			System.exit(0);
		}
	}
	
	public void initRootLayout(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("views/rootLayout.fxml"));
			this.rootLayout = loader.load();
			
			Scene scene = new Scene(this.rootLayout);
			this.primaryStage.setScene(scene);
			
			this.rootLayoutController = loader.getController();
			rootLayoutController.setMainApp(this);
			
			this.primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showHomeView(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("views/homeView.fxml"));
			AnchorPane homeView = loader.load();
			this.rootLayout.setCenter(homeView);
			HomeViewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void showNameView(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("views/nameView.fxml"));
			AnchorPane nameView = loader.load();
			this.rootLayout.setCenter(nameView);
			this.rootLayout.getCenter().setTranslateX(((this.rootLayout.getWidth() - nameView.getPrefWidth()) / 2));
			this.rootLayout.getCenter().setTranslateY(((this.rootLayout.getPrefHeight() - nameView.getPrefHeight()) / 2));
			NameViewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void showRenameDialog(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("views/renameDialog.fxml"));
			AnchorPane renameDialog = loader.load();
			
			Stage janelaRenameDialog = new Stage();
			janelaRenameDialog.setTitle("Renomear");
			janelaRenameDialog.setResizable(false);
			janelaRenameDialog.initModality(Modality.WINDOW_MODAL);
			janelaRenameDialog.initOwner(this.primaryStage);
			Scene scene = new Scene(renameDialog);
			janelaRenameDialog.setScene(scene);
			
			RenameDialogController controller = loader.getController();
			controller.setMainApp(this);
			controller.setStage(janelaRenameDialog);
			
			janelaRenameDialog.showAndWait();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public ServidorIR getServidorIR() {
		return servidorIR;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	public RootLayoutController getRootLayoutController() {
		return rootLayoutController;
	}

	public void setUsuario(Usuario usuario) throws RemoteException, ServerNotActiveException {
		if(usuario != null){
			this.usuario = usuario;
			this.servidorIR.adicionarUsuario(this.usuario);
			this.servidorIR.notificarEntrada(usuario);
		}else{
			this.usuario = null;
		}
	}
	
}
