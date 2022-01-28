package Server;

import Model.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println("launching the server...");
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
            server.setExecutor(threadPoolExecutor);
            server.createContext("/account", new AccountHttpHandler());
            server.createContext("/customer", new CustomerHttpHandler());
            server.start();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
