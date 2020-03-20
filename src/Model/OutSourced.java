package Model;

public class OutSourced extends Part {

    private String companyName;

    public OutSourced(int inputID, String inputName, double inputPrice, int inputStock, int inputMin, int inputMax,
                      String inputCompanyName) {

        super(inputID, inputName, inputPrice, inputStock, inputMin, inputMax);
        companyName = inputCompanyName;

    }

    public void setCompanyName(String inputCompanyName) {
        companyName = inputCompanyName;
    }

    public String getCompanyName() {
        return companyName;
    }


}
