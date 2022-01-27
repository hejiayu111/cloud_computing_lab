package Server;

import DataSourceMock.RepositoryDataSource;
import Model.Account;
import Repository.AccountRepository;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class AccountHttpHandler implements HttpHandler {

    private final AccountRepository accountRepository = new AccountRepository(new RepositoryDataSource());
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AccountHttpHandler() throws IOException {
    }


    public Map<String, String> queryToMap(String query) {
        if(query == null) {
            return null;
        }
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            }else{
                result.put(entry[0], "");
            }
        }
        return result;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        if (httpExchange.getRequestMethod().equals("GET")) {
            String query = httpExchange.getRequestURI().getQuery();
            Map<String, String> queryMap = queryToMap(query);
            String id = queryMap.get("id");
            Account account = accountRepository.getAccountById(Integer.parseInt(id));
            response = objectMapper.writeValueAsString(account);
        }

        httpExchange.getResponseHeaders().set("Content-Type", "application/json");
        httpExchange.sendResponseHeaders(200, response.length());

        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
