package guru.springframework.service;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;
import guru.springframework.repository.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    public static final String FIRSTNAME = "Anna";
    public static final String LASTNAME = "Maxima";
    public static final long ID = 1;
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    public void getAllCustomers() throws Exception {
        //Given
        List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());
        when(customerRepository.findAll()).thenReturn(customers);

        //When
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        //Then
        assertEquals(3, customerDTOS.size());
    }

    @Test
    public void getCustomerById() throws Exception {
        //Given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstname(FIRSTNAME);
        customer.setLastname(LASTNAME);
        when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(customer));
        //When
        CustomerDTO customerDTO = customerService.getCustomerById(ID);
        //Then
        assertEquals(ID, (long)customerDTO.getId());
        assertEquals(FIRSTNAME, customerDTO.getFirstname());
        assertEquals(LASTNAME, customerDTO.getLastname());
    }

    @Test
    public void createNewCustomer() throws Exception {
        //Given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Alex");
        customerDTO.setLastname("Smith");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstname(customerDTO.getFirstname());
        savedCustomer.setLastname(customerDTO.getLastname());
        savedCustomer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
        //When
        CustomerDTO savedCustomerDTO = customerService.createNewCustomer(customerDTO);

        //Then
        assertEquals(customerDTO.getFirstname(), savedCustomerDTO.getFirstname());
        assertEquals("/api/v1/customers/1", savedCustomerDTO.getCustomerUrl());
    }

    @Test
    public void saveCustomerByDTO() throws Exception {

        //Given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Jim");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstname(customerDTO.getFirstname());
        savedCustomer.setLastname(customerDTO.getLastname());
        savedCustomer.setId(1l);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //When
        CustomerDTO savedDto = customerService.saveCustomerByDTO(1L, customerDTO);

        //Then
        assertEquals(customerDTO.getFirstname(), savedDto.getFirstname());
        assertEquals("/api/v1/customers/1", savedDto.getCustomerUrl());
    }

    @Test
    public void deleteCustomerById() throws Exception {
        Long id = 1L;
        customerRepository.deleteById(id);
        verify(customerRepository, times(1)).deleteById(anyLong());
    }
}