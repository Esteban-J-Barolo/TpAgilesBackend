package utn.frsf.TpAgilesBackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Localidad {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private Integer provinciaId;
	private String localidad;
	
	public Localidad() {
		super();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId_privincia() {
		return provinciaId;
	}
	public void setId_privincia(Integer id_privincia) {
		this.provinciaId = id_privincia;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

}
