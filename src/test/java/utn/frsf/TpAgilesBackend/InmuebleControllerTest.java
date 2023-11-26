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
import utn.frsf.TpAgilesBackend.model.Inmueble;
import utn.frsf.TpAgilesBackend.repositories.InmuebleRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class InmuebleControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@MockBean
    private InmuebleRepository inmuebleRepository;
    	
	@BeforeEach
    public void setUp() {
        // Configura el comportamiento del repositorio mock
		when(inmuebleRepository.save(any(Inmueble.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(inmuebleRepository.saveAndFlush(any(Inmueble.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(inmuebleRepository.findById(any(Integer.class))).thenReturn(Optional.of(new Inmueble()));
        when(inmuebleRepository.findById(eq(4))).thenReturn(Optional.empty());
    }

	@Test
	void TRAER_inmueble() throws Exception {
		mockMvc.perform( MockMvcRequestBuilders.get("/inmuebles"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers
						.content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON) );
	}
	
	@Test
	void GUARDAR_inmueble() throws Exception {
		
		Inmueble inmueble = new Inmueble();

        String inmuebleJson = objectMapper.writeValueAsString(inmueble);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/inmuebles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inmuebleJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                		.content()
                		.contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
    void BUSCAR_inmueble() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/inmuebles/search")
                .param("provincia", "San Juan")
                .param("localidad", "San Juan")
                .param("barrio", "amti")
                .param("tipo", "CASA")
                .param("dormitorios", "3")
                .param("preioMin", "1257000")
                .param("precioMax", "5257000"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                		.content()
                		.contentType(MediaType.APPLICATION_JSON));
    }
	
	@Test
    void REEMPLAZAR_inmueble() throws Exception {
		Inmueble inmueble = new Inmueble();

        mockMvc.perform(MockMvcRequestBuilders.put("/inmuebles/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inmueble)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                		.content()
                		.contentType(MediaType.APPLICATION_JSON));
    }
	
	@Test
    void ELIMINAR_inmueble() throws Exception {

        List<Integer> idList = Arrays.asList(1, 2, 3);

        mockMvc.perform(delete("/inmuebles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(idList)))
                .andExpect(status().isOk());
        // Verifica que se lanzó una excepción para el ID 4
        assertThrows(ServletException.class, () -> {
            mockMvc.perform(delete("/inmuebles")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(Arrays.asList(4))))
            		.andExpect(status().isNotFound());
            });
    }
	
	@Test
    void BUSCAR_estados_inmueble() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/inmuebles/estados"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers
        		.content()
        		.contentType(MediaType.APPLICATION_JSON));
    }
	
	@Test
    void BUSCAR_localidades_inmueble() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/inmuebles/localidades"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers
        		.content()
        		.contentType(MediaType.APPLICATION_JSON));
    }
	
	@Test
    void BUSCAR_provincias_inmueble() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/inmuebles/provincias"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers
        		.content()
        		.contentType(MediaType.APPLICATION_JSON));
    }
}
