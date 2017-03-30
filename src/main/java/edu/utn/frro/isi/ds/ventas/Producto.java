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
	
	protected Producto() {
	}

	public Producto(String descripcion, Double precio) {
		this.descripcion = descripcion;
		this.precio = precio;
	}
	public Producto(String descripcion, Double precio, String urlImagen) {
		this( descripcion,  precio);
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


}
