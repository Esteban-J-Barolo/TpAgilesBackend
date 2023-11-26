package utn.frsf.TpAgilesBackend;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import utn.frsf.TpAgilesBackend.exceptions.VendedorNotFoundException;
import utn.frsf.TpAgilesBackend.model.Vendedor;
import utn.frsf.TpAgilesBackend.repositories.VendedorRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestEliminar {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VendedorRepository vendedorRepository;

    @BeforeEach
    public void setUp() {
        // Configura el comportamiento del repositorio mock
        when(vendedorRepository.saveAndFlush(any(Vendedor.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(vendedorRepository.findById(any(Integer.class))).thenReturn(Optional.of(new Vendedor()));
        when(vendedorRepository.findById(eq(4))).thenReturn(Optional.empty());
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
