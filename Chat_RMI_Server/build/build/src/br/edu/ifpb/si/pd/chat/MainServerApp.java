package br.edu.ifpb.si.pd.chat;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import br.edu.ifpb.si.pd.chat.services.Servidor;
import br.edu.ifpb.si.pd.chat.utils.MensagemUtil;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainServerApp extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			Servidor s = new Servidor();
			Naming.bind("servidor", s);
			MensagemUtil.alertMessage("Servidor Online...");
			System.exit(0);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
