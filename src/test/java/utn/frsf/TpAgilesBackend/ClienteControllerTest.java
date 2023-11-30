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
import utn.frsf.TpAgilesBackend.model.Cliente;
import utn.frsf.TpAgilesBackend.repositories.ClienteRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@MockBean
    private ClienteRepository clienteRepository;
    	
	@BeforeEach
    public void setUp() {
        // Configura el comportamiento del repositorio mock
		when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(clienteRepository.saveAndFlush(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(clienteRepository.findById(any(Integer.class))).thenReturn(Optional.of(new Cliente()));
        when(clienteRepository.findById(eq(4))).thenReturn(Optional.empty());
    }

	@Test
	void TRAER_clientes() throws Exception {
		mockMvc.perform( MockMvcRequestBuilders.get("/clientes"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers
						.content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON) );
	}
	
	@Test
	void GUARDAR_clientes() throws Exception {
		
		Cliente cliente = new Cliente();
		
		cliente.setNombre("Esteban");
		cliente.setApellido("Barolo");

        String clienteJson = objectMapper.writeValueAsString(cliente);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clienteJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                		.content()
                		.contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
    void BUSCAR_clientes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/clientes/search")
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
    void REEMPLAZAR_cliente() throws Exception {
		Cliente cliente = new Cliente();
		
		cliente.setNombre("Esteban");
		cliente.setApellido("Barolo");

        mockMvc.perform(MockMvcRequestBuilders.put("/clientes/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                		.content()
                		.contentType(MediaType.APPLICATION_JSON));
    }
	
	@Test
    void ELIMINAR_cliente() throws Exception {

        List<Integer> idList = Arrays.asList(1, 2, 3);

        mockMvc.perform(delete("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(idList)))
                .andExpect(status().isOk());
        
        // Verifica que se lanzó una excepción para el ID 4
        assertThrows(ServletException.class, () -> {
            mockMvc.perform(delete("/clientes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(Arrays.asList(4))))
            		.andExpect(status().isNotFound());
            });
    }

}
