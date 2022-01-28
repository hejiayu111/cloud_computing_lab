package Server;

import DataSourceMock.RepositoryDataSource;
import Model.Account;
import Model.Customer;
import Repository.AccountRepository;
import Repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomerHttpHandler implements HttpHandler {
    private final CustomerRepository customerRepository = new CustomerRepository(new RepositoryDataSource());
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomerHttpHandler() throws IOException {
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
        try {
            if (httpExchange.getRequestMethod().equals("GET")) {
                String query = httpExchange.getRequestURI().getQuery();
                Map<String, String> queryMap = queryToMap(query);
                String id = queryMap.get("id");
                Customer customer = customerRepository.getCustomerById(Integer.parseInt(id));
                response = objectMapper.writeValueAsString(customer);
            } else if (httpExchange.getRequestMethod().equals("POST")) {
                Headers headers = httpExchange.getRequestHeaders();
                Set<Map.Entry<String, List<String>>> entries = headers.entrySet();
            }
            httpExchange.getResponseHeaders().set("Content-Type", "application/json");
            httpExchange.sendResponseHeaders(200, response.length());

        } catch (Exception e) {
            httpExchange.getResponseHeaders().set("Content-Type", "application/json");
            httpExchange.sendResponseHeaders(400, response.length());
        }

        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
