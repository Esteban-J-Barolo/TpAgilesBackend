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

import utn.frsf.TpAgilesBackend.enums.EstadoInmueble;
import utn.frsf.TpAgilesBackend.enums.OrientacionInmueble;
import utn.frsf.TpAgilesBackend.enums.TipoInmueble;
import utn.frsf.TpAgilesBackend.exceptions.InmuebleNotFoundException;
import utn.frsf.TpAgilesBackend.model.Inmueble;
import utn.frsf.TpAgilesBackend.model.Localidad;
import utn.frsf.TpAgilesBackend.model.Provincia;
import utn.frsf.TpAgilesBackend.repositories.InmuebleRepository;
import utn.frsf.TpAgilesBackend.repositories.LocalidadRepository;
import utn.frsf.TpAgilesBackend.repositories.ProvinciaRepository;
import utn.frsf.TpAgilesBackend.specification.InmuebleSpecification;

@CrossOrigin
@RestController
public class InmuebleController {

	private final InmuebleRepository inmuebleRepository;
	private final ProvinciaRepository provinciaRepository;
	private final LocalidadRepository localidadRepository;

	InmuebleController(InmuebleRepository inmuebleRepository, LocalidadRepository localidadRepository, ProvinciaRepository provinciaRepository) {
		this.inmuebleRepository = inmuebleRepository;
		this.localidadRepository = localidadRepository;
		this.provinciaRepository = provinciaRepository;
	}

	@GetMapping("/inmuebles")
	Iterable<Inmueble> allInmueble() {
		return inmuebleRepository.findAll();
	}
	
	@GetMapping("/inmuebles/{id}")
	Inmueble getInmuebleById(@PathVariable Integer id) {
        return inmuebleRepository.findById(id)
                .orElseThrow(() -> new InmuebleNotFoundException(id));
    }

	@PostMapping("/inmuebles")
	Inmueble newInmueble(@RequestBody Inmueble newInmueble) {
		return inmuebleRepository.saveAndFlush(newInmueble);
	}
	
	@GetMapping("/inmuebles/search")
	public Iterable<Inmueble> searchInmuebles(
	        @RequestParam(name = "provincia",	required = false) String provincia,
	        @RequestParam(name = "localidad",	required = false) String localidad,
	        @RequestParam(name = "barrio",		required = false) String barrio,
	        @RequestParam(name = "tipo",		required = false) TipoInmueble tipo,
	        @RequestParam(name = "dormitorios",	required = false) Integer dormitorios,
	        @RequestParam(name = "precioMin",	required = false) Float precioMin,
	        @RequestParam(name = "precioMax",	required = false) Float precioMax) {
		
		InmuebleSpecification spec = new InmuebleSpecification(provincia, localidad, barrio, tipo, dormitorios, precioMin, precioMax);

		return inmuebleRepository.findAll(spec);
	}
	
	@PutMapping("/inmuebles/{id}")
	Inmueble replaceInmueble(@RequestBody Inmueble newInmueble, @PathVariable Long id) {
		return inmuebleRepository.findById(id.intValue())
                .map(inmueble -> {
                	inmueble.setPropietario(newInmueble.getPropietario());
                    inmueble.setFechaCreacion(newInmueble.getFechaCreacion());
                    inmueble.setEstado(newInmueble.getEstado());
                    inmueble.setProvincia(newInmueble.getProvincia());
                    inmueble.setLocalidad(newInmueble.getLocalidad());
                    inmueble.setCalleNumero(newInmueble.getCalleNumero());
                    inmueble.setPiso(newInmueble.getPiso());
                    inmueble.setBarrio(newInmueble.getBarrio());
                    inmueble.setTipo(newInmueble.getTipo());
                    inmueble.setPrecio(newInmueble.getPrecio());
                    inmueble.setOrientacion(newInmueble.getOrientacion());
                    inmueble.setFrente(newInmueble.getFrente());
                    inmueble.setFondo(newInmueble.getFondo());
                    inmueble.setSuperficie(newInmueble.getSuperficie());
                    inmueble.setHorizontal(newInmueble.isHorizontal());
                    inmueble.setAntiguedad(newInmueble.getAntiguedad());
                    inmueble.setDormitorios(newInmueble.getDormitorios());
                    inmueble.setBanios(newInmueble.getBanios());
                    inmueble.setCochera(newInmueble.isCochera());
                    inmueble.setPatio(newInmueble.isPatio());
                    inmueble.setPiscina(newInmueble.isPiscina());
                    inmueble.setAguaCorriente(newInmueble.isAguaCorriente());
                    inmueble.setCloacas(newInmueble.isCloacas());
                    inmueble.setGasNatural(newInmueble.isGasNatural());
                    inmueble.setAguaCaliente(newInmueble.isAguaCaliente());
                    inmueble.setTelefono(newInmueble.isTelefono());
                    inmueble.setLavadero(newInmueble.isLavadero());
                    inmueble.setPavimento(newInmueble.isPavimento());
                    inmueble.setLinkImagen(newInmueble.getLinkImagen());
                    inmueble.setLinkVideo(newInmueble.getLinkVideo());
                    return inmuebleRepository.saveAndFlush(inmueble);
                }).orElseGet(() -> {
                	newInmueble.setId(id.intValue());
                    return inmuebleRepository.saveAndFlush(newInmueble);
                });
	}
	
	@DeleteMapping("/inmuebles")
	void deleteInmueble(@RequestBody List<Integer> idList) {
		for (Integer id : idList) {
			inmuebleRepository.findById(id).map(
					inmueble -> {
						inmueble.setEstado(EstadoInmueble.ELIMINADO);
                        return inmuebleRepository.saveAndFlush(inmueble);
                    }).orElseThrow(() -> new InmuebleNotFoundException(id));
            ;
        }
	}
	
	@GetMapping("/inmuebles/estados")
	public EstadoInmueble[] estadosInmueble() {
        return EstadoInmueble.values();
    }
	
	@GetMapping("/inmuebles/tipos")
	public TipoInmueble[] tiposInmueble() {
        return TipoInmueble.values();
    }
	
	@GetMapping("/inmuebles/orientaciones")
	public OrientacionInmueble[] orintacionesInmueble() {
        return OrientacionInmueble.values();
    }
	
	@GetMapping("/inmuebles/provincias")
	public Iterable<Provincia> getAllProvincias() {
	    return provinciaRepository.findAll();
	}
	
	@GetMapping("/inmuebles/localidades")
	public Iterable<Localidad> getAllLocalidades() {
	    return localidadRepository.findAll();
	}
	
	@GetMapping("/inmuebles/localidades/provincia")
	public Iterable<Localidad> getAllLocalidades(@RequestParam("provinciaId") Long provinciaId) {
	    return localidadRepository.findByProvinciaId(provinciaId);
	}

}