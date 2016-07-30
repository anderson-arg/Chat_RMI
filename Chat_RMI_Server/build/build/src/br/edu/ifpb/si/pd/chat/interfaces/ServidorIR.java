package br.edu.ifpb.si.pd.chat.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.List;

public interface ServidorIR extends Remote{
	public void adicionarUsuario(UsuarioIR u) throws RemoteException;
	public boolean existe(UsuarioIR usuario) throws RemoteException;
	public void enviarParaTodos(UsuarioIR u) throws RemoteException, ServerNotActiveException;
	public void listarUsuarios(UsuarioIR usuario) throws RemoteException;
	public void bye(UsuarioIR u) throws RemoteException, ServerNotActiveException;
	public void notificarEntrada(UsuarioIR u) throws RemoteException, ServerNotActiveException;
	public void sendUser(UsuarioIR u, String nome) throws RemoteException, ServerNotActiveException;
	public void rename(UsuarioIR u, String novoNome) throws RemoteException;
	public List<String> getUsuariosOnline() throws RemoteException;
}
