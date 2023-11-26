package utn.frsf.TpAgilesBackend.exceptions;

public class VendedorNotFoundException extends RuntimeException {
	
	public VendedorNotFoundException(Integer id) {
        super("No se econtro vendedor " + id);
    }

}
