package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.ForeignKey;
import javax.persistence.Table;

@Entity
@Table(name="linea_pedido")
public class LineaPedido {

	private static int nserie=1;
	@Id
	public int lineaPedidoId;
	@ManyToOne
	@JoinColumn(name = "id_pedido", nullable = false, foreignKey=@ForeignKey(name="Pedido_ID"))
	public Pedido pedido;
	@OneToOne
	@JoinColumn(name = "id_producto", nullable = false, foreignKey=@ForeignKey(name="Producto_ID"))
	public Producto producto;
	@Column(name = "nombre_producto", nullable = false)
	public String nombreProducto;
	@Column(name = "imagen", nullable = true)
	public String imgProducto;
	@Column(name = "cantidad", nullable = false)
	public int cantidad;
	
	
	
	public LineaPedido() {
		super();
		this.lineaPedidoId=nserie++;
	}

	
	
	public LineaPedido(Pedido pedido, Producto producto, String nombreProducto, String imgProducto, int cantidad) {
		super();
		this.lineaPedidoId=nserie++;
		this.pedido = pedido;
		this.producto = producto;
		this.nombreProducto = nombreProducto;
		this.imgProducto = imgProducto;
		this.cantidad = cantidad;
	}

	



	public LineaPedido(Producto producto, String nombreProducto, String imgProducto, int cantidad) {
		super();
		this.producto = producto;
		this.nombreProducto = nombreProducto;
		this.imgProducto = imgProducto;
		this.cantidad = cantidad;
	}



	public int getCantidad() {
		return cantidad;
	}



	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}


	
	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	
	public String getImgProducto() {
		return imgProducto;
	}

	public void setImgProducto(String imgProducto) {
		this.imgProducto = imgProducto;
	}

	




	@Override
	public String toString() {
		return "LineaPedido [lineaPedidoId=" + lineaPedidoId + ", pedido=" + pedido + ", producto=" + producto
				+ ", nombreProducto=" + nombreProducto + ", imgProducto=" + imgProducto + ", cantidad=" + cantidad
				+ "]";
	}



	public static int getNserie() {
		return nserie;
	}


	public static void setNserie(int nserie) {
		LineaPedido.nserie = nserie;
	}


	public int getLineaPedidoId() {
		return lineaPedidoId;
	}


	public void setLineaPedidoId(int lineaPedidoId) {
		this.lineaPedidoId = lineaPedidoId;
	}



	public Pedido getPedido() {
		return pedido;
	}



	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}



	public Producto getProducto() {
		return producto;
	}



	public void setProducto(Producto producto) {
		this.producto = producto;
	}


	
	public int getId_producto() {
		return this.producto.getId();
	}

		
	
}
