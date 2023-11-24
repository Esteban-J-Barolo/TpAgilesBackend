package utn.frsf.TpAgilesBackend.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import utn.frsf.TpAgilesBackend.model.Cliente;

public class ClienteSpecification implements Specification<Cliente> {

    private String nombre;
    private String apellido;
    private Integer telefono;
    private Integer numeroDoc;
    private String tipoDoc;
    private String email;

    public ClienteSpecification(String nombre, String apellido, String tipoDoc, String email, Integer telefono,
            Integer numeroDoc) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.tipoDoc = tipoDoc;
        this.telefono = telefono;
        this.numeroDoc = numeroDoc;
    }

    @Override
    public Predicate toPredicate(Root<Cliente> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
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

        return builder.and(predicates.toArray(new Predicate[predicates.size()]));

    }

}