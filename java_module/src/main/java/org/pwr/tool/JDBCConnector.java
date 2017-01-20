package org.pwr.tool;

import java.sql.*;

public class JDBCConnector {

    private static JDBCConnector Instance;

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/metrics?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
    //static final String DB_URL = "jdbc:sqlserver://wpmg.database.windows.net:1433;database=wpmg;user=wpmg@wpmg;password={your_password_here};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30";
    //  Database credentials
    static final String USER = "root";
    static final String PASS = "pass";
    Connection conn;

    JDBCConnector(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            //conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    @Deprecated
    public ResultSet selectStatement(String statement){
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            String sql;
            sql = statement;
            ResultSet rs = stmt.executeQuery(sql);

//            while(rs.next()){
//                //Retrieve by column name
//                int id  = rs.getInt("id");
//                int age = rs.getInt("age");
//                String first = rs.getString("first");
//                String last = rs.getString("last");
//
//                //Display values
//                System.out.print("ID: " + id);
//                System.out.print(", Age: " + age);
//                System.out.print(", First: " + first);
//                System.out.println(", Last: " + last);
//            }
//       STEP 6: Clean-up environment
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertStatement(String statement){
        Statement stmt = null;
        try{
            stmt = conn.createStatement();
            String sql = statement;
            stmt.executeUpdate(sql);
            stmt.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        return conn;
    }

    public static JDBCConnector getInstance(){
        if(Instance == null){
            Instance = new JDBCConnector();
        }
        return Instance;
    }

    public static void main(String[] args) {

    }

    public void testStatement(){

    }

}
