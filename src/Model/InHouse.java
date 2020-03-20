package Model;

public class InHouse extends Part {

    private int machineID;

    public InHouse(int inputID, String inputName, double inputPrice, int inputStock, int inputMin, int inputMax,
                   int inputMachineId) {

        super(inputID, inputName, inputPrice, inputStock, inputMin, inputMax);
        machineID = inputMachineId;

    }

    public void setMachineID(int inputMachineID) {
        machineID = inputMachineID;
    }

    public int getMachineID() {
        return machineID;
    }


}
