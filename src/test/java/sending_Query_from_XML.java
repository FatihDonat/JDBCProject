import org.testng.annotations.AfterTest;
        import org.testng.annotations.BeforeTest;
        import org.testng.annotations.Parameters;
        import org.testng.annotations.Test;

        import java.sql.*;

public class sending_Query_from_XML {


    protected static Connection connection;
    protected static Statement statement;

    @BeforeTest
    public void DBConnectionOpen() {

        String url="jdbc:mysql://db-technostudy.ckr1jisflxpv.us-east-1.rds.amazonaws.com:3306/sakila"; //hostname, port, db adı
        String username="root";
        String password="'\"-LhCB'.%k[4S]z";


        try {
            connection = DriverManager.getConnection(url, username, password);
            statement=connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    @Parameters("query")
    public void test1(String query) throws SQLException {
        ResultSet rs=statement.executeQuery(query);
        ResultSetMetaData rsmd=rs.getMetaData();

        for(int i=1; i<= rsmd.getColumnCount(); i++)
            System.out.printf("%-20s",rsmd.getColumnName(i));

        System.out.println();

        while (rs.next())           {
            for(int i=1; i<= rsmd.getColumnCount(); i++)
                System.out.printf("%-20s",rs.getString(i));

            System.out.println();
        }
        System.out.println("----------------------------------------------------------");
    }

    @AfterTest
    public void DBConnectionClose() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}