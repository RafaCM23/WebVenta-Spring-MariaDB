package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.model.Cantidades;
import com.example.demo.model.LineaPedido;
import com.example.demo.model.Pedido;
import com.example.demo.model.Producto;

import com.example.demo.repository.LineaPedidoRepository;
import com.example.demo.repository.PedidoRepository;
import com.example.demo.repository.ProductoRepository;


@Service
public class PedidoService {
	
	
	
	public static final Producto p1 = new Producto(1,"Steam Gift Card 25€");
	public static final Producto p2 = new Producto(2,"Amazon Gift Card 25€");
	public static final Producto p3 = new Producto(3,"Google Play Gift Card 25€");
	public static final Producto p4 = new Producto(4,"Xbox  Gift Card 25€");
	public static final Producto p5 = new Producto(5,"eShop Gift Card 25€");
	public static final Producto p6 = new Producto(6,"PS Store Gift Card 25€");
	
	@Autowired
	ProductoRepository productoREPO;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	LineaPedidoRepository lineaPedidoREPO;
	
	@Autowired
	PedidoRepository pedidoREPO;
	
	public Pedido creaPedido(Cantidades cant) {
		
		 Pedido nuevo = new Pedido();
		 LineaPedido aux =null;
		 pedidoREPO.save(nuevo);
		
		 List<Producto> todos = productoREPO.findAll();
		 
		 if(cant.getCant1()!=0) {
			 Producto p = todos.get(0);
			 aux = new LineaPedido(nuevo,p,p.getNombre(),p.getImgProducto(),cant.getCant1());
			 lineaPedidoREPO.save(aux);
			 nuevo.addLinea(aux);
			
		 }
		 
		 if(cant.getCant2()!=0) {
			 Producto p = todos.get(1);
			 aux = new LineaPedido(nuevo,p,p.getNombre(),p.getImgProducto(),cant.getCant2());
			 lineaPedidoREPO.save(aux);
			 nuevo.addLinea(aux);
			 
		 }
		 
		 if(cant.getCant3()!=0) {
			 Producto p = todos.get(2);
			 aux = new LineaPedido(nuevo,p,p.getNombre(),p.getImgProducto(),cant.getCant3());
			 lineaPedidoREPO.save(aux);
			 nuevo.addLinea(aux);
			
		 }
		 
		 if(cant.getCant4()!=0) {
			 Producto p = todos.get(3);
			 aux = new LineaPedido(nuevo,p,p.getNombre(),p.getImgProducto(),cant.getCant4());
			 lineaPedidoREPO.save(aux);
			 nuevo.addLinea(aux);
			 
		 }
		 
		 if(cant.getCant5()!=0) {
			 Producto p = todos.get(4);
			 aux = new LineaPedido(nuevo,p,p.getNombre(),p.getImgProducto(),cant.getCant5());
			 lineaPedidoREPO.save(aux);
			 nuevo.addLinea(aux);
			 
		 }
		 
		 
		 if(cant.getCant6()!=0) {
			 Producto p = todos.get(5);
			 aux = new LineaPedido(nuevo,p,p.getNombre(),p.getImgProducto(),cant.getCant6());
			 lineaPedidoREPO.save(aux);
			 nuevo.addLinea(aux);
		 }
		 if (nuevo.getlineas().isEmpty()) {
			pedidoREPO.delete(nuevo);
			return null;
		}else {
			pedidoREPO.save(nuevo);
			return nuevo;
		}
		
		
	}
	
	public void guardaPedido(Pedido p) {
		pedidoREPO.save(p);
	}
	
	public void borraPedido(Pedido p) {
		pedidoREPO.delete(p);
	}
	
	
	

	
	
	
}
