package utn.frsf.TpAgilesBackend.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import utn.frsf.TpAgilesBackend.model.Propietario;

public interface PropietarioRepository extends JpaRepository<Propietario, Integer>, JpaSpecificationExecutor<Propietario> {

}