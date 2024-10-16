/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package greengrocery;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.awt.Insets;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static oracle.net.aso.C00.x;


public class CustomerController implements Initializable {

    @FXML
    private TableColumn<?, ?> shopping_col_productAmount;

    @FXML
    private TableColumn<?, ?> shopping_col_productName;

    @FXML
    private TableColumn<?, ?> shopping_col_productPrice;

    @FXML
    private GridPane shopping_menuGrid;

    @FXML
    private Button shopping_menuPayBtn;

    @FXML
    private Button shopping_menuReceiptBtn;

    @FXML
    private Button shopping_menuRemoveBtn;

    @FXML
    private ScrollPane shopping_menuScrol;

    @FXML
    private TableView<?> shopping_menuTable;

    @FXML
    private Button shopping_menu_cart;

    @FXML
    private AnchorPane shopping_menu_form;

    @FXML
    private Label shopping_menu_group;

    @FXML
    private Label shopping_menu_name;

    @FXML
    private Button shopping_menu_product;

    @FXML
    private Button shopping_menu_sıgnOut;
    
    @FXML
    private TextField shopping_menuTotal;
    
    private Alert alert;
    
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private java.sql.ResultSet result;
    
    private Image image;
    
    
    private ObservableList<ProductData> productListData = FXCollections.observableArrayList();
    
    
    public ObservableList<CustomerData> CustomerListData() {

        ObservableList<CustomerData> customerist = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customers";

        connect = (Connection) UserInfoDB.connectDb();

        try {
            prepare = (PreparedStatement) connect.prepareStatement(sql);
            result = (java.sql.ResultSet) prepare.executeQuery();

            CustomerData customers;

            while (result.next()) {
                customers = new CustomerData(result.getString("customer_id"),result.getString("customer_firstName"), result.getString("customer_password"),
                         result.getString("customer_lastName"),
                         result.getDate("order_date"));

                customerist.add(customers);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerist;
    }
    

    
    public void displayCustomerName(){
        shopping_menu_name.setText(Data.customerid);
    
    }
    public ObservableList<ProductData> menuGetData() {
        
        String sql = "SELECT * FROM products";
        
        ObservableList<ProductData> productListData = FXCollections.observableArrayList();
        connect = UserInfoDB.connectDB();
        
        try {
            prepare = (PreparedStatement) connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            ProductData prod;
            
            while (result.next()) {
                prod = new ProductData(result.getInt("product_id"),
                        result.getString("product_name"),
                        result.getString("product_status"),
                        result.getDouble("product_price"),
                        result.getString("product_image"),
                        result.getDate("order_date"),
                        result.getDouble("product_amount"));
                
                productListData.add(prod);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return productListData;
    } 
    
    public void menuDisplayCard() {
        
        productListData.clear();
        productListData.addAll(menuGetData());
        
        int row = 0;
        int column = 0;
        
        shopping_menuGrid.getChildren().clear();
        shopping_menuGrid.getRowConstraints().clear();
        shopping_menuGrid.getColumnConstraints().clear();
        
        for (int q = 0; q < productListData.size(); q++) {
            
            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("Product.fxml"));
                AnchorPane pane = load.load();
                ProductController cardC = load.getController();
                cardC.setData(productListData.get(q));
                
                if (column == 3) {
                    column = 0;
                    row += 1;
                }
                
                shopping_menuGrid.add(pane, column++, row);
                
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    
    public void switchForm(ActionEvent event) {
        
        if (event.getSource() == shopping_menu_cart) {
            shopping_menu_cart.setVisible(true);
            shopping_menu_product.setVisible(false);

            
        } else if (event.getSource() == shopping_menu_product) {
            shopping_menu_cart.setVisible(false);
            shopping_menu_product.setVisible(true);
          
        }
        
    }
    
    private double x=0;
    private double y=0;
    
    public void logout() {

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Do you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                shopping_menu_sıgnOut.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(getClass().getResource("FXMLDoc.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayCustomerName();
        
        menuDisplayCard();
    }    
    
}
