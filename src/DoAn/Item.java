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
    String label;
    Double confidence;
    Double x_min;
    Double x_max;
    Double y_min;
    Double y_max;

    public Item (String label, Double confidence, Double x_min, Double x_max, Double y_min, Double y_max) {
        this.label = label;
        this.confidence = confidence;
        this.x_min = x_min;
        this.x_max = x_max;
        this.y_min = y_min;
        this.y_max = y_max;
    }
}
