package DataSourceMock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RDSDataSource {

    private final Connection conn;
    private static RDSDataSource instance = null;

    public RDSDataSource() throws SQLException {
        conn = DriverManager.getConnection(
                "jdbc:mysql://lab-database.cguvf7srcy3k.us-east-2.rds.amazonaws.com:3306/lab", "admin", "lablablab");
    }

    public static RDSDataSource getInstance() {
        if (instance == null) {
            try {
                instance = new RDSDataSource();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public static PreparedStatement newPreparedStatement(String query) throws SQLException {
        return getInstance().conn.prepareStatement(query);
    }
}
