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

import utn.frsf.TpAgilesBackend.exceptions.ClienteNotFoundException;
import utn.frsf.TpAgilesBackend.model.Cliente;
import utn.frsf.TpAgilesBackend.repositories.ClienteRepository;
import utn.frsf.TpAgilesBackend.specification.ClienteSpecification;

@CrossOrigin

@RestController
public class ClienteController {

    private final ClienteRepository clienteRepository;

    ClienteController(ClienteRepository repository) {
        this.clienteRepository = repository;
    }

    @GetMapping("/clientes")
    Iterable<Cliente> allClientes() {
        return clienteRepository.findAll();
    }

    @PostMapping("/clientes")
    Cliente newCliente(@RequestBody Cliente newCliente) {
        return clienteRepository.saveAndFlush(newCliente);
    }

    @GetMapping("/clientes/search")
    Iterable<Cliente> findCliente(
            @RequestParam(name = "nombre", required = false) String nombre,
            @RequestParam(name = "apellido", required = false) String apellido,
            @RequestParam(name = "telefono", required = false) Integer telefono,
            @RequestParam(name = "numeroDoc", required = false) Integer numeroDoc,
            @RequestParam(name = "tipoDoc", required = false) String tipoDoc,
            @RequestParam(name = "email", required = false) String email) {
        ClienteSpecification spec = new ClienteSpecification(nombre, apellido, tipoDoc, email, telefono, numeroDoc);

        return clienteRepository.findAll(spec);
    }
    

    @PutMapping("/clientes/{id}")
    Cliente replaceCliente(@RequestBody Cliente newCliente, @PathVariable Long id) {

        return clienteRepository.findById(id.intValue())
                .map(cliente -> {
                    cliente.setNombre(newCliente.getNombre());
                    cliente.setApellido(newCliente.getApellido());
                    cliente.setTipoDoc(newCliente.getTipoDoc());
                    cliente.setNumeroDoc(newCliente.getNumeroDoc());
                    cliente.setEmail(newCliente.getEmail());
                    cliente.setTelefono(newCliente.getTelefono());
                    cliente.setEliminado(false);
                    return clienteRepository.saveAndFlush(cliente);
                }).orElseGet(() -> {
                    newCliente.setId(id.intValue());
                    return clienteRepository.saveAndFlush(newCliente);
                });
    }

    @DeleteMapping("/clientes")
    void deleteCliente(@RequestBody List<Integer> idList) {
        for (Integer id : idList) {
            clienteRepository.findById(id).map(
                    cliente -> {
                        cliente.setEliminado(true);
                        return clienteRepository.saveAndFlush(cliente);
                    }).orElseThrow(() -> new ClienteNotFoundException(id));
            ;
        }
    }

}