package utn.frsf.TpAgilesBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import utn.frsf.TpAgilesBackend.model.Vendedor;


public interface VendedorRepository extends JpaRepository<Vendedor, Integer>, JpaSpecificationExecutor<Vendedor> {
	
}