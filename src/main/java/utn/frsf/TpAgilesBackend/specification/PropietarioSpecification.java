package utn.frsf.TpAgilesBackend.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import utn.frsf.TpAgilesBackend.model.Propietario;

public class PropietarioSpecification implements Specification<Propietario> {

    private String nombre;
    private String apellido;
    private Integer telefono;
    private Integer numeroDoc;
    private String tipoDoc;
    private String email;
    private String calle;
    private String provincia;
    private String localidad;
    
    public PropietarioSpecification(String nombre, String apellido, String tipoDoc, String email, Integer telefono,
            Integer numeroDoc, String calle, String provincia, String localidad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.tipoDoc = tipoDoc;
        this.telefono = telefono;
        this.numeroDoc = numeroDoc;
        this.calle = calle;
        this.provincia = provincia;
        this.localidad = localidad;
    }

    @Override
    public Predicate toPredicate(Root<Propietario> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        if (nombre != null) {
            predicates.add(builder.like(root.get("nombre"), "%" + nombre + "%"));
        }

        if (apellido != null) {
            predicates.add(builder.like(root.get("apellido"), "%" + apellido + "%"));
        }

        if (email != null) {
            predicates.add(builder.like(root.get("email"), "%" + email + "%"));
        }

        if (tipoDoc != null) {
            predicates.add(builder.like(root.get("tipoDoc"), "%" + tipoDoc+ "%"));
        }

        if (telefono != null) {
            predicates.add(builder.equal(root.get("telefono"), telefono));
        }

        if (numeroDoc != null) {
            predicates.add(builder.equal(root.get("numeroDoc"), numeroDoc));
        }
        if (calle != null) {
            predicates.add(builder.like(root.get("calle"), "%" + calle + "%"));
        }       
        if (provincia != null) {
            predicates.add(builder.like(root.get("provincia"), "%" + provincia + "%"));
        }
        if (localidad != null) {
            predicates.add(builder.like(root.get("localidad"), "%" + localidad + "%"));
        }
        return builder.and(predicates.toArray(new Predicate[predicates.size()]));

    }

}