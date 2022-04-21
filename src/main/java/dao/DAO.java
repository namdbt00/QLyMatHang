package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
    public static Connection con;

    public DAO(){
        if(con == null){
            String dbUrl =
                    "jdbc:mysql://azuinst.tk:3306/qlmathang?autoReconnect=false&useSSL=true&clientInteractive=true";
            String dbClass = "com.mysql.jdbc.Driver";
            try {
                Class.forName(dbClass);
                con = DriverManager.getConnection (dbUrl, "admin", "admin@1234");
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
