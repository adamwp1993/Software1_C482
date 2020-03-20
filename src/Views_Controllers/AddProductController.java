package Views_Controllers;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

public class AddProductController implements Initializable {

    ObservableList<Part> allPartsTempList = FXCollections.observableArrayList();
    ObservableList<Part> addedPartsTempList = FXCollections.observableArrayList();

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
    @ FXML private TableColumn<Part, Integer> addedPartsInventoryColumn;
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


    public void pressSaveButton(ActionEvent event) throws IOException {
        if (addedPartsTempList.isEmpty()) {
            partAlert();
        }
        else {
            Product newProduct = new Product(
                    Inventory.generateProductID(),
                    productNameTextField.getText(),
                    Double.parseDouble(productPriceTextField.getText()),
                    Integer.parseInt(productInvTextField.getText()),
                    Integer.parseInt(productMinTextField.getText()),
                    Integer.parseInt(productMaxTextField.getText())
            );

            Inventory.addProduct(newProduct);
            // Add the parts in the temp observable list to the Product
            addedPartsTempList.forEach(part -> {
                Inventory.addPart(part);

            });
            switchScreen(event, "mainScreen.fxml");
        }

    }


    public void pressAddButton(ActionEvent event) throws IOException {
        Part selectedPart = allPartsTable.getSelectionModel().getSelectedItem();
        int selectedPartID = selectedPart.getPartID();
        addedPartsTempList.add(selectedPart);
        allPartsTempList.removeIf(checkPart -> checkPart.getPartID() == selectedPartID);
        setAllPartsTable(allPartsTempList);
        setAddedPartsTable(addedPartsTempList);
    }


    public void pressCancelButton(ActionEvent event) throws IOException {
        if(showAlert("Cancel")) {
            switchScreen(event, "mainScreen.fxml");
        }
    }


    public void pressDeleteButton(ActionEvent event) throws IOException {
        if(showAlert("Delete")) {
            Part selectedPart = addedPartsTable.getSelectionModel().getSelectedItem();
            int selectedPartID = selectedPart.getPartID();
            allPartsTempList.add(selectedPart);
            addedPartsTempList.removeIf(checkPart -> checkPart.getPartID() == selectedPartID);
            setAddedPartsTable(addedPartsTempList);
            setAllPartsTable(allPartsTempList);
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


    public void pressSearchAddedPartsButton(ActionEvent event) {
        ObservableList<Part> searchList = FXCollections.observableArrayList();
        try {
            int inputID = Integer.parseInt(searchAddedPartsTextField.getText());
            for (Part lookupPart : addedPartsTempList) {
                if(lookupPart.getPartID() == inputID) {
                    searchList.add(lookupPart);
                }
            }
            setAddedPartsTable(searchList);

        }
        catch(NumberFormatException exception) {
            String inputName = searchAddedPartsTextField.getText();
            for (Part lookupPart : addedPartsTempList) {
                if (lookupPart.getPartName().toLowerCase().contains(inputName.toLowerCase())) {
                    searchList.add(lookupPart);

                }
            }
            setAddedPartsTable(searchList);
        }
    }


    public void setAllPartsTable(ObservableList<Part> allPartsTempList) {
        allPartsIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partID"));
        allPartsNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("partName"));
        allPartsInvColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partInventory"));
        allPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("partPrice"));
        allPartsTable.setItems(allPartsTempList);
    }

    public void setAddedPartsTable(ObservableList<Part> addedPartsTempList) {
        addedPartsIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partID"));
        addedPartsNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("partName"));
        addedPartsInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("partInventory"));
        addedPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("partPrice"));
        addedPartsTable.setItems(addedPartsTempList);

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
        // Using a temp list to be able to move between the two tables.
        allPartsTempList = Inventory.getAllParts();
        setAllPartsTable(allPartsTempList);


    }
}
