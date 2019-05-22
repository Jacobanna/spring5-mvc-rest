package guru.springframework.controller.v1;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static guru.springframework.controller.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void getAllCustomers() throws Exception {
        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setFirstname("Kayne");
        customerDTO1.setLastname("War");
        customerDTO1.setCustomerUrl("/api/v1/customer/1");

        CustomerDTO customerDTO2 = new CustomerDTO();
        customerDTO2.setFirstname("Max");
        customerDTO2.setLastname("Gon");
        customerDTO2.setCustomerUrl("/api/v1/customer/2");

        CustomerDTO customerDTO3 = new CustomerDTO();
        customerDTO3.setFirstname("Adam");
        customerDTO3.setLastname("Smith");
        customerDTO3.setCustomerUrl("/api/v1/customer/3");

        List<CustomerDTO> customerDTOS = Arrays.asList(customerDTO1, customerDTO2, customerDTO3);

        when(customerService.getAllCustomers()).thenReturn(customerDTOS);

        mockMvc.perform(get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(3)));
    }

    @Test
    public void getCustomerById() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Adam");
        customerDTO.setLastname("Smith");
        customerDTO.setCustomerUrl("/api/v1/customer/1");

        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO);

        mockMvc.perform(get("/api/v1/customers/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Adam")));
    }

    @Test
    public void createNewCustomer() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Fred");
        customerDTO.setLastname("Flintstone");

        CustomerDTO returnConsumerDTO = new CustomerDTO();
        returnConsumerDTO.setFirstname(customerDTO.getFirstname());
        returnConsumerDTO.setLastname(customerDTO.getLastname());
        returnConsumerDTO.setCustomerUrl("/api/v1/customers/1");

        when(customerService.createNewCustomer(customerDTO)).thenReturn(returnConsumerDTO);

        //when&then
        mockMvc.perform(post("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo("Fred")))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
    }

    @Test
    public void updateCustomer() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Fred");
        customerDTO.setLastname("Flintstone");

        CustomerDTO returnCustomerDTO = new CustomerDTO();
        returnCustomerDTO.setFirstname(customerDTO.getFirstname());
        returnCustomerDTO.setLastname(customerDTO.getLastname());
        returnCustomerDTO.setCustomerUrl("/api/v1/customers/1");

        when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnCustomerDTO);

        //when/then
        mockMvc.perform(put("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo("Fred")))
                .andExpect(jsonPath("$.lastname", equalTo("Flintstone")))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
    }
}