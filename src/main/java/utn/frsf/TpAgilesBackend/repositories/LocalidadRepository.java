package utn.frsf.TpAgilesBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import utn.frsf.TpAgilesBackend.model.Localidad;

public interface LocalidadRepository extends JpaRepository<Localidad, Integer>, JpaSpecificationExecutor<Localidad>{

}
