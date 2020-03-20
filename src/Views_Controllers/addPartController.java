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

public class addPartController implements Initializable {

    private ToggleGroup partSourceToggleGroup = new ToggleGroup();

    @ FXML private RadioButton inHouseRadioButton;
    @ FXML private RadioButton outSourcedRadioButton;
    @ FXML private TextField partNameTextBox;
    @ FXML private TextField invTextBox;
    @ FXML private TextField priceTextBox;
    @ FXML private TextField maxTextBox;
    @ FXML private TextField minTextBox;
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


    public void pressRadioButton(ActionEvent event) throws IOException {
        if (event.getSource() == outSourcedRadioButton) {
            //switchScreen(event, "addOutSourcedPart.fxml");
            partTypeLabel.setText("Company");
            partTypeData.setPromptText("Company Name");

        }
        if (event.getSource() == inHouseRadioButton) {
            partTypeLabel.setText("Machine ID");
            partTypeData.setPromptText("Machine ID");
        }
    }


    public void pressSaveButton(ActionEvent event) throws IOException {
        if (partSourceToggleGroup.getSelectedToggle() == inHouseRadioButton) {
            Part newPart = new InHouse(
                    Inventory.generatePartID(),
                    partNameTextBox.getText(),
                    Double.parseDouble(priceTextBox.getText()),
                    Integer.parseInt(invTextBox.getText()),
                    Integer.parseInt(minTextBox.getText()),
                    Integer.parseInt(maxTextBox.getText()),
                    Integer.parseInt(partTypeData.getText())
            );

            Inventory.addPart(newPart);
            switchScreen(event, "mainScreen.fxml");
        }
        if (partSourceToggleGroup.getSelectedToggle() == outSourcedRadioButton) {
            Part newPart = new OutSourced(
                    Inventory.generatePartID(),
                    partNameTextBox.getText(),
                    Double.parseDouble(priceTextBox.getText()),
                    Integer.parseInt(invTextBox.getText()),
                    Integer.parseInt(minTextBox.getText()),
                    Integer.parseInt(maxTextBox.getText()),
                    partTypeData.getText()
            );

            Inventory.addPart(newPart);
            switchScreen(event, "mainScreen.fxml");
        }
    }


    public void pressExitButton(ActionEvent event) throws IOException{
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
