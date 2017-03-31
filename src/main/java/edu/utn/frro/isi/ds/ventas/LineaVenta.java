package edu.utn.frro.isi.ds.ventas;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class LineaVenta {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(name="producto_id")
	private Producto producto=null;
	private Integer cantidad;
	private Double precioProductoCobrado;
	
	public LineaVenta(Producto producto, Integer cantidad) {
		this.producto = producto;
		this.cantidad = cantidad;
	}	
	
	public LineaVenta() {
	}


	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	public String getDescripcion() {
		if (producto==null)
			return null;
		return producto.getDescripcion();
	}
	public Long getProductoId() {
		if (producto==null)
			return null;
		return producto.getId();
	}
	public Double getPrecio() {
		if (precioProductoCobrado != null) {
			return precioProductoCobrado;
		}
		if (producto==null)
			return null;
		return producto.getPrecio();
	}


	public Double getSubtotal() {
		if (producto==null)
			return 0.0;
		return producto.getPrecio() * cantidad;
	}

	public void comprar() {
		producto.reducirStock(cantidad);
		precioProductoCobrado = producto.getPrecio();
	}
}
