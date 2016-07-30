package br.edu.ifpb.si.pd.chat.models;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import br.edu.ifpb.si.pd.chat.interfaces.UsuarioIR;
import javafx.collections.ObservableList;

public class Usuario extends UnicastRemoteObject implements UsuarioIR {
	private static final long serialVersionUID = 1L;
	private String nome;
	private String msg;
	private ObservableList<String> mensagens;
	
	public Usuario(String nome) throws RemoteException {
		this.nome = nome;
	}
	
	public void mensagem(String msg) throws RemoteException{
		System.out.println(msg);
		this.mensagens.add(msg);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public void setMensagens(ObservableList<String> mensagens){
		this.mensagens = mensagens;
	}
}
