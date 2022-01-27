package Repository;

import DataSourceMock.RepositoryDataSource;
import Model.Account;
import Model.Customer;

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
}
