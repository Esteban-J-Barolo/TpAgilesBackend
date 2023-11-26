package utn.frsf.TpAgilesBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import utn.frsf.TpAgilesBackend.model.Provincia;

public interface ProvinciaRepository extends JpaRepository<Provincia, Integer>, JpaSpecificationExecutor<Provincia> {

}
