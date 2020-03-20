package Views_Controllers;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class ModifyProductController implements Initializable {

    ObservableList<Part> allPartsTempList;


    Product modProduct;

    @ FXML private TextField productIDTextField;
    @ FXML private TextField productNameTextField;
    @ FXML private TextField productInvTextField;
    @ FXML private TextField productPriceTextField;
    @ FXML private TextField productMaxTextField;
    @ FXML private TextField productMinTextField;

    @ FXML private TableView<Part> allPartsTable;
    @ FXML private TableColumn<Part, Integer> allPartsIDColumn;
    @ FXML private TableColumn<Part, String> allPartsNameColumn;
    @ FXML private TableColumn<Part, Integer> allPartsInvColumn;
    @ FXML private TableColumn<Part, Double> allPartsPriceColumn;

    @ FXML private TableView<Part> addedPartsTable;
    @ FXML private TableColumn<Part, Integer> addedPartsIDColumn;
    @ FXML private TableColumn<Part, String> addedPartsNameColumn;
    @ FXML private TableColumn<Part, Integer> addedPartsInvColumn;
    @ FXML private TableColumn<Part, Double> addedPartsPriceColumn;

    @ FXML private Button addButton;
    @ FXML private Button saveButton;
    @ FXML private Button cancelButton;
    @ FXML private Button deleteButton;

    @ FXML private Button searchAllPartsButton;
    @ FXML private Button searchAddedPartsButton;

    @ FXML private TextField searchAllPartsTextField;
    @ FXML private TextField searchAddedPartsTextField;


    public void switchScreen(ActionEvent event, String switchPath) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(switchPath));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    public void initData(Product selectedProduct) {
        modProduct = selectedProduct;
        productIDTextField.setText(Integer.toString(modProduct.getProductID()));
        productNameTextField.setText(modProduct.getProductName());
        productInvTextField.setText(Integer.toString(modProduct.getProductStock()));
        productPriceTextField.setText(Double.toString(modProduct.getProductPrice()));
        productMaxTextField.setText(Integer.toString(modProduct.getMaxNum()));
        productMinTextField.setText(Integer.toString(modProduct.getMinNum()));


        // Parts cannot exist in both tables
        allPartsTempList =  FXCollections.observableArrayList(Inventory.getAllParts());
        for(Part checkPart : modProduct.getAssociatedParts()) {
            allPartsTempList.remove(checkPart);
        }


        setAddedPartsTable(modProduct.getAssociatedParts());
        setAllPartsTable(allPartsTempList);

    }


    public void setAddedPartsTable(ObservableList<Part> tempList) {
        // sets the added Parts table, takes an Observable List of Parts as input.
        addedPartsIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partID"));
        addedPartsNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("partName"));
        addedPartsInvColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partInventory"));
        addedPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("partPrice"));
        addedPartsTable.setItems(tempList);

    }


    public void setAllPartsTable(ObservableList<Part> tempList) {

        allPartsIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partID"));
        allPartsNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("partName"));
        allPartsInvColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partInventory"));
        allPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("partPrice"));
        allPartsTable.setItems(tempList);
    }


    public void pressAddButton(ActionEvent event) throws IOException {
        Part selectedPart = allPartsTable.getSelectionModel().getSelectedItem();
        modProduct.addAssociatedParts(selectedPart);
        allPartsTempList.remove(selectedPart);
        setAllPartsTable(allPartsTempList);
        setAddedPartsTable(modProduct.getAssociatedParts());
    }


    public void pressDeleteButton(ActionEvent event) throws IOException {
        if(showAlert("Delete")) {
            Part selectedPart = addedPartsTable.getSelectionModel().getSelectedItem();
            modProduct.deleteAssociatedPart(selectedPart);
            allPartsTempList.add(selectedPart);
            setAllPartsTable(allPartsTempList);
            setAddedPartsTable(modProduct.getAssociatedParts());
        }
    }


    public void pressCancelButton(ActionEvent event) throws IOException {
        if(showAlert("Cancel")) {
            switchScreen(event, "mainScreen.fxml");
        }
    }


    public void pressSaveButton(ActionEvent event) throws IOException {
        if(modProduct.getAssociatedParts().isEmpty()) {
            partAlert();

        }
        else {
            modProduct.setProductName(productNameTextField.getText());
            modProduct.setProductStock(Integer.parseInt(productInvTextField.getText()));
            modProduct.setProductPrice(Double.parseDouble(productPriceTextField.getText()));
            modProduct.setMaxNum(Integer.parseInt(productMaxTextField.getText()));
            modProduct.setMinNum(Integer.parseInt(productMinTextField.getText()));

            Inventory.updateProduct(Inventory.lookupProductIndex(modProduct.getProductID()), modProduct);
            switchScreen(event, "mainScreen.fxml");
        }

    }


    public void pressSearchAllPartsButton(ActionEvent event) throws IOException {
        ObservableList<Part> searchList = FXCollections.observableArrayList();
        try {
            Part searchedPart;
            searchedPart = Inventory.lookupPart(Integer.parseInt(searchAllPartsTextField.getText()));
            searchList.add(searchedPart);
            setAllPartsTable(searchList);

        }
        catch (NumberFormatException exception) {
            searchList = Inventory.lookupPart(searchAllPartsTextField.getText());
            setAllPartsTable(searchList);
        }
    }


    public void pressSearchAddedPartsButton(ActionEvent event) throws IOException {
        ObservableList<Part> searchList = FXCollections.observableArrayList();
        try {
            int inputID = Integer.parseInt(searchAddedPartsTextField.getText());
            for (Part lookupPart : modProduct.getAssociatedParts()) {
                if(lookupPart.getPartID() == inputID) {
                    searchList.add(lookupPart);
                }
            }
            setAddedPartsTable(searchList);

        }
        catch(NumberFormatException exception) {
            String inputName = searchAddedPartsTextField.getText();
            for (Part lookupPart : modProduct.getAssociatedParts()) {
                if (lookupPart.getPartName().toLowerCase().contains(inputName.toLowerCase())) {
                    searchList.add(lookupPart);

                }
            }
            setAddedPartsTable(searchList);
        }
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


    private void partAlert() {
        boolean confirmFlag = false;
        ButtonType okayButton = new ButtonType("Okay", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert deleteAlert = new Alert(Alert.AlertType.WARNING, "Products must have one Part.",
                okayButton);
        deleteAlert.setTitle("Part Alert");
        Optional<ButtonType> result = deleteAlert.showAndWait();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
