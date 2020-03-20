package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int partsIDCounter = 1;
    private static int productIDCounter = 1;




    public static void addPart(Part inputPart) {
        allParts.add(inputPart);
    }

    public static void addProduct(Product inputProduct) {
        allProducts.add(inputProduct);
    }

    public static Part lookupPart(int inputID) {
        for (Part lookupPart : allParts) {
            if(lookupPart.getPartID() == inputID) {
                return lookupPart;
            }
        }
        return null;
    }

    public static ObservableList<Part> lookupPart(String inputName) {
        ObservableList<Part> tempList = FXCollections.observableArrayList();
        for (Part lookupPart : allParts) {
            if(lookupPart.getPartName().toLowerCase().contains(inputName.toLowerCase())) {
                // not Caps sensitive
                tempList.add(lookupPart);

            }
        }
        return tempList;
    }

    public static ObservableList<Product> lookupProduct(String inputName) {
        ObservableList<Product> tempList = FXCollections.observableArrayList();
        for(Product lookupProduct : allProducts) {
            if(lookupProduct.getProductName().toLowerCase().contains(inputName.toLowerCase())) {
                tempList.add(lookupProduct);
            }
        }
        return tempList;
    }

    public static boolean deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
        return true;
    }

    public static boolean deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
        return true;
    }

    public static Product lookupProduct(int inputID) {
        for (Product lookupProduct : allProducts) {
            if (lookupProduct.getProductID() == inputID) {
                return lookupProduct;
            }
        }
        return null;
    }

    public static int lookupProductIndex(int inputID) {
        for (Product lookupProduct : allProducts) {
            if(lookupProduct.getProductID() == inputID) {
                return allProducts.indexOf(lookupProduct);
            }
        }
        return -1;
    }


    public static int lookupPartIndex(int inputID) {
        for( Part lookupPart : allParts ) {
            if(lookupPart.getPartID() == inputID) {
                return allParts.indexOf(lookupPart);
            }
        }
        return -1;
    }



    public static void updatePart(int index, Part updatedPart) {
        allParts.set(index, updatedPart);
    }

    public static void updateProduct(int index, Product updatedProduct) {
        allProducts.set(index, updatedProduct);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public static int generatePartID() {
        partsIDCounter += 1;
        return partsIDCounter;
    }

    public static int generateProductID() {
        productIDCounter += 1;
        return productIDCounter;
    }





}
