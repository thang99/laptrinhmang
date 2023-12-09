/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DoAn;

/**
 *
 * @author HoangPhi
 */
import java.io.Serializable;

public class Item implements Serializable {
private String label;
    private double confidence;
    private double xMin;
    private double yMin;
    private double xMax;
    private double yMax;

    public Item(String label, double confidence, double xMin, double yMin, double xMax, double yMax) {
        this.label = label;
        this.confidence = confidence;
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    public String getLabel() {
        return label;
    }

    public double getConfidence() {
        return confidence;
    }

    public double getXMin() {
        return xMin;
    }

    public double getYMin() {
        return yMin;
    }

    public double getXMax() {
        return xMax;
    }

    public double getYMax() {
        return yMax;
    }
}
