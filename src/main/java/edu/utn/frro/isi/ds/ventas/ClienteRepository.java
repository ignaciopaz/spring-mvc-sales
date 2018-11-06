package edu.utn.frro.isi.ds.ventas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	/*List<Cliente> findByLastNameStartsWithIgnoreCase(String lastName);
	List<Cliente> findByFirstNameStartsWithIgnoreCase(String firstName);
	
	@Query("select e from #{#entityName} e where UPPER(e.lastName) like UPPER(CONCAT(?1, '%')) AND UPPER(e.firstName) like UPPER(CONCAT(?2, '%'))")
	List<Cliente>  findByLastNameAndFirstName(String lastName, String firstName);
	 */ 
	@Query("select e from #{#entityName} e where UPPER(e.nombre) like UPPER(CONCAT(?1, '%')) AND UPPER(e.apellido) like UPPER(CONCAT(?2, '%'))")
	List<Cliente> findByNombreAndApellido(String nombre, String apellido);
}
