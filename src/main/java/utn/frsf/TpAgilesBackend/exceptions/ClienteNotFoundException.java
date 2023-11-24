package utn.frsf.TpAgilesBackend.exceptions;

public class ClienteNotFoundException extends RuntimeException {
    public ClienteNotFoundException(Integer id) {
        super("No se encontro cliente " + id);
    }
}