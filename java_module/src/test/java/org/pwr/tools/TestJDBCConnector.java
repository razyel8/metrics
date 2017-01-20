package org.pwr.tools;

import org.junit.Test;
import org.pwr.tool.JDBCConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestJDBCConnector {

    @Test
    public void testConnector() {
        JDBCConnector.getInstance().testStatement();
    }


    @Test
    public void testSelect() {
        try {
            Statement stmt = JDBCConnector.getInstance().getConnection().createStatement();
            String sql;
            sql = "SELECT * FROM classentity";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                //Retrieve by column name
                String id = rs.getString("sh1");
                String filePath = rs.getString("filePath");
                String message = rs.getString("message");
                System.out.println(id + ", " + filePath + ", " +  message);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    @Test
    public void testInsert() {
        JDBCConnector.getInstance().insertStatement("INSERT INTO classentity (sh1, filePath, author, date, action, message) VALUES ('ffdfewqfewq', 'path/path2', 'myAuthor', '12345687897789789', 'M', 'lcdcdscdscsd long long message');");
    }


}
