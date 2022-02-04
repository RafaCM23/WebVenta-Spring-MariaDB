package com.example.demo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@SuppressWarnings("rawtypes")
@Entity
@Table(name="pedidos")
public class Pedido implements Comparable{
	
	private static int nserie=1;
	@Id
	private int id;
	@OneToMany(fetch=FetchType.EAGER)
	private List<LineaPedido> lineas;
	@Column(name="fecha")
	private Date fecha;
	
	


	public Pedido() {
		this.lineas=new ArrayList<LineaPedido>();
		this.id = nserie++;
		this.fecha=new Date(System.currentTimeMillis());
	}


	public Pedido(int id, List<LineaPedido> lineas) {
		super();
		this.id = nserie++;
		this.lineas = lineas;
		this.fecha=new Date(System.currentTimeMillis());
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		return id == other.id;
	}

	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	
	public List<LineaPedido> getlineas() {
		return lineas;
	}
	public String imprimeLinea(int posicion) {
		return lineas.get(posicion).toString();
	}


	public void setlineas(List<LineaPedido> lineas) {
		this.lineas = lineas;
	}

	public void addLinea(LineaPedido p) {
		this.lineas.add(p);
	}

	public void borrarLinea(int idlinea) {
		this.lineas.remove(idlinea);
	}

	@Override
	public String toString() {
		StringBuilder respuesta = new StringBuilder();
		respuesta.append("Pedido= ID:"+this.id+" \n");
		for (int i = 0; i < lineas.size(); i++) {
			respuesta.append(lineas.get(i).toString()+ "\n");
			
		}
		return respuesta.toString();
	}


	public static int getNserie() {
		return nserie;
	}


	public static void setNserie(int nserie) {
		Pedido.nserie = nserie;
	}
	
	
	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	


	@Override
	public int compareTo(Object o) {
		int resultado=0;
		
		if(this.fecha.after(((Pedido)o).getFecha())){
			resultado=-1;
		}
		if(this.fecha.before(((Pedido)o).getFecha())){
			resultado=1;
		}
		return resultado;
	}
	
	
	
}
