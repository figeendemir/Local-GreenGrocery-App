/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package greengrocery;

import java.sql.Date;


public class ProductData {
    
    private Integer product_id;
    private String product_name;
    private String product_status;
    private Double product_price;
    private Date order_date;
    private String product_image;
    private Double product_amount;
    
    public ProductData(Integer product_id, String product_name, String product_status, Double product_price
            , String product_image, Date order_date,Double product_amount){
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_status = product_status;
        this.product_price = product_price;
        this.order_date = order_date;
        this.product_image = product_image;
        this.product_amount = product_amount;
    }
    
    public ProductData(Integer product_id, String product_name, String product_status, Double product_price,
            String product_image){
        
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_image = product_image;
    }
    
    public Integer getProdcutId(){
        return product_id;
    }
    
    public String getName(){
        return product_name;
    }
    public String getStatus(){
        return product_status;
    }
    public Double getPrice(){
        return product_price;
    }
    
    public String getImage(){
        return product_image;
    }
    public Date getDate(){
        return order_date;
    }
    public Double getAmount(){
        return product_amount;
    }
    
}