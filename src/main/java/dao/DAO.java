package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
    public static Connection con;

    public DAO(){
        if(con == null){
            String dbUrl =
                    "jdbc:mysql://localhost:3306/qlmathang?autoReconnect=false&useSSL=true&clientInteractive=true";
            String dbClass = "com.mysql.jdbc.Driver";
            try {
                Class.forName(dbClass);
                con = DriverManager.getConnection (dbUrl, "root", "91020.Nam");
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
