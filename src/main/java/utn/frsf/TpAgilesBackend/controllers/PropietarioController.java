package utn.frsf.TpAgilesBackend.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import utn.frsf.TpAgilesBackend.exceptions.PropietarioNotFoundException;
import utn.frsf.TpAgilesBackend.model.Propietario;
import utn.frsf.TpAgilesBackend.repositories.PropietarioRepository;
import utn.frsf.TpAgilesBackend.specification.PropietarioSpecification;

@CrossOrigin
@RestController
public class PropietarioController {

    private final PropietarioRepository propietarioRepository;

    PropietarioController(PropietarioRepository repository) {
        this.propietarioRepository = repository;
    }

    @GetMapping("/propietarios")
    Iterable<Propietario> allClientes() {
        return propietarioRepository.findAll();
    }

    @PostMapping("/propietarios")
    Propietario newPropietario(@RequestBody Propietario newPropietario) {
        return propietarioRepository.saveAndFlush(newPropietario);
    }

    @GetMapping("/propietarios/search")
    Iterable<Propietario> findCliente(
            @RequestParam(name = "nombre", required = false) String nombre,
            @RequestParam(name = "apellido", required = false) String apellido,
            @RequestParam(name = "telefono", required = false) Integer telefono,
            @RequestParam(name = "numeroDoc", required = false) Integer numeroDoc,
            @RequestParam(name = "tipoDoc", required = false) String tipoDoc,
            @RequestParam(name = "calle", required = false) String calle,
            @RequestParam(name = "localidad", required = false) String localidad,
            @RequestParam(name = "provincia", required = false) String provincia,
            @RequestParam(name = "email", required = false) String email) {
        PropietarioSpecification spec = new PropietarioSpecification(nombre, apellido, tipoDoc, email, telefono, numeroDoc,calle,provincia,localidad);

        return propietarioRepository.findAll(spec);
    }
    
    @PutMapping("/propietarios/{id}")
    Propietario replacePropietario(@RequestBody Propietario newPropietario, @PathVariable Long id) {

        return propietarioRepository.findById(id.intValue())
                .map(propietario -> {
                    propietario.setNombre(newPropietario.getNombre());
                    propietario.setApellido(newPropietario.getApellido());
                    propietario.setTipoDoc(newPropietario.getTipoDoc());
                    propietario.setNumeroDoc(newPropietario.getNumeroDoc());
                    propietario.setEmail(newPropietario.getEmail());
                    propietario.setTelefono(newPropietario.getTelefono());
                    propietario.setEliminado(false);
                    propietario.setProvincia(newPropietario.getProvincia());
                    propietario.setLocalidad(newPropietario.getLocalidad());
                    propietario.setCalle(newPropietario.getCalle());
                    return propietarioRepository.saveAndFlush(propietario);
                }).orElseGet(() -> {
                    newPropietario.setId(id.intValue());
                    return propietarioRepository.saveAndFlush(newPropietario);
                });
    }

    @DeleteMapping("/propietarios")
    void deletePropeitario(@RequestBody List<Integer> idList) {
        for (Integer id : idList) {
            propietarioRepository.findById(id).map(
                    propietario -> {
                        propietario.setEliminado(true);
                        return propietarioRepository.saveAndFlush(propietario);
                    }).orElseThrow(() -> new PropietarioNotFoundException(id));
            ;
        }
    }

}