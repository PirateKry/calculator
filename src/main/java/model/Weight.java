package model;

public final class Weight {
    private final double kilograms;

    private Weight(double kilograms) {
        this.kilograms = kilograms;
    }

    public static Weight fromKilograms(double kg) {
        if (kg < 2.0 || kg > 500.0) {
            throw new IllegalArgumentException(
                    "Ошибка: Вес должен быть в диапазоне от 2 до 500 кг");
        }
        return new Weight(kg);
    }

    public double getInKilograms() {
        return kilograms;
    }

    @Override
    public String toString() {
        return String.format("%.1f кг", kilograms);
    }
}
