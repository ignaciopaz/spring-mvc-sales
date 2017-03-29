/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.utn.frro.isi.ds.ventas.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import edu.utn.frro.isi.ds.ventas.Cliente;
import edu.utn.frro.isi.ds.ventas.ClienteRepository;
import edu.utn.frro.isi.ds.ventas.Producto;
import edu.utn.frro.isi.ds.ventas.ProductoRepository;

@Component
public class DatabaseLoader implements CommandLineRunner {

	private final ClienteRepository clienteRepository;
	private final ProductoRepository productoRepository;

	@Autowired
	public DatabaseLoader(ClienteRepository customerRepository, ProductoRepository productRepository) {
		this.clienteRepository = customerRepository;
		this.productoRepository = productRepository;
	}

	@Override
	public void run(String... strings) throws Exception {

		loadClientes();
		locadProductos();
	}

	private void loadClientes() {
		this.clienteRepository.save(new Cliente("Frodo", "Baggins", "ring bearer"));
		this.clienteRepository.save(new Cliente("Bilbo", "Baggins", "burglar"));
		this.clienteRepository.save(new Cliente("Gandalf", "the Grey", "wizard"));
		this.clienteRepository.save(new Cliente("Samwise", "Gamgee", "gardener"));
		this.clienteRepository.save(new Cliente("Meriadoc", "Brandybuck", "pony rider"));
		this.clienteRepository.save(new Cliente("Peregrin", "Took", "pipe smoker"));
	}
	private void locadProductos() {
		this.productoRepository.save(new Producto("Martillo", 20.0 ));
		this.productoRepository.save(new Producto("Destornillador", 10.0 ));
		this.productoRepository.save(new Producto("Pack de Tornillos", 5.0 ));
		
	}
}
