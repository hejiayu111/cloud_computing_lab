package Repository;

import DataSourceMock.RepositoryDataSource;
import Model.Account;
import Model.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;


public class CustomerRepository {

    private RepositoryDataSource repositoryDataSource;

    private void insertTestData(String dataPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = Customer.class.getResourceAsStream(dataPath);
        Customer customer = mapper.readValue(is, Customer.class);
        this.upsertCustomer(customer);
    }

    public CustomerRepository (RepositoryDataSource repositoryDataSource) throws IOException {
        this.repositoryDataSource = repositoryDataSource;
        insertTestData("/customerTestData.json");
    }

    public Customer getCustomerById(int id) throws IOException {
        return repositoryDataSource.id2CustomerMap.get(id);
    }

    public void upsertCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("customer should not be null");
        }

        int id = customer.id;
        repositoryDataSource.id2CustomerMap.put(id, customer);
    }

    public String getCustomerById2String(int id) throws IOException {
        Customer customer= getCustomerById(id);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(customer);
    }
}
