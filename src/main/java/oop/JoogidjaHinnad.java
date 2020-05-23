package oop;

public class JoogidjaHinnad {

    private String kohviJook;
    private double hind;
    private int järjekorranumber;

    JoogidjaHinnad(String kohviJook, double hind, int järjekorranumber) {
        this.kohviJook = kohviJook;
        this.hind = hind;
        this.järjekorranumber = järjekorranumber;
    }

    public String getKohviJook() {
        return kohviJook;
    }

    public double getHind() {
        return hind;
    }

    public int getJärjekorranumber() {
        return järjekorranumber;
    }
}
