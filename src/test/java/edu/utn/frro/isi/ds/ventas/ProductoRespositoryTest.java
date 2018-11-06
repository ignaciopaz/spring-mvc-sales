package edu.utn.frro.isi.ds.ventas;

import org.junit.*;
import org.junit.runner.*;
import static org.assertj.core.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductoRespositoryTest {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ProductoRepository repository;
	
	@Test
	public void whenAddingProductWithoutStockResultIsEmpty() throws Exception {
		persist(new Producto("P1", 2.0, 0));
		persist(new Producto("P2", 3.0, 0));
		List<Producto> productos = this.repository.buscarProductosEnStock();
		assertThat(productos.isEmpty()).isEqualTo(true);
	}
	@Test
	public void whenAddingNoProductsResultIsEmpty() throws Exception {
		List<Producto> productos = this.repository.buscarProductosEnStock();
		assertThat(productos.isEmpty()).isEqualTo(true);
	}
	@Test
	public void whenAddingProductWithStockResult() throws Exception {
		persist(new Producto("P1", 2.0, 1));
		persist(new Producto("P2", 3.0, 0));
		persist(new Producto("P3", 3.0, 45));
		List<Producto> productos = this.repository.buscarProductosEnStock();
		assertThat(productos.size()).isEqualTo(2);
	}
	
	@Test
	public void whenProductStockIsModifiedResultChanges() throws Exception {
		Producto p1 = new Producto("P1", 2.0, 1);
		Producto p2 = new Producto("P2", 3.0, 0);
		Producto p3 = new Producto("P3", 3.0, 45);
		this.repository.save(p1);
		this.repository.save(p2);
		this.repository.save(p3);
		p1.reducirStock(2);
		List<Producto> productos = this.repository.buscarProductosEnStock();
		assertThat(productos.size()).isEqualTo(1);
		p3.reducirStock(46);
		productos = this.repository.buscarProductosEnStock();
		assertThat(productos.size()).isEqualTo(0);
	}
	private void persist(Object entity) {
		this.entityManager.persist(entity);
	}
}
