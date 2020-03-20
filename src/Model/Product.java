package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productID;
    private String productName;
    private double productPrice;
    private int productStock;
    private int minNum;
    private int maxNum;

    public Product(int inputID, String inputName, double inputPrice, int inputStock, int inputMin, int inputMax) {
        productID = inputID;
        productName = inputName;
        productPrice = inputPrice;
        productStock = inputStock;
        minNum = inputMin;
        maxNum = inputMax;

    }

    public void setProductID(int inputID) {
        productID = inputID;
    }

    public void setProductName(String inputName) {
        productName = inputName;
    }

    public void setProductPrice(double inputPrice) {
        productPrice = inputPrice;
    }

    public void setProductStock(int inputStock) {
        productStock = inputStock;
    }

    public void setMinNum(int inputMin) {
        minNum = inputMin;
    }

    public void setMaxNum(int inputMax) {
        maxNum = inputMax;
    }


    public int getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getProductStock() {
        return productStock;
    }

    public int getMinNum() {
        return minNum;
    }

    public int getMaxNum() {
        return maxNum;
    }


    public void addAssociatedParts(Part inputPart) {
        associatedParts.add(inputPart);
    }

    public boolean deleteAssociatedPart(Part inputPart) {
        associatedParts.remove(inputPart);
        return true;
    }



     public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
     }



}
