package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Pedido;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;

@Service
public class UsuarioService {

@Autowired
UsuarioRepository usuarioREPO;	

	
	public List<Usuario> findAll(){
		return this.usuarioREPO.findAll();
		
	}
	
	public void guardaPedido(Pedido p, String nk) {
		Usuario user= getByNick(nk);
		Collections.reverse(user.getPedidos());
		user.addPedido(p);
		Collections.reverse(user.getPedidos());
		usuarioREPO.save(user);
	}
	
	public void guardaUsuario(Usuario user) {
		usuarioREPO.save(user);
	}
	
	public Usuario getByNick(String nk) {
		
		Usuario buscado= new Usuario( nk);
		List<Usuario> users = this.findAll();
		if(users.indexOf(buscado)!=-1) {
			return users.get(users.indexOf(buscado));
		}else {
			return  null;
		}
		
	}
	
	public List<Pedido> getPedidosByNick(String nk){
		
		Usuario user = getByNick(nk);
		
		return user.getPedidos();
	}
	
	public int autenticar(String nk, String psw) {
		int respuesta=-1;
		Usuario buscado=getByNick(nk);
		if(buscado!=null && buscado.getPassword().equals(psw)) {			
				respuesta=1;
			
		}
		return respuesta;
	}
	
	

}
