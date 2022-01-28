package Repository;

import DataSourceMock.RepositoryDataSource;
import DataSourceMock.RDSDataSource;

import Model.Account;
import Model.AccountStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRepository {
    private RepositoryDataSource repositoryDataSource;

    private void insertTestData(String dataPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = Account.class.getResourceAsStream(dataPath);
        Account account = mapper.readValue(is, Account.class);
        this.upsertAccount(account);
    }

    public AccountRepository(RepositoryDataSource repositoryDataSource) throws IOException {
        this.repositoryDataSource = repositoryDataSource;
        // insertTestData("/accountTestData.json");
    }

    public Account getAccountById(int id) {
        Account account = null;
        try {
            PreparedStatement statement = RDSDataSource.newPreparedStatement("select * from Account where id = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            AccountStatus status = AccountStatus.values()[rs.getInt(4)];
            account = new Account(rs.getInt(1), rs.getString(2), rs.getDouble(3), status);

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return account;
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
