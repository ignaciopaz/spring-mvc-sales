package edu.utn.frro.isi.ds.ventas;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Producto {

	@Id
	@GeneratedValue
	private Long id;

	private String descripcion;
	private Double precio;	
	private String urlImagen;
	private Integer cantidadStock;
	
	protected Producto() {
	}

	public Producto(String descripcion, Double precio, Integer cantidadStock) {
		this.descripcion = descripcion;
		this.precio = precio;
		this.cantidadStock=cantidadStock;
	}
	public Producto(String descripcion, Double precio, Integer cantidadStock, String urlImagen) {
		this( descripcion,  precio, cantidadStock);
		this.urlImagen = urlImagen;
	}
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Long getId() {
		return id;
	}

	public String getUrlImagen() {
		return urlImagen;
	}

	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}
	
	public boolean isTieneStock() {
		return isTieneStock(1);
	}
	
	public boolean isTieneStock(Integer cantidad) {
		return cantidadStock >= cantidad;
	}

	public Integer getCantidadStock() {
		return cantidadStock;
	}

	public void setCantidadStock(Integer cantidadStock) {
		this.cantidadStock = cantidadStock;
	}

	public void reducirStock(Integer cantidad) {
		cantidadStock -= cantidad;		
	}
	
	public boolean equals(Object o) {
		if (o instanceof Producto && o !=null) {
			Producto other = (Producto) o;
			return other.getId().equals(getId());
		}
		return false;
	}


}
