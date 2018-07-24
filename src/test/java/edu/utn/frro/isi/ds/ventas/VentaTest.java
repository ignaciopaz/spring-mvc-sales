package edu.utn.frro.isi.ds.ventas;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class VentaTest {
	private Producto p1, p2, p3;
	private Venta v;
	
	@Before // Arrange general para todos los tests. Se corre antes de ejecutar cada test.
    public void setup() {
    	v = new Venta();
    	p1 = new Producto("p1", 10.0, 3);
    	p1.setId(1L);
    	p2 = new Producto("p2", 5.5, 3);
    	p2.setId(2L);
    	p3 = new Producto("p3", 6.0, 5);
    	p3.setId(3L);
    }
    
	@Test public void calcularTotal1Producto1Item() {
		v.agregarProducto(p1, 1);
		assertEquals(Double.valueOf(10.0), v.getTotal());
	}
	
	@Test public void calcularTotal1Producto2Items() {
		v.agregarProducto(p1, 2);
		assertEquals(Double.valueOf(20.0), v.getTotal());
	}
	
	@Test public void calcularTotal2Producto() {
		v.agregarProducto(p1, 2);
		v.agregarProducto(p2, 1);
		assertEquals(Double.valueOf(25.5), v.getTotal());
	}
	
	@Test public void calcularTotal2Producto3Lineas() {
		v.agregarProducto(p1, 2);
		v.agregarProducto(p2, 3);
		v.agregarProducto(p3, 3);
		assertEquals(Double.valueOf(54.5), v.getTotal());
	}
	
	@Test(expected=RuntimeException.class) public void evitarAgregarProductoDuplicado() {
		v.agregarProducto(p1, 2);
		v.agregarProducto(p1, 3);		
	}
	
	@Test(expected=RuntimeException.class) public void evitarAgregarProductoSinStockSuficiente() {
		v.agregarProducto(p1, 22);
	}
	
	@Test public void totalVentaNoCambiaAlCambiarElPrecioProducto() {
		v.agregarProducto(p1, 2);
		v.agregarProducto(p2, 1);
		Double totalAntesConfirmar = v.getTotal();
		v.confirmarVenta();
		p1.setPrecio(2.0);
		Double totalDespuesConfirmar = v.getTotal();
		assertEquals(totalAntesConfirmar, totalDespuesConfirmar);
	}
	
	@Test(expected=RuntimeException.class) public void evitarAgregarLineaEnVentaFinalizada() {
		v.agregarProducto(p1, 1);
		v.agregarProducto(p2, 1);
		v.confirmarVenta();
		v.agregarProducto(p3, 1);
	}
}
