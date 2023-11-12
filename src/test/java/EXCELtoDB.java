
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class EXCELtoDB  {


    protected static Connection connection;
    protected static Statement statement;

    @BeforeTest
    public void DBConnectionOpen() {

        String url = "jdbc:mysql://db-technostudy.ckr1jisflxpv.us-east-1.rds.amazonaws.com:3306/z_nurhayatOgrenciKayit"; //hostname, port, db adı
        String username = "root";
        String password = "'\"-LhCB'.%k[4S]z";


        try {
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test3() throws SQLException, IOException {
        String path = "src/test/java/excelFiles/JDBCOdev.xlsx";
        FileInputStream inputStream = new FileInputStream(path);
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        String sql = " insert into cucumberOdev (id, ad,soyad,telefon) values(?)";
        String databaseSil = "truncate cucumberOdev";
        PreparedStatement ps = connection.prepareStatement(databaseSil);
        ps.executeUpdate(databaseSil);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            int id = (int) row.getCell(0).getNumericCellValue();
            String ad = row.getCell(1).getStringCellValue();
            String soyad = row.getCell(2).getStringCellValue();
            String telefon = row.getCell(3).getStringCellValue();

            sql = "insert into cucumberOdev values('" + id + "','" + ad + "','" + soyad + "','" + telefon + "')";
            ps.execute(sql);
            ps.execute("commit");
        }
    }

    @AfterTest
    public void DBConnectionClose () {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
