package guru.springframework.bootstrap;

import guru.springframework.domain.Category;
import guru.springframework.domain.Customer;
import guru.springframework.repository.CategoryRepository;
import guru.springframework.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        populateCategories();
        populateCustomers();
    }

    private void populateCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Data Loaded - " + categoryRepository.count() + " elements added to Category table.");
    }

    private void populateCustomers() {
        Customer customer1 = new Customer();
        customer1.setFirstname("Anna");
        customer1.setLastname("Zalewsky");

        Customer customer2 = new Customer();
        customer2.setFirstname("Mark");
        customer2.setLastname("Robak");

        Customer customer3 = new Customer();
        customer3.setFirstname("Derek");
        customer3.setLastname("Konkin");

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

        System.out.println("Data Loaded - " + customerRepository.count() + " elements added to Customer table.");
    }
}
