/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package greengrocery;

import java.awt.Button;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ProductController implements Initializable {
    
    
    @FXML
    private Button pro_addBtn;

    @FXML
    private Spinner<?> pro_amountSpin;

    @FXML
    private ImageView pro_image;

    @FXML
    private Label pro_name;

    @FXML
    private Label pro_price;

    @FXML
    private AnchorPane product_form;
    
    private ProductData prodData;
    private Image image;
    
    public void setData(ProductData prodData) {
        
        this.prodData = prodData;
        pro_name.setText(prodData.getName());
        pro_price.setText(String.valueOf(prodData.getPrice()));
        String path = "File:" + prodData.getImage();
        image = new Image(path, 231, 106, false, true);
        pro_image.setImage(image);

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
}
