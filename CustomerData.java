/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package greengrocery;

import java.util.Date;


public class CustomerData {
    
    private String customer_id;
    private String customer_username;
    private String customer_password;
    private String customer_firstName;
    private String customer_lastName;
    private Date order_date;
    
    public CustomerData(String customer_id, String customer_password,String customer_firstName ,String customer_lastName, Date order_date){
        this.customer_id = customer_id;
        this.customer_password = customer_password;
        this.customer_firstName = customer_firstName;
        this.customer_lastName = customer_lastName;
        this.order_date = order_date;
    }
    
    
    
    public String getcustomerId(){
        return customer_id;
    }
    
    public String getpassword(){
        return customer_password;
    }
    public String getfirstName(){
        return customer_firstName;
    }
    public String getlastName(){
        return customer_lastName;
    }
    
    public Date getDate(){
        return order_date;
    }
}
