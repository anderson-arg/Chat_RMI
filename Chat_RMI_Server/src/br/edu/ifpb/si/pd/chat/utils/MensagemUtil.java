package br.edu.ifpb.si.pd.chat.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MensagemUtil {
	public static void alertMessage(String text){
		Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Mensagem");
        alert.setHeaderText("Alerta!");
        alert.setContentText(text);
        alert.showAndWait();
	}
}
