package Repository;

import DataSourceMock.RDSDataSource;
import DataSourceMock.RepositoryDataSource;
import Model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CustomerRepository {

    private RepositoryDataSource repositoryDataSource;

//    private void insertTestData(String dataPath) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        InputStream is = Customer.class.getResourceAsStream(dataPath);
//        Customer customer = mapper.readValue(is, Customer.class);
//        this.upsertCustomer(customer);
//    }

    public CustomerRepository (RepositoryDataSource repositoryDataSource) throws IOException {
        this.repositoryDataSource = repositoryDataSource;
//        insertTestData("/customerTestData.json");
    }

    public Customer getCustomerById(int id) throws SQLException {
        PreparedStatement statement = RDSDataSource.newPreparedStatement("select * from Customer where id=?");
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        try {
            if (!rs.first()) {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
        Customer customer = new Customer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
        return customer;
    }

    private Customer insertCustomer (Customer customer) throws SQLException{
        PreparedStatement statement = RDSDataSource.newPreparedStatement("insert into Customer values (?, ?, ?)");
        statement.setString(1, customer.firstName);
        statement.setString(2, customer.lastName);
        statement.setString(3, customer.associatedAccountNumber);
        statement.execute();
        return customer;
    }

    private Customer updateCustomer (Customer existingCustomer, Customer customer) throws SQLException{
        customer.id = existingCustomer.id;
        PreparedStatement statement = RDSDataSource.
                newPreparedStatement("update Customer set firstName=?, lastName=?, associatedAccountNumber=? where Customer.id=?");
        statement.setInt(4, customer.id);
        statement.setString(1, customer.firstName);
        statement.setString(2, customer.lastName);
        statement.setString(3, customer.associatedAccountNumber);
        statement.execute();
        return customer;
    }
    
    private String convertCustomer2String (Customer customer) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(customer);
    }

    public String upsertCustomer(Customer customer) throws SQLException, IOException {
        if (customer == null) {
            throw new IllegalArgumentException("customer should not be null");
        }

        int id = customer.id;

        Customer existingCustomer = getCustomerById(id);
        if (existingCustomer == null) {
            return convertCustomer2String(insertCustomer(customer));
        } else {
            return convertCustomer2String(updateCustomer(existingCustomer, customer));
        }
    }

    public String getCustomerById2String(int id) throws IOException, SQLException {
        Customer customer= getCustomerById(id);
        return convertCustomer2String(customer);
    }
}
