package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class BMICategory {
    private final String name;
    private final double max;
    private final String colorCode;

    @JsonCreator
    public BMICategory(
            @JsonProperty("name") String name,
            @JsonProperty("max")         double max,
            @JsonProperty("color")       String colorCode) {
        this.name = Objects.requireNonNull(name, "name");
        this.colorCode   = Objects.requireNonNull(colorCode, "colorCode");
        this.max         = max;
    }

    public String getName() { return name; }
    public double getMax()         { return max; }
    public String getColorCode()   { return colorCode; }


    //SEVERE_THINNESS("Выраженный дефицит массы", "#3498db"),
    //UNDERWEIGHT("Недостаточная масса тела", "#5dade2"),
    //NORMAL("Норма", "#2ecc71"),
    //OVERWEIGHT("Избыточная масса (предожирение)", "#f39c12"),
    //OBESITY_I("Ожирение I степени", "#e67e22"),
    //OBESITY_II("Ожирение II степени", "#e74c3c"),
    //OBESITY_III("Ожирение III степени", "#c0392b");

    //private final String description;
    //private final String colorCode;

    //BMICategory(String description, String colorCode) {
        //this.description = description;
        //this.colorCode = colorCode;
    //}

    //public String getDescription() { return description; }
    //public String getColorCode() { return colorCode; }
}