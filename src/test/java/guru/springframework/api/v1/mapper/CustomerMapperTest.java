package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerMapperTest {


    public static final String FIRSTNAME = "John";
    public static final String LASTNAME = "Wick";
    public static final String CUSTOMER_URL = "shop/customers/1";

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;


    @Test
    public void customerToCustomerDTO() throws Exception {
        //Given
        Customer customer = new Customer();
        customer.setFirstname(FIRSTNAME);
        customer.setLastname(LASTNAME);
        customer.setCustomerUrl(CUSTOMER_URL);

        //When
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);

        //Then
        assertEquals(FIRSTNAME, customerDTO.getFirstname());
        assertEquals(LASTNAME, customerDTO.getLastname());
        assertEquals(CUSTOMER_URL, customerDTO.getCustomerUrl());
    }
}
