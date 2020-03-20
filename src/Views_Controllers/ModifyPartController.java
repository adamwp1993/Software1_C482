package Views_Controllers;

import Model.InHouse;
import Model.Inventory;
import Model.OutSourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyPartController implements Initializable {

    private Part modPart;
    private ToggleGroup partSourceToggleGroup = new ToggleGroup();

    @ FXML private RadioButton inHouseRadioButton;
    @ FXML private RadioButton outSourcedRadioButton;

    @ FXML private TextField partIDTextField;
    @ FXML private TextField partNameTextField;
    @ FXML private TextField partInvTextField;
    @ FXML private TextField partPriceTextField;
    @ FXML private TextField partMaxTextField;
    @ FXML private TextField partMinTextField;
    @ FXML private TextField partTypeData;

    @ FXML private Button saveButton;
    @ FXML private Button exitButton;

    @ FXML private Label partTypeLabel;



    public void setToggleGroup() {
        this.inHouseRadioButton.setToggleGroup(partSourceToggleGroup);
        this.outSourcedRadioButton.setToggleGroup(partSourceToggleGroup);
    }


    public void switchScreen(ActionEvent event, String switchPath) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(switchPath));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    public void initData(Part genericPart) {
        // Pulls in the selected Part from the main screen and initializes.
        // Check the part type, Cast it, Set it.

        modPart = genericPart;

        if(genericPart instanceof InHouse) {

            InHouse inputPart = (InHouse) genericPart;

            partIDTextField.setText(Integer.toString(inputPart.getPartID()));
            partNameTextField.setText(inputPart.getPartName());
            partInvTextField.setText(Integer.toString(inputPart.getPartInventory()));
            partPriceTextField.setText(Double.toString(inputPart.getPartPrice()));
            partMaxTextField.setText(Integer.toString(inputPart.getMaxNum()));
            partMinTextField.setText(Integer.toString(inputPart.getMinNum()));
            partTypeData.setText(Integer.toString(inputPart.getMachineID()));

            partSourceToggleGroup.selectToggle(inHouseRadioButton);
            partTypeLabel.setText("Machine ID");

        }
        else if(genericPart instanceof OutSourced) {

            OutSourced inputPart = (OutSourced) genericPart;

            partIDTextField.setText(Integer.toString(inputPart.getPartID()));
            partNameTextField.setText(inputPart.getPartName());
            partInvTextField.setText(Integer.toString(inputPart.getPartInventory()));
            partPriceTextField.setText(Double.toString(inputPart.getPartPrice()));
            partMaxTextField.setText(Integer.toString(inputPart.getMaxNum()));
            partMinTextField.setText(Integer.toString(inputPart.getMinNum()));
            partTypeData.setText(inputPart.getCompanyName());

            partSourceToggleGroup.selectToggle(outSourcedRadioButton);
            partTypeLabel.setText("Company");

        }

    }


    public void pressRadioButton(ActionEvent event) throws IOException {
        if (event.getSource() == inHouseRadioButton) {
            partTypeLabel.setText("Machine ID");
        }
        else if(event.getSource() == outSourcedRadioButton) {
            partTypeLabel.setText("Company");
        }
    }


    public void pressSaveButton(ActionEvent event) throws IOException {
        if (partSourceToggleGroup.getSelectedToggle() == inHouseRadioButton) {

            int index = Inventory.lookupPartIndex(modPart.getPartID());
            Part newPart = new InHouse(
                    modPart.getPartID(),
                    partNameTextField.getText(),
                    Double.parseDouble(partPriceTextField.getText()),
                    Integer.parseInt(partInvTextField.getText()),
                    Integer.parseInt(partMinTextField.getText()),
                    Integer.parseInt(partMaxTextField.getText()),
                    Integer.parseInt(partTypeData.getText())
            );

            Inventory.updatePart(index, newPart);
            switchScreen(event, "mainScreen.fxml");

        }

        if (partSourceToggleGroup.getSelectedToggle() == outSourcedRadioButton) {
            int index = Inventory.lookupPartIndex(modPart.getPartID());
            Part newPart = new OutSourced(
                    Inventory.generatePartID(),
                    partNameTextField.getText(),
                    Double.parseDouble(partPriceTextField.getText()),
                    Integer.parseInt(partInvTextField.getText()),
                    Integer.parseInt(partMinTextField.getText()),
                    Integer.parseInt(partMaxTextField.getText()),
                    partTypeData.getText()
            );

            Inventory.updatePart(index, newPart);
            switchScreen(event, "mainScreen.fxml");

        }
    }


    public void pressExitButton(ActionEvent event) throws IOException {
        if(showAlert("Exit")) {
            switchScreen(event, "mainScreen.fxml");
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setToggleGroup();


    }
}
