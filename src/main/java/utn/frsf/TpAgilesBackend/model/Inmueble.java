package utn.frsf.TpAgilesBackend.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import utn.frsf.TpAgilesBackend.enums.EstadoInmueble;
import utn.frsf.TpAgilesBackend.enums.OrientacionInmueble;
import utn.frsf.TpAgilesBackend.enums.TipoInmueble;

@Entity
public class Inmueble {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	private Cliente propietario;

	private LocalDate fechaCreacion;

	@Enumerated(EnumType.STRING)
	private EstadoInmueble estado;

	private String provincia;
	private String localidad;
	private String calleNumero;
	private String piso;
	private String barrio;
	@Enumerated(EnumType.STRING)
	private TipoInmueble tipo;
	private Float precio;
	@Enumerated(EnumType.STRING)
	private OrientacionInmueble orientacion;
	private Float frente;
	private Float fondo;
	private Float superficie;
	// datos inmueble
	private boolean horizontal;
	private int antiguedad;
	private int dormitorios;
	private int banios;
	private boolean cochera;
	private boolean patio;
	private boolean piscina;
	private boolean aguaCorriente;
	private boolean cloacas;
	private boolean gasNatural;
	private boolean aguaCaliente;
	private boolean telefono;
	private boolean lavadero;
	private boolean pavimento;

	private String linkImagen;

	public Inmueble() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Cliente getPropietario() {
		return propietario;
	}

	public void setPropietario(Cliente propietario) {
		this.propietario = propietario;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public EstadoInmueble getEstado() {
		return estado;
	}

	public void setEstado(EstadoInmueble estado) {
		this.estado = estado;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getCalleNumero() {
		return calleNumero;
	}

	public void setCalleNumero(String calleNumero) {
		this.calleNumero = calleNumero;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getBarrio() {
		return barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	public TipoInmueble getTipo() {
		return tipo;
	}

	public void setTipo(TipoInmueble tipo) {
		this.tipo = tipo;
	}

	public Float getPrecio() {
		return precio;
	}

	public void setPrecio(Float precio) {
		this.precio = precio;
	}

	public OrientacionInmueble getOrientacion() {
		return orientacion;
	}

	public void setOrientacion(OrientacionInmueble orientacion) {
		this.orientacion = orientacion;
	}

	public Float getFrente() {
		return frente;
	}

	public void setFrente(Float frente) {
		this.frente = frente;
	}

	public Float getFondo() {
		return fondo;
	}

	public void setFondo(Float fondo) {
		this.fondo = fondo;
	}

	public Float getSuperficie() {
		return superficie;
	}

	public void setSuperficie(Float superficie) {
		this.superficie = superficie;
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	public int getAntiguedad() {
		return antiguedad;
	}

	public void setAntiguedad(int antiguedad) {
		this.antiguedad = antiguedad;
	}

	public int getDormitorios() {
		return dormitorios;
	}

	public void setDormitorios(int dormitorios) {
		this.dormitorios = dormitorios;
	}

	public int getBanios() {
		return banios;
	}

	public void setBanios(int banios) {
		this.banios = banios;
	}

	public boolean isCochera() {
		return cochera;
	}

	public void setCochera(boolean cochera) {
		this.cochera = cochera;
	}

	public boolean isPatio() {
		return patio;
	}

	public void setPatio(boolean patio) {
		this.patio = patio;
	}

	public boolean isPiscina() {
		return piscina;
	}

	public void setPiscina(boolean piscina) {
		this.piscina = piscina;
	}

	public boolean isAguaCorriente() {
		return aguaCorriente;
	}

	public void setAguaCorriente(boolean aguaCorriente) {
		this.aguaCorriente = aguaCorriente;
	}

	public boolean isCloacas() {
		return cloacas;
	}

	public void setCloacas(boolean cloacas) {
		this.cloacas = cloacas;
	}

	public boolean isGasNatural() {
		return gasNatural;
	}

	public void setGasNatural(boolean gasNatural) {
		this.gasNatural = gasNatural;
	}

	public boolean isAguaCaliente() {
		return aguaCaliente;
	}

	public void setAguaCaliente(boolean aguaCaliente) {
		this.aguaCaliente = aguaCaliente;
	}

	public boolean isTelefono() {
		return telefono;
	}

	public void setTelefono(boolean telefono) {
		this.telefono = telefono;
	}

	public boolean isLavadero() {
		return lavadero;
	}

	public void setLavadero(boolean lavadero) {
		this.lavadero = lavadero;
	}

	public boolean isPavimento() {
		return pavimento;
	}

	public void setPavimento(boolean pavimento) {
		this.pavimento = pavimento;
	}

	public String getLinkImagen() {
		return linkImagen;
	}

	public void setLinkImagen(String linkImagen) {
		this.linkImagen = linkImagen;
	}
	
}
