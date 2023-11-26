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

import utn.frsf.TpAgilesBackend.exceptions.VendedorNotFoundException;
import utn.frsf.TpAgilesBackend.model.Vendedor;
import utn.frsf.TpAgilesBackend.repositories.VendedorRepository;
import utn.frsf.TpAgilesBackend.specification.VendedorSpecification;

@CrossOrigin
@RestController
public class VendedorController {

	private final VendedorRepository vendedorRepository;

	VendedorController(VendedorRepository vendedorRepository) {
		this.vendedorRepository = vendedorRepository;
	}

	@GetMapping("/vendedores")
	Iterable<Vendedor> allVendedores() {
		return vendedorRepository.findAll();
	}

	@PostMapping("/vendedores")
	Vendedor newVendedor(@RequestBody Vendedor newVendedor) {
		return vendedorRepository.save(newVendedor);
	}

	@GetMapping("/vendedores/search")
	Iterable<Vendedor> findCliente(@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "apellido", required = false) String apellido,
			@RequestParam(name = "telefono", required = false) Integer telefono,
			@RequestParam(name = "numeroDoc", required = false) Integer numeroDoc,
			@RequestParam(name = "tipoDoc", required = false) String tipoDoc,
			@RequestParam(name = "email", required = false) String email) {

		VendedorSpecification spec = new VendedorSpecification(nombre, apellido, tipoDoc, email, telefono, numeroDoc);

        return vendedorRepository.findAll(spec);
	}

	@PutMapping("/vendedores/{id}")
	Vendedor replaceVendedor(@RequestBody Vendedor newVendedor, @PathVariable Long id) {
		return vendedorRepository.findById(id.intValue()).map(vendedor -> {
			vendedor.setNombre(newVendedor.getNombre());
			vendedor.setApellido(newVendedor.getApellido());
			vendedor.setEmail(newVendedor.getEmail());
			vendedor.setNumeroDoc(newVendedor.getNumeroDoc());
			vendedor.setTipoDoc(newVendedor.getTipoDoc());
			vendedor.setTelefono(newVendedor.getTelefono());
			vendedor.setContraseña(newVendedor.getContraseña());
			vendedor.setEliminado(false);
			return vendedorRepository.save(vendedor);
		}).orElseGet(() -> {
			newVendedor.setId(id.intValue());
			return vendedorRepository.save(newVendedor);
		});

	}

	@DeleteMapping("/vendedores")
	public void deleteVendedor(@RequestBody List<Integer> idList) {
		for (Integer id : idList) {
			vendedorRepository.findById(id).map(vendedor -> {
				vendedor.setEliminado(true);
				return vendedorRepository.saveAndFlush(vendedor);
			}).orElseThrow(() -> new VendedorNotFoundException(id));
		}
	}

}