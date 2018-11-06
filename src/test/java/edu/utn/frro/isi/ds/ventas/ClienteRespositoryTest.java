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
public class ClienteRespositoryTest {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ClienteRepository repository;
	
	@Test
	public void whenAddingOnefindByNombreAndApellidoReturnsOne() throws Exception {
		persist(new Cliente("Joe", "Doe", "Engineer"));
		List<Cliente> clientes = this.repository.findByNombreAndApellido("Joe", "Doe");
		Cliente cliente = clientes.get(0);
		assertThat(clientes.size()).isEqualTo(1);
		assertThat(cliente.getNombre()).isEqualTo("Joe");
		assertThat(cliente.getApellido()).isEqualTo("Doe");
	}
	
	@Test
	public void whenAddingTwofindByNombreAndApellidoReturnsTwo() throws Exception {
		persist(new Cliente("Joe", "Doe", "Engineer"));
		persist(new Cliente("John", "Don", "Engineer"));
		List<Cliente> clientes = this.repository.findByNombreAndApellido("jo", "do");
		
		assertThat(clientes.size()).isEqualTo(2);
		Cliente cliente = clientes.get(0);
		assertThat(cliente.getNombre()).isEqualTo("Joe");
		assertThat(cliente.getApellido()).isEqualTo("Doe");
		cliente = clientes.get(1);
		assertThat(cliente.getNombre()).isEqualTo("John");
		assertThat(cliente.getApellido()).isEqualTo("Don");
	}
	
	@Test
	public void whenSavingClienteIdIsGenerated() throws Exception {
		Cliente cliente = new Cliente("Joe", "Doe", "Engineer");
		this.repository.save(cliente);
		List<Cliente> clientes = this.repository.findAll();
		Cliente cliente2 = this.repository.getOne(2L);
		assertThat(clientes.size()).isEqualTo(1);
		assertThat(cliente.getId()).isEqualTo(2);
		assertThat(cliente2).isEqualTo(cliente);	
	}
	
	private void persist(Object entity) {
		this.entityManager.persist(entity);
	}
}
