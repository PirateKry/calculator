package model;

public final class Height {
    private final double meters;

    private Height(double meters) {
        this.meters = meters;
    }

    public static Height fromCentimeters(double cm) {
        if (cm < 40.0 || cm > 280.0) {
            throw new IllegalArgumentException(
                    "Ошибка: Рост в см должен быть в диапазоне от 40 до 280");
        }
        return new Height(cm / 100.0);
    }

    public static Height fromMeters(double m) {
        if (m < 0.4 || m > 2.8) {
            throw new IllegalArgumentException(
                    "Ошибка: Рост в метрах должен быть в диапазоне от 0.4 до 2.8");
        }
        return new Height(m);
    }

    public double getInMeters() {
        return meters;
    }

    @Override
    public String toString() {
        return String.format("%.0f см", meters * 100);
    }
}
