package countries;

public abstract class Country {

    private int countCampLevel = 0;
    private String opening;

    private String era;
    private String commander;

    void generateText(){

    }

    public void setOpening(String opening) {
        this.opening = opening;
    }

    public String getOpening() {
        return opening;
    }
}
