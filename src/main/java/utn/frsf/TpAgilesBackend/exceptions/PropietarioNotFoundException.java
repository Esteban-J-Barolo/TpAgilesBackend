package utn.frsf.TpAgilesBackend.exceptions;

public class PropietarioNotFoundException extends RuntimeException {
    public PropietarioNotFoundException(Integer id) {
        super("No se encontro propietario " + id);
    }
}