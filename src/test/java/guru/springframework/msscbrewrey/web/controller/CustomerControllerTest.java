package guru.springframework.msscbrewrey.web.controller;

import guru.springframework.msscbrewrey.AbstractBeerBaseTest;
import guru.springframework.web.model.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest extends AbstractBeerBaseTest {

    @Test
    void doGet() throws Exception {

        when(customerService.getCustomer(any())).thenReturn(CustomerDto.builder().name("John Paul Jones").build());
        this.mvc.perform(get("/api/v1/customer/" + UUID.randomUUID())).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("John Paul Jones")));

    }

    @Test
    void handlePost() throws Exception {
        CustomerDto dto = getCustomer();
        String json = mapper.writeValueAsString(dto);
        UUID uuid = UUID.randomUUID();
        when(customerService.createCustomer(any())).thenReturn(dto);


        this.mvc.perform(post("/api/v1/customer")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void handlePut() throws Exception {
        CustomerDto dto = getCustomer();
        String json = mapper.writeValueAsString(dto);
        UUID uuid = UUID.randomUUID();
        doNothing().when(customerService).updateCustomer(any());

        this.mvc.perform(put("/api/v1/customer/" + uuid)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isAccepted());
    }

    @Test
    void doDelete() throws Exception {
        doNothing().when(customerService).deleteCustomer(any());
        this.mvc.perform(delete("/api/v1/customer/" + UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void expectBadRequestForConstraintViolationExceptionWithBlankName() throws Exception {
        CustomerDto dto = getCustomer();
        dto.setName(" ");
        String json = mapper.writeValueAsString(dto);
        UUID uuid = UUID.randomUUID();
        doNothing().when(customerService).updateCustomer(any());

        this.mvc.perform(post("/api/v1/customer")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    void expectBadRequestForConstraintViolationExceptionWithNameHaving2chars() throws Exception {
        CustomerDto dto = CustomerDto.builder()
                .name("Ja")
                .build();
        String json = mapper.writeValueAsString(dto);
        UUID uuid = UUID.randomUUID();
        this.mvc.perform(put("/api/v1/customer/" + uuid)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest());
    }
    @Test
    void expectBadRequestForConstraintViolationExceptionWithNameGreaterThan100chars() throws Exception {
        CustomerDto dto = CustomerDto.builder()
                .name("James Brown II expectBadRequestForConstraintViolationExceptionWithNameGreaterThan100chars expectBadRequestForConstraintViolationExceptionWithNameGreaterThan100chars")
                .build();
        String json = mapper.writeValueAsString(dto);
        UUID uuid = UUID.randomUUID();
        doNothing().when(customerService).updateCustomer(any());

        this.mvc.perform(put("/api/v1/customer/" + uuid)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest());
    }
}