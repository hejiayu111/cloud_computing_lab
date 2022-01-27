package Repository;

import DataSourceMock.RepositoryDataSource;
import Model.Customer;
import Model.Transaction;
import Model.TransactionType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TransactionRepository {

    private RepositoryDataSource repositoryDataSource;

    public TransactionRepository (RepositoryDataSource repositoryDataSource) {
        this.repositoryDataSource = repositoryDataSource;
    }

    public Transaction getTransactionById(int id) {
        return repositoryDataSource.id2TransactionMap.get(id);
    }

    public String getTransactionById2String(int id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(getTransactionById(id));
    }

    public void upsertCustomer(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("customer should not be null");
        }

        int id = transaction.id;
        repositoryDataSource.id2TransactionMap.put(id, transaction);
    }
}
