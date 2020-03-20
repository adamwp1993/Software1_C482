package Model;

import Views_Controllers.MainScreenController;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/Views_Controllers/mainScreen.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {

        // test data
        Part mapleBody = new InHouse(Inventory.generatePartID(), "Maple Guitar Body", 40.00, 600,
                1, 1200, 300);
        Part mapleNeck = new InHouse(Inventory.generatePartID(), "Maple Guitar Neck", 25.00, 400,
                1, 1200, 301);
        Part humbuckler = new OutSourced(Inventory.generatePartID(),"Pickups",40.00,250,
                1,1200, "Fender");
        Part headStock = new OutSourced(Inventory.generatePartID(), "Headstock", 10, 1100,
                1, 1200, "Joe's Woodworking");
        Part bridge = new OutSourced(Inventory.generatePartID(), "bridge", 15.00,
                700, 1, 1200, "Fender");
        Inventory.addPart(mapleBody);
        Inventory.addPart(mapleNeck);
        Inventory.addPart(humbuckler);
        Inventory.addPart(headStock);
        Inventory.addPart(bridge);

        Product customStratocaster = new Product(Inventory.generateProductID(), "Flame Painted Stratocaster", 900.00,
                125, 1, 600);
        Inventory.addProduct(customStratocaster);

        launch(args);

    }






}
