package model;

public class BMIResult {
    private final double bmi;
    private final BMICategory category;

    public BMIResult(double bmi, BMICategory category) {
        this.bmi = bmi;
        this.category = category;
    }

    public double getBmi() { return bmi; }
    public BMICategory getCategory() { return category; }

    @Override
    public String toString() {
        return String.format("ИМТ = %.2f (%s)", bmi, category.getName());
    }
}