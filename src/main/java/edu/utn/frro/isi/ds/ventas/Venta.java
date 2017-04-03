package edu.utn.frro.isi.ds.ventas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity @Table(name="ventas") //Ejemplo de mapeo: de la clase Venta a tabla ventas, no hace falta si la tabla tiene el mismo nombre que la clase.
public class Venta {

	@Id
	@GeneratedValue
	@Column(name="id_venta", nullable=false) //Ejemplo de mapeo: a campo id_vanta de la tabla ventas, no hace falta si el campo tiene el mismo nombre que el atributo.
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_cliente", nullable=false) //Mapeo: nombre del campo en la tabla ventas que será la FK a la tabla cliente
	private Cliente cliente;

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
	@JoinColumn(name="id_venta") //Mapeo: nombre del campo en la tabla lineaventa que será la FK a la tabla ventas
	private List<LineaVenta> lineas = new ArrayList<LineaVenta>();
	
	@Column(name="fecha_venta", nullable=false)
	private Date fechaVenta;
	
	protected Venta() {
		this.fechaVenta=new Date();
	}

	protected Venta(Cliente cliente) {
		this();
		this.cliente=cliente;
	}

	public void agregarProducto(Producto producto, Integer cantidad) throws RuntimeException {
		if (getProductos().contains(producto)) {
			throw new RuntimeException("El producto "+producto.getDescripcion()+ " ya está agregado.");
		} else if (!isPuedeAgregar(producto, cantidad)) {
			throw new RuntimeException("Solo quedan " +producto.getCantidadStock() +" unidades del producto "+producto.getDescripcion());
		}
		LineaVenta lineaVenta = new LineaVenta(producto, cantidad);
		lineas.add(lineaVenta);
	}
	
	public List<LineaVenta> getLineas() {
		return lineas;
	}
	
	public List<Producto> getProductos() {
		List<Producto> productos = new ArrayList<Producto>();
		for (LineaVenta linea : lineas) {
			productos.add(linea.getProducto());
		}
		return productos;
	}
	
	public Double getTotal() {
		Double total=0.0;
		for(LineaVenta linea : lineas) {
			total += linea.getSubtotal();
		}
		return total;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente=cliente;
	}

	public LineaVenta getLineaAt(Integer index)  {
		LineaVenta linea = null;
		if (lineas.size() > index) {
			linea = lineas.get(index);
		}
		return linea;
	}

	public Long getId() {
		return id;
	}
	
	public Boolean isTerminada() {
		return id!=null;
	}

	public void removerLinea(Integer index) {
		LineaVenta linea = getLineaAt(index);
		if (linea != null) {
			lineas.remove(linea);
		}		
	}
	
	public boolean isComprable() {
		return !isTerminada() && lineas.size()>0;
	}

	public void comprar() {
		for (LineaVenta linea : lineas) {
			linea.comprar();
		}
		
	}

	public boolean isPuedeAgregar(Producto producto, Integer cantidadTotalRequerida) {
		if (!producto.isTieneStock(cantidadTotalRequerida)) return false;
		
		for (LineaVenta linea : lineas) {
			if(producto.equals(linea.getProducto())) {
				cantidadTotalRequerida += linea.getCantidad();
				if (!producto.isTieneStock(cantidadTotalRequerida)) return false;
			}
		}
		return true;
	}
}
