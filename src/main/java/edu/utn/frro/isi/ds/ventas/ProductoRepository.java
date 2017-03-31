package edu.utn.frro.isi.ds.ventas;

import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

	@Query("SELECT p FROM Producto p WHERE p.cantidadStock > 0")
	public List<Producto> buscarProductosEnStock() ;
	
}
