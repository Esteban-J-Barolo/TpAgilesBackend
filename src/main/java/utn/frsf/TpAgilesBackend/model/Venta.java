package utn.frsf.TpAgilesBackend.model;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Venta {

	private Vendedor vendedor;
	private Cliente cliente;
	private Inmueble inmueble;
	private LocalDate fecha;
	private Float costoTotal;

	public Venta(Vendedor vendedor, Cliente cliente, Inmueble inmueble, LocalDate fecha, Float costoTotal) {
		super();
		this.vendedor = vendedor;
		this.cliente = cliente;
		this.inmueble = inmueble;
		this.fecha = fecha;
		this.costoTotal = costoTotal;
	}

	public void generarPdf() {
		try {
			// Crear un objeto PdfWriter para escribir en el archivo PDF
			PdfWriter writer = new PdfWriter(new File("venta.pdf"));

			// Crear un objeto PdfDocument que representa el documento PDF
			PdfDocument pdf = new PdfDocument(writer);
			
			// Crear un objeto Document para agregar contenido al PDF
			Document document = new Document(pdf);

			// Agregar contenido al PDF
			document.add(new Paragraph(
					"Detalles de la venta:\n\n" +
							"Fecha: " + this.fecha.format(DateTimeFormatter.ISO_LOCAL_DATE) + "\n" +
							"Vendedor: " + vendedor.getNombre() + " " + vendedor.getApellido() + "\n" +
							"Cliente: " + cliente.getNombre() + " " + cliente.getApellido() + "\n" +
							"Inmueble: " + inmueble.getTipo() + " en " + inmueble.getLocalidad() + "\n" +
							"Costo Total: $" + this.costoTotal));

			// Cerrar el documento
			document.close();

			System.out.println("El archivo PDF se ha creado correctamente en: venta.pdf");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}