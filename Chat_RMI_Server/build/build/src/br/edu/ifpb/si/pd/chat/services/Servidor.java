package br.edu.ifpb.si.pd.chat.services;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ifpb.si.pd.chat.interfaces.ServidorIR;
import br.edu.ifpb.si.pd.chat.interfaces.UsuarioIR;
import br.edu.ifpb.si.pd.chat.utils.DateUtil;

public class Servidor extends UnicastRemoteObject implements ServidorIR{
	private static final long serialVersionUID = 1L;
	
	private Map<String, UsuarioIR> usuarios;
	
	public Servidor() throws RemoteException {
		this.usuarios = new HashMap<String, UsuarioIR>();
	}
	
	public void adicionarUsuario(UsuarioIR usuario) throws RemoteException{
		if(usuario.getNome() == null || usuario.getNome().isEmpty() || this.existe(usuario)){
			throw new RemoteException();
		}else{
			this.usuarios.put(usuario.getNome(), usuario);
			System.out.println(usuario.getNome()+" entrou no chat!");
		}
	}
	
	public boolean existe(UsuarioIR usuario) throws RemoteException{
		if(this.usuarios.containsKey(usuario.getNome())){
			return true;
		}else{
			return false;
		}
	}
	
	public void enviarParaTodos(UsuarioIR u) throws RemoteException, ServerNotActiveException{
		for(Map.Entry<String, UsuarioIR> kv : this.usuarios.entrySet()){
			if(!u.equals(kv.getValue()))kv.getValue().mensagem((u.getNome()+" diz: "+u.getMsg()+" "+DateUtil.dataAtual()));
		}
	}
	
	public void listarUsuarios(UsuarioIR usuario) throws RemoteException{
		String msg="Usuarios Online:"+this.usuarios.size()+"\n";
		for(String s : this.usuarios.keySet()){
			msg+= s+"\n";
		}
		usuario.mensagem(msg);
	}
	
	public void bye(UsuarioIR u) throws RemoteException, ServerNotActiveException{
		for(Map.Entry<String, UsuarioIR> kv : this.usuarios.entrySet()){
			if(!u.equals(kv.getValue()))kv.getValue().mensagem((u.getNome()+" saiu do chat... "+DateUtil.dataAtual()));
		}
		this.usuarios.remove(u.getNome());
		u.mensagem("Você saiu do chat!");
	}
	
	public void notificarEntrada(UsuarioIR u) throws RemoteException, ServerNotActiveException{
		for(Map.Entry<String, UsuarioIR> kv : this.usuarios.entrySet()){
			if(!u.equals(kv.getValue()))kv.getValue().mensagem((u.getNome()+" entrou no chat... "+DateUtil.dataAtual()));
		}
	}
	
	public void sendUser(UsuarioIR u, String nome) throws RemoteException, ServerNotActiveException{
		for(Map.Entry<String, UsuarioIR> kv : this.usuarios.entrySet()){
			if(nome.equals(kv.getKey())){
				kv.getValue().mensagem((u.getNome()+" diz[privado]: "+u.getMsg()+" "+DateUtil.dataAtual()));
			}
		}
	}
	
	public void rename(UsuarioIR u, String novoNome) throws RemoteException{
		if(existe(u) && !this.usuarios.containsKey(novoNome)){
			this.usuarios.remove(u.getNome());
			u.setNome(novoNome);
			this.usuarios.put(u.getNome(), u);
		}else{
			throw new RemoteException();
		}
	}

	public List<String> getUsuariosOnline() {
		List<String> lista = new ArrayList<String>();
		if(this.usuarios.size() > 0)
			usuarios.forEach((k,v) -> lista.add(k));
		return lista;
	}

	public void setUsuarios(Map<String, UsuarioIR> usuarios) {
		this.usuarios = usuarios;
	}
}
