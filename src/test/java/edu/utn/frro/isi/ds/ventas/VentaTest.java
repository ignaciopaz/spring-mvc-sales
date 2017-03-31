package edu.utn.frro.isi.ds.ventas;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class VentaTest {
	private static Producto p1, p2;
	private static Venta v;
	
	@Before
    public void setup() {
    	v = new Venta();
    }
	
    @BeforeClass
    public static void setupAllTests() {
    	p1 = new Producto("p1", 10.0);
    	p2 = new Producto("p2", 5.5);
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
		v.agregarProducto(p1, 1);
		assertEquals(Double.valueOf(30.0), v.getTotal());
	}
	
	@Test public void calcularTotal2Producto3Lineas() {
		v.agregarProducto(p1, 2);
		v.agregarProducto(p1, 3);
		v.agregarProducto(p2, 3);
		assertEquals(Double.valueOf(66.5), v.getTotal());
	}
}
