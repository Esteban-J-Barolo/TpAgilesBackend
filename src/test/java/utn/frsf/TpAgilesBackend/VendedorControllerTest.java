package utn.frsf.TpAgilesBackend;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import utn.frsf.TpAgilesBackend.model.Vendedor;
import utn.frsf.TpAgilesBackend.repositories.VendedorRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class VendedorControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
    private ObjectMapper objectMapper;

	@MockBean
    private VendedorRepository vendedorRepository;
	
	@BeforeEach
    public void setUp() {
        // Configura el comportamiento del repositorio mock
		when(vendedorRepository.save(any(Vendedor.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(vendedorRepository.saveAndFlush(any(Vendedor.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(vendedorRepository.findById(any(Integer.class))).thenReturn(Optional.of(new Vendedor()));
        when(vendedorRepository.findById(eq(4))).thenReturn(Optional.empty());
    }
	
	@Test
	void TRAER_vendedores() throws Exception {
		mockMvc.perform( MockMvcRequestBuilders.get("/vendedores"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers
						.content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON) );
	}
	
	@Test
	void GUARDAR_vendedores() throws Exception {
		
		Vendedor vendedor = new Vendedor();
		
		vendedor.setNombre("Esteban");
		vendedor.setApellido("Barolo");
		vendedor.setEmail("esteba@gmail.com");
		vendedor.setContraseña("la mas dificil");
		vendedor.setEliminado(false);
		vendedor.setNumeroDoc(4321);
		vendedor.setTelefono(1234);
		vendedor.setTipoDoc("DNI");

        String vendedorJson = objectMapper.writeValueAsString(vendedor);
		
        mockMvc.perform(MockMvcRequestBuilders.post("/vendedores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(vendedorJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                		.content()
                		.contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
    void BUSCAR_vendedores() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/vendedores/search")
                .param("nombre", "Juan")
                .param("apellido", "Perez")
                .param("telefono", "123456789")
                .param("numeroDoc", "123456")
                .param("tipoDoc", "DNI")
                .param("email", "juan@example.com"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                		.content()
                		.contentType(MediaType.APPLICATION_JSON));
    }
	
	@Test
    void REEMPLAZAR_vendedores() throws Exception {

		Vendedor vendedor = new Vendedor();
		
		vendedor.setNombre("Esteban");
		vendedor.setApellido("Barolo");
		vendedor.setEmail("esteba@gmail.com");
		vendedor.setContraseña("la mas dificil");
		vendedor.setEliminado(false);
		vendedor.setNumeroDoc(54321);
		vendedor.setTelefono(12345);
		vendedor.setTipoDoc("DNI");

        mockMvc.perform(MockMvcRequestBuilders.put("/vendedores/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vendedor)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                		.content()
                		.contentType(MediaType.APPLICATION_JSON));
    }
	
	@Test
    public void testDeleteVendedor() throws Exception {
    	
        List<Integer> idList = Arrays.asList(1, 2, 3);

        // Realiza la solicitud DELETE y espera el código de estado 200 OK
        mockMvc.perform(delete("/vendedores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(idList)))
                .andExpect(status().isOk());

        // Verifica que se lanzó una excepción para el ID 4
        assertThrows(ServletException.class, () -> {
            mockMvc.perform(delete("/vendedores")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(Arrays.asList(4))))
            		.andExpect(status().isNotFound());
            });
   
    }

}
