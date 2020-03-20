package Model;

public abstract class Part {

    private int partID;
    private String partName;
    private double partPrice;
    private int partInventory;
    private int minNum;
    private int maxNum;


    public Part(int inputID, String inputName, double inputPrice, int inputStock, int inputMin, int inputMax) {
        //Constructor
        partID = inputID;
        partName = inputName;
        partPrice = inputPrice;
        partInventory = inputStock;
        minNum = inputMin;
        maxNum = inputMax;

    }

    //setters
    public void setPartID(int inputID) {
        partID = inputID;
    }

    public void setPartName(String inputName) {
        partName = inputName;
    }

    public void setPartPrice(double inputPrice) {
        partPrice = inputPrice;
    }

    public void setPartInventory(int inputStock) {
        partInventory = inputStock;
    }

    public void setMinNum(int inputMin) {
        minNum = inputMin;
    }

    public void setMaxNum(int inputMax) {
        maxNum = inputMax;
    }

    //getters
    public int getPartID() {
        return partID;
    }

    public String getPartName() {
        return partName;
    }

    public double getPartPrice() {
        return partPrice;
    }

    public int getPartInventory() {
        return partInventory;
    }

    public int getMinNum() {
        return minNum;
    }

    public int getMaxNum() {
        return maxNum;
    }



}
