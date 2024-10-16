/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package greengrocery;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class FXMLDocumentController implements Initializable {
    

    @FXML
    private AnchorPane admin_form;

    @FXML
    private Hyperlink admin_hy;

    @FXML
    private Button admin_loginBtn;

    @FXML
    private PasswordField admin_password;

    @FXML
    private TextField admin_username;

    @FXML
    private AnchorPane customer_form;

    @FXML
    private Hyperlink customer_hy;

    @FXML
    private Button customer_loginBtn;

    @FXML
    private PasswordField customer_password;

    @FXML
    private TextField customer_username;

    @FXML
    private AnchorPane main_form;
    
    @FXML
    private AnchorPane carrier_form;

    @FXML
    private Hyperlink carrier_hy;

    @FXML
    private Hyperlink carrier_hy2;

    
    
    public void swithcForm (ActionEvent event){
        if(event.getSource()== admin_hy){
            admin_form.setVisible(false);
            customer_form.setVisible(true);
            carrier_form.setVisible(false);
        
        }else if(event.getSource()==customer_hy){
            admin_form.setVisible(true);
            customer_form.setVisible(false);
            carrier_form.setVisible(false);
       
        }else if(event.getSource()== carrier_hy){
            admin_form.setVisible(false);
            customer_form.setVisible(false);
            carrier_form.setVisible(true);}
    }
    

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    
    private double x = 0;
    private double y = 0;
    
    
    public void customerlogin(){
        String customerData = "SELECT * FROM customers WHERE customer_username = ? AND customer_password = ?";
        connect = UserInfoDB.connectDb();
        
        try{
            prepare = connect.prepareStatement(customerData);
            prepare.setString(1, customer_username.getText());
            prepare.setString(2, customer_password.getText());
            
            result = prepare.executeQuery();
            
            Alert alert;
            
            if(customer_username.getText().isEmpty() || customer_password.getText().isEmpty()){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter your username and password");
                alert.showAndWait();
            }else{
                if(result.next()){
                    
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();
                    
                    Data.customerid =customer_username.getText();

                    customer_loginBtn.getScene().getWindow().hide();
                    
                    Parent root = FXMLLoader.load(getClass().getResource("Customer.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    
                    root.setOnMousePressed((MouseEvent event) ->{
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });

                    root.setOnMouseDragged((MouseEvent event) ->{
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);
                    });
                    
                    stage.initStyle(StageStyle.TRANSPARENT);
                    
                    stage.setScene(scene);
                    stage.show();
                    
                }else{
                    
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username/Password");
                    alert.showAndWait();  
                }
            }
            
        }catch(IOException | SQLException e){}
    
    
    
    }
    
    public void ownerlogin(){
        String ownerData = "SELECT * FROM admin WHERE admin_username = ? AND admin_password = ?";
        connect = UserInfoDB.connectDb();
        
        try{
            prepare = connect.prepareStatement(ownerData);
            prepare.setString(1, admin_username.getText());
            prepare.setString(2, admin_password.getText());
            
            result = prepare.executeQuery();
            
            Alert alert;
            
            if(admin_username.getText().isEmpty() || admin_password.getText().isEmpty()){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter your username and password");
                alert.showAndWait();
            }else{
                if(result.next()){
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();
                    
                    Data.username = admin_username.getText();
                    
                    admin_loginBtn.getScene().getWindow().hide();
                    
                    Parent root = FXMLLoader.load(getClass().getResource("/greengrocery/Owner.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    
                    root.setOnMousePressed((MouseEvent event) ->{
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });

                    root.setOnMouseDragged((MouseEvent event) ->{
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);
                    });
                    
                    stage.initStyle(StageStyle.TRANSPARENT);
                    
                    stage.setScene(scene);
                    stage.show();
                    
                }else{
                    
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username/Password");
                    alert.showAndWait();
                    
                }
            }
            
        }catch(IOException | SQLException e){}
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
}