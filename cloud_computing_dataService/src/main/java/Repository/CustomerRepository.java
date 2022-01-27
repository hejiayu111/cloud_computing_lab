package Repository;

import DataSourceMock.RepositoryDataSource;
import Model.Account;
import Model.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class CustomerRepository {

    private RepositoryDataSource repositoryDataSource;

    public CustomerRepository (RepositoryDataSource repositoryDataSource) {
        this.repositoryDataSource = repositoryDataSource;
    }

    public Customer getCustomerById(int id) {
        return repositoryDataSource.id2CustomerMap.get(id);
    }

    public void upsertCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("customer should not be null");
        }

        int id = customer.id;
        repositoryDataSource.id2CustomerMap.put(id, customer);
    }

    public String getCustomerById2String(int id) throws JsonProcessingException {
        Customer customer= getCustomerById(id);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(customer);
    }
}
