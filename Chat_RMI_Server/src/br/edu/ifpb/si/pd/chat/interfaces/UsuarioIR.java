package br.edu.ifpb.si.pd.chat.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UsuarioIR extends Remote{
	public void setNome(String nome) throws RemoteException;
	public void setMsg(String msg) throws RemoteException;
	public String getNome() throws RemoteException;
	public String getMsg() throws RemoteException;
	public void mensagem(String msg) throws RemoteException;
}
