
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

public class DBtoEXCEL{


    protected static Connection connection;
    protected static Statement statement;

    @BeforeTest
    public void DBConnectionOpen() {


        String url="*****"; //hostname, port, db
        String username="*****";
        String password="*****";


        try {
            connection = DriverManager.getConnection(url, username, password);
            statement=connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void test() throws SQLException, IOException {
        ResultSet rs = statement.executeQuery("select * from actor");
        ResultSetMetaData rsmd = rs.getMetaData();

        int columnCount = rsmd.getColumnCount();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Actor");
        Row newRow = sheet.createRow(0);
        for (int i = 0; i < columnCount; i++) {
            Cell newCell = newRow.createCell(i);
            newCell.setCellValue(rsmd.getColumnName(i + 1));
        }
        int sayac = 1;

        while (rs.next()) {
            newRow = sheet.createRow(sayac);
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                Cell newCell = newRow.createCell(i);
                newCell.setCellValue(rs.getString(i + 1));
            }
            sayac++;
        }

        String yeniExcelPath = "src/test/java/actorDatabase.xlsx";
        FileOutputStream outputStream = new FileOutputStream(yeniExcelPath);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
        System.out.println("İşlem tamamlandı");
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