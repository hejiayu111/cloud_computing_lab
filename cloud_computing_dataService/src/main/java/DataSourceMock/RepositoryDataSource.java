package DataSourceMock;

import Model.Account;
import Model.Customer;
import Model.Transaction;

import java.util.HashMap;

public class RepositoryDataSource {
    public HashMap<Integer, Account> id2AccountMap;
    public HashMap<String, Account> accountNumber2AccountMap;
    public HashMap<Integer, Customer> id2CustomerMap;
    public HashMap<Integer, Transaction> id2TransactionMap;

    public RepositoryDataSource () {
        this.id2AccountMap = new HashMap<>();
        this.accountNumber2AccountMap = new HashMap<>();
        this.id2CustomerMap = new HashMap<>();
        this.id2TransactionMap = new HashMap<>();
    }
}
