package utn.frsf.TpAgilesBackend.controllers;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import utn.frsf.TpAgilesBackend.model.RequestCIV;
import utn.frsf.TpAgilesBackend.model.Reserva;
import utn.frsf.TpAgilesBackend.model.Venta;

@CrossOrigin
@RestController
public class OperacionController {
	
	@PostMapping("/venta")
    void crearVenta(@RequestBody RequestCIV request) {
		
		Venta venta = new Venta(request.getVendedor(), request.getCliente(), request.getInmueble(), LocalDate.now(), request.getInmueble().getPrecio());
		
        venta.generarPdf();
    }
	
	@PostMapping("/reserva")
    void crearReserva(@RequestBody RequestCIV request) {
		
		Reserva reserva = new Reserva(request.getVendedor(), request.getCliente(), request.getInmueble(), LocalDate.now());
		
		reserva.generarPdf();
    }
}
