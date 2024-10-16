/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package greengrocery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserInfoDB {
    
    public static Connection connectDb(){
        
        try{
            
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/userinfo", "root", "");  
            return connect;
        }catch(ClassNotFoundException | SQLException e){}
        return null;
    }    

    static com.mysql.jdbc.Connection connectDB() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    
}
