package utn.frsf.TpAgilesBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import utn.frsf.TpAgilesBackend.model.Inmueble;

public interface InmuebleRepository extends JpaRepository<Inmueble, Integer>, JpaSpecificationExecutor<Inmueble>{

}
