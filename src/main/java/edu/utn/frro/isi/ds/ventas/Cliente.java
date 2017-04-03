package edu.utn.frro.isi.ds.ventas;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity //no hay mapeo, se asume que la tabla se llama clientes y los campos tienen los mismos nombres que los atributos.
public class Cliente {

	@Id
	@GeneratedValue
	private Long id;

	private String nombre;
	private String apellido;
	private String profesion;

	public Cliente() {
	}

	public Cliente(String nombre, String apellido, String profesion) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.profesion = profesion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getProfesion() {
		return profesion;
	}

	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}

	
}
