package Repository;

import DataSourceMock.RepositoryDataSource;
import Model.Account;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AccountRepository {
    private RepositoryDataSource repositoryDataSource;

    public AccountRepository (RepositoryDataSource repositoryDataSource) {
        this.repositoryDataSource = repositoryDataSource;
    }

    public Account getAccountById(int id) {
        return repositoryDataSource.id2AccountMap.get(id);
    }

    public String getAccountById2String(int id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(getAccountById(id));
    }

    public Account getAccountByAccountNumber(String accountNumber) {
        return repositoryDataSource.accountNumber2AccountMap.get(accountNumber);
    }

    public String getAccountByAccountNumber2String(String accountNumber) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(getAccountByAccountNumber(accountNumber));
    }

    public void upsertAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("account should not be null");
        }

        int id = account.id;
        String accountNumber = account.accountNumber;
        repositoryDataSource.id2AccountMap.put(id, account);
        repositoryDataSource.accountNumber2AccountMap.put(accountNumber, account);
    }

}
