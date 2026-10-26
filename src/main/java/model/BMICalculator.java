package model;

//import java.util.Map;

public class BMICalculator {
    //private final Map<BMICategory, double[]> ranges;

    public BMICalculator() {
        //this.ranges = BMIRanges.getDefaultRanges();
    }

    public double calculateBMI(Height height, Weight weight) {
        double heightM = height.getInMeters();
        return weight.getInKilograms() / (heightM * heightM);
    }

    public double calculateBrocasIndex(Height height) {
        double heightM = height.getInMeters();
        if(heightM < 100)
            throw new IllegalArgumentException("Ошибка: Рост должен быть больше 100 см");
        return heightM - 100;
    }

    public double calculateBreitmanIndex(Height height) {
        double heightM = height.getInMeters();
        if(heightM < 72)
            throw new IllegalArgumentException("Ошибка: Рост должен быть больше 72 см");
        return heightM * 0.7 -50;
    }

    public double calculateNoordenaIndex(Height height) {
        double heightM = height.getInMeters();
        if(heightM < 0)
            throw new IllegalArgumentException("Ошибка: Рост положительным");
        return heightM * 420 / 1000;
    }

    public double calculateTatonyIndex(Height height) {
        double heightM = height.getInMeters();
        if(heightM < 100)
            throw new IllegalArgumentException("Ошибка: Рост должен быть больше 100 см");
        return heightM * 420 / 1000;
    }

    public double calculatePigneIndex(Height height, Weight weight, double circle) {
        double heightM = height.getInMeters();
        double weightKG = weight.getInKilograms();
        if(heightM < 100 || heightM > 200)
            throw new IllegalArgumentException("Ошибка: Рост должен быть больше 100 см и меньше 200 см");
        if(weightKG < 30 || weightKG > 150)
            throw new IllegalArgumentException("Ошибка: Вес должен быть больше 30 кг и меньше 150 кг");
        return heightM - (heightM + circle);
    }

    //public BMICategory determineCategory(double bmi) {
        //for (Map.Entry<BMICategory, double[]> entry : ranges.entrySet()) {
            //double[] range = entry.getValue();
            //if (bmi >= range[0] && bmi < range[1]) {
                //return entry.getKey();
            //}
        //}
        //return BMICategory.OBESITY_III; // По умолчанию
    //}

    public BMIResult calculate(Height height, Weight weight) {
        double bmi = calculateBMI(height, weight);
        BMICategory category = BMIRanges.determineCategory(bmi);
        return new BMIResult(bmi, category);
    }
}