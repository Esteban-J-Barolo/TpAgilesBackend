package utn.frsf.TpAgilesBackend.exceptions;

public class InmuebleNotFoundException extends RuntimeException {
    public InmuebleNotFoundException(Integer id) {
        super("No se encontro cliente " + id);
    }
}
