package Repository;

import DataSourceMock.RepositoryDataSource;
import Model.Customer;
import Model.Transaction;
import Model.TransactionType;

public class TransactionRepository {

    private RepositoryDataSource repositoryDataSource;

    public TransactionRepository (RepositoryDataSource repositoryDataSource) {
        this.repositoryDataSource = repositoryDataSource;
    }

    public Transaction getTransactionById(int id) {
        return repositoryDataSource.id2TransactionMap.get(id);
    }

    public void upsertCustomer(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("customer should not be null");
        }

        int id = transaction.id;
        repositoryDataSource.id2TransactionMap.put(id, transaction);
    }
}
