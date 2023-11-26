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
import utn.frsf.TpAgilesBackend.model.Propietario;
import utn.frsf.TpAgilesBackend.repositories.PropietarioRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class PropietarioControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@MockBean
    private PropietarioRepository propietarioRepository;
    	
	@BeforeEach
    public void setUp() {
        // Configura el comportamiento del repositorio mock
		when(propietarioRepository.save(any(Propietario.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(propietarioRepository.saveAndFlush(any(Propietario.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(propietarioRepository.findById(any(Integer.class))).thenReturn(Optional.of(new Propietario()));
        when(propietarioRepository.findById(eq(4))).thenReturn(Optional.empty());
    }

	@Test
	void TRAER_propietario() throws Exception {
		mockMvc.perform( MockMvcRequestBuilders.get("/propietarios"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers
						.content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON) );
	}
	
	@Test
	void GUARDAR_propietario() throws Exception {
		
		Propietario propietario = new Propietario();
		
        String propietarioJson = objectMapper.writeValueAsString(propietario);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/propietarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(propietarioJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                		.content()
                		.contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
    void BUSCAR_propietario() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/propietarios/search")
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
    void REEMPLAZAR_propietario() throws Exception {
		
		Propietario propietario = new Propietario();

        mockMvc.perform(MockMvcRequestBuilders.put("/propietarios/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(propietario)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                		.content()
                		.contentType(MediaType.APPLICATION_JSON));
    }
	
	@Test
    void ELIMINAR_propietario() throws Exception {

        List<Integer> idList = Arrays.asList(1, 2, 3);

        mockMvc.perform(delete("/propietarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(idList)))
                .andExpect(status().isOk());
        // Verifica que se lanzó una excepción para el ID 4
        assertThrows(ServletException.class, () -> {
            mockMvc.perform(delete("/propietarios")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(Arrays.asList(4))))
            		.andExpect(status().isNotFound());
            });
    }
}
