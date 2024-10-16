/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package greengrocery;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class OwnerController implements Initializable{
    
    
    @FXML
    private AnchorPane main_form;
    
    @FXML
    private Button SignOut_Btn;

    @FXML
    private Button profile_Btn;

    @FXML
    private BarChart<?, ?> profile_chart;

    @FXML
    private AnchorPane profile_form;

    @FXML
    private Label profile_stock;

    @FXML
    private Label profile_total;

    @FXML
    private Button stock_Btn;

    @FXML
    private AnchorPane stock_form;

    @FXML
    private Button stock_product_addBtn;

    @FXML
    private TextField stock_product_amount;

    @FXML
    private TableColumn<ProductData, String> stock_product_col_producname;

    @FXML
    private TableColumn<ProductData, String> stock_product_col_productid;

    @FXML
    private TableColumn<ProductData, String> stock_product_col_productprice;

    @FXML
    private TableColumn<ProductData, String> stock_product_col_productstatus;

    @FXML
    private Button stock_product_deleteBtn;

    @FXML
    private TextField stock_product_id;

    @FXML
    private ImageView stock_product_image;

    @FXML
    private Button stock_product_importBtn;

    @FXML
    private TextField stock_product_name;

    @FXML
    private TextField stock_product_price;

    @FXML
    private TextField stock_product_search;

    @FXML
    private TableView<ProductData> stock_product_table;

    @FXML
    private Button stock_product_updateBtn;

    @FXML
    private Label username;
    
    
    
    
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private Image image;
    
    
   
    
    
    public void ProductAdd() {

        String sql = "INSERT INTO products (product_id, product_name, product_status, product_price, product_image, order_date,product_amount) "
                + "VALUES(?,?,?,?,?,?)";

        connect = (Connection) UserInfoDB.connectDb();

        try {
            Alert alert;

            if (stock_product_id.getText().isEmpty()
                    || stock_product_name.getText().isEmpty()
                    || stock_product_amount.getText().isEmpty()
                    || stock_product_price.getText().isEmpty()
                    || Data.path == null || Data.path == " ") {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            } else {
                String checkData = "SELECT product_id FROM products WHERE product_id = '"
                        + stock_product_id.getText() + "'";

                statement = (Statement) connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Product ID: " + stock_product_id.getText() + " was already exist!");
                    alert.showAndWait();
                } else {
                    prepare = (PreparedStatement) connect.prepareStatement(sql);
                    prepare.setString(1, stock_product_id.getText());
                    prepare.setString(2, stock_product_name.getText());
                    prepare.setString(3, stock_product_price.getText());
                    prepare.setString(4, stock_product_amount.getText());

                    String uri = Data.path;
                    uri = uri.replace("\\", "\\\\");
                    prepare.setString(5, uri);

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(6, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    ProductShowListData();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public void ProductUpdate() {

        String uri = Data.path;
        if (!(uri == null || uri == "")) {
            uri = uri.replace("\\", "\\\\");
        }

        String sql = "UPDATE products SET name = '"
                + stock_product_name.getText() + "', amount = '"
                + stock_product_amount.getText() + "', price = '"
                + stock_product_price.getText() + "', image = '"
                + uri + "' WHERE product_id = '" + stock_product_id.getText() + "'";

        connect = (Connection) UserInfoDB.connectDb();

        try {
            Alert alert;

            if (stock_product_id.getText().isEmpty()
                    || stock_product_name.getText().isEmpty()
                    || stock_product_amount.getText().isEmpty()
                    || stock_product_price.getText().isEmpty()
                    || uri == null || uri == "" || Data.path == null || Data.path == "") {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Product ID: " + stock_product_id.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = (Statement) connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    ProductShowListData();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public void ProductDelete() {

        String sql = "DELETE FROM products WHERE product_id = '"
                + stock_product_id.getText() + "'";

        connect = (Connection) UserInfoDB.connectDb();

        try {
            Alert alert;

            if (stock_product_id.getText().isEmpty()
                    || stock_product_name.getText().isEmpty()
                    || stock_product_amount.getText().isEmpty()
                    || stock_product_price.getText().isEmpty()
                    || Data.path == null || Data.path == "") {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Do you want to DELETE Product ID: " + stock_product_id.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = (Statement) connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    ProductShowListData();

          
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    

    
          
    
   
    
    public void ProductInsertImage() {

        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new ExtensionFilter("Image File", "*jpg", "*png"));

        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {

            Data.path = file.getAbsolutePath();

            image = new Image(file.toURI().toString(), 161, 104, false, true);
            stock_product_image.setImage(image);

        }

    }
    
    public ObservableList<ProductData> ProductListData() {

        ObservableList<ProductData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM products";

        connect = (Connection) UserInfoDB.connectDb();

        try {
            prepare = (PreparedStatement) connect.prepareStatement(sql);
            result = (ResultSet) prepare.executeQuery();

            ProductData products;

            while (result.next()) {
                products = new ProductData(result.getInt("product_id"),result.getString("product_name"), result.getString("product_status"),
                         result.getDouble("product_price"), result.getString("product_image"),
                         result.getDate("order_date"),result.getDouble("product_amount"));

                listData.add(products);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    
    private ObservableList<ProductData> ProductList;
    
    public void ProductShowListData() {
        ProductList = ProductListData();

        stock_product_col_productid.setCellValueFactory(new PropertyValueFactory<>("productId"));
        stock_product_col_producname.setCellValueFactory(new PropertyValueFactory<>("productname"));
        stock_product_col_productstatus.setCellValueFactory(new PropertyValueFactory<>("productstatus"));
        stock_product_col_productprice.setCellValueFactory(new PropertyValueFactory<>("productprice"));

        stock_product_table.setItems(ProductList);
    }
    
    public void ProductSearch() {

        FilteredList<ProductData> filter = new FilteredList<>(ProductList, e -> true);

        stock_product_search.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(PrediateProductData -> {

                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (PrediateProductData.getProdcutId().toString().contains(searchKey)) {
                    return true;
                } else if (PrediateProductData.getName().toString().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (PrediateProductData.getStatus().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (PrediateProductData.getPrice().toString().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });

        });

        SortedList<ProductData> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(stock_product_table.comparatorProperty());

        stock_product_table.setItems(sortList);

    }
    
    
    
    public void ProductSelect() {

        ProductData products = stock_product_table.getSelectionModel().getSelectedItem();
        int num = stock_product_table.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        stock_product_id.setText(String.valueOf(products.getProdcutId()));
        stock_product_name.setText(products.getName());
        stock_product_price.setText(String.valueOf(products.getPrice()));

        Data.path = products.getImage();

        String uri = "file:" + products.getImage();

        image = new Image(uri, 161, 104, false, true);
        stock_product_image.setImage(image);

    }
    
    public void displayUsername() {
        String user = Data.username;
        username.setText(user.substring(0, 1).toUpperCase() + user.substring(1));

    }
    
    
    
    public void switchForm(ActionEvent event) {

        if (event.getSource() == profile_Btn) {
            profile_form.setVisible(true);
            stock_form.setVisible(false);
            
        } else if (event.getSource() == stock_Btn) {
            profile_form.setVisible(false);
            stock_form.setVisible(true);


            ProductShowListData();
            ProductSearch();

        }

    }
    
    private double x=0;
    private double y=0;
    
    public void logout() {

        try {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Do you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                // HIDE YOUR DASHBOARD FORM
                SignOut_Btn.getScene().getWindow().hide();

                // LINK YOUR LOGIN FORM
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
    public void initialize(URL location, ResourceBundle resources){
        displayUsername();
        
        ProductShowListData();
        
    }
    
}
