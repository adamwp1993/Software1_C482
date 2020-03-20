package Views_Controllers;

import Model.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;



public class MainScreenController implements Initializable {




    @ FXML private TableColumn<Part, Double>partPriceColumn;
    @ FXML private TableColumn<Part, Integer>partInventoryColumn;
    @ FXML private TableColumn<Part, String> partNameColumn;
    @ FXML private TableColumn<Part, Integer> partIDColumn;
    @ FXML private TableView<Part> allPartsTable;


    @ FXML private TableColumn<Product, String>productNameColumn;
    @ FXML private TableColumn<Product, Integer>productIDColumn;
    @ FXML private TableColumn<Product, Integer>productInventoryColumn;
    @ FXML private TableColumn<Product, Double>productPriceColumn;
    @ FXML private TableView<Product> allProductTable;

    @ FXML private Button addPartsButton;
    @ FXML private Button modifyPartsButton;
    @ FXML private Button addProductsButton;
    @ FXML private Button modifyProductsButton;
    @ FXML private Button deletePartsButton;
    @ FXML private Button deleteProductsButton;
    @ FXML private Button exitButton;
    @ FXML private Button partSearchButton;
    @ FXML private Button productSearchButton;

    @ FXML private TextField partSearchTextField;
    @ FXML private TextField productSearchTextField;



    public void switchScreen(ActionEvent event, String switchPath) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(switchPath));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    public void pressPartSearchButton(ActionEvent event) throws IOException {
        ObservableList<Part> searchList = FXCollections.observableArrayList();
        try {
            Part searchedPart;
            searchedPart = Inventory.lookupPart(Integer.parseInt(partSearchTextField.getText()));
            searchList.add(searchedPart);
            setAllPartsTable(searchList);

        }
        catch (NumberFormatException exception) {
            searchList = Inventory.lookupPart(partSearchTextField.getText());
            setAllPartsTable(searchList);
        }
    }


    public void pressPartModifyButton(ActionEvent event) throws IOException {
        Part selectedPart = allPartsTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("modifyPart.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        // get controller and call initData
        ModifyPartController controller = loader.getController();
        controller.initData(selectedPart);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);

    }


    public void pressPartAddButton(ActionEvent event) throws IOException {
        switchScreen(event, "addPart.fxml");
    }


    public void pressPartDeleteButton(ActionEvent event) {
        if(showAlert("Delete")) {
            Part selectedPart = allPartsTable.getSelectionModel().getSelectedItem();
            Inventory.deletePart(selectedPart);
            setAllPartsTable(Inventory.getAllParts());
        }
    }


    public void pressProductSearchButton(ActionEvent event) throws IOException {
        ObservableList<Product> searchList = FXCollections.observableArrayList();
        if(productSearchTextField.getText() == null) {
            setAllProductTable(Inventory.getAllProducts());
        }
        else {
            try {
                Product searchedProduct;
                searchedProduct = Inventory.lookupProduct(Integer.parseInt(productSearchTextField.getText()));
                searchList.add(searchedProduct);
                setAllProductTable(searchList);

            }
            catch (NumberFormatException exception) {
                searchList = Inventory.lookupProduct(productSearchTextField.getText());
                setAllProductTable(searchList);
            }
        }
    }


    public void pressProductModifyButton(ActionEvent event) throws IOException {
        Product selectedProduct = allProductTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("modifyProduct.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        ModifyProductController controller = loader.getController();
        controller.initData(selectedProduct);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
    }


    public void pressProductAddButton(ActionEvent event) throws IOException {
        switchScreen(event, "addProduct.fxml");
    }


    public void pressProductDeleteButton(ActionEvent event) throws IOException {
        if(showAlert("Delete")) {
            Product selectedProduct = allProductTable.getSelectionModel().getSelectedItem();
            Inventory.deleteProduct(selectedProduct);
            setAllProductTable(Inventory.getAllProducts());
        }
    }


    public void pressExitButton(ActionEvent event) throws IOException {
        if(showAlert("Quit Program")) {
            Platform.exit();
            System.exit(0);
        }
    }


    public void setAllPartsTable(ObservableList<Part> inputPartList) {
        partIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("partName"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partInventory"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("partPrice"));
        allPartsTable.setItems(inputPartList);
    }


    public void setAllProductTable(ObservableList<Product> inputProductList) {
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
        productIDColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productID"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productStock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("productPrice"));
        allProductTable.setItems(inputProductList);
    }

    private boolean showAlert(String action) {
        boolean confirmFlag = false;
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert deleteAlert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to " + action + "?",
                yesButton, noButton);
        deleteAlert.setTitle(action);
        Optional<ButtonType> result = deleteAlert.showAndWait();

        if (result.orElse(noButton) == yesButton) {
            confirmFlag = true;
        }
        return confirmFlag;

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setAllPartsTable(Inventory.getAllParts());
        setAllProductTable(Inventory.getAllProducts());

    }
}
