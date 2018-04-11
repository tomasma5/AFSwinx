package cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.devicestatus.model.partial;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class Resolution {

    private int widthInPixels;
    private int heightInPixels;
    private double inches;

    public Resolution() {
    }

    public Resolution(int widthInPixels, int heightInPixels, double inches) {
        this.widthInPixels = widthInPixels;
        this.heightInPixels = heightInPixels;
        this.inches = inches;
    }

    public int getWidthInPixels() {
        return widthInPixels;
    }

    public void setWidthInPixels(int widthInPixels) {
        this.widthInPixels = widthInPixels;
    }

    public int getHeightInPixels() {
        return heightInPixels;
    }

    public void setHeightInPixels(int heightInPixels) {
        this.heightInPixels = heightInPixels;
    }

    public double getInches() {
        return inches;
    }

    public void setInches(double inches) {
        this.inches = inches;
    }
}
