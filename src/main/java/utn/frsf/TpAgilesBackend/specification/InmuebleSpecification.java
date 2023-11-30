package utn.frsf.TpAgilesBackend.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import utn.frsf.TpAgilesBackend.enums.TipoInmueble;
import utn.frsf.TpAgilesBackend.model.Inmueble;
import utn.frsf.TpAgilesBackend.model.Provincia;

public class InmuebleSpecification implements Specification<Inmueble>{

	private String provincia;
	private String localidad;
	private String barrio;
	private TipoInmueble tipo;
	private Integer dormitorios;
	private Float precioMin;
	private Float precioMax;
	
	

	public InmuebleSpecification(String provincia, String localidad, String barrio, TipoInmueble tipo,
			Integer dormitorios, Float precioMin, Float precioMax) {
		super();
		this.provincia = provincia;
		this.localidad = localidad;
		this.barrio = barrio;
		this.tipo = tipo;
		this.dormitorios = dormitorios;
		this.precioMin = precioMin;
		this.precioMax = precioMax;
	}

	@Override
	public Predicate toPredicate(Root<Inmueble> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		List<Predicate> predicates = new ArrayList<>();

        if (provincia != null) {
            predicates.add(builder.like(root.get("provincia").get("provincia"), provincia ));
        }

        if (localidad != null) {
            predicates.add(builder.like(root.get("localidad").get("localidad"), localidad ));
        }

        if (barrio != null) {
            predicates.add(builder.like(root.get("barrio"), "%" + barrio + "%"));
        }

        if (tipo != null) {
            predicates.add(builder.equal(root.get("tipo"), tipo));
        }

        if (dormitorios != null) {
            predicates.add(builder.equal(root.get("dormitorios"), dormitorios));
        }
        
        if (precioMin != null || precioMax != null) {
        	// condicion para cuando min es null
        	if (precioMin != null && precioMax != null) {
        		predicates.add(builder.between(root.get("precio"), precioMin, precioMax));
        	}else if (precioMin != null) {
        		predicates.add(builder.greaterThan(root.get("precio"), precioMin));
			} else {
				predicates.add(builder.lessThan(root.get("precio"), precioMax));
        	}
        }

        return builder.and(predicates.toArray(new Predicate[predicates.size()]));

	}

}