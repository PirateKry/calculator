package org.example;
import model.*;
import java.util.Scanner;

//TIP Для <b>запуска</b> кода нажмите <shortcut actionId="Run"/> или
// щелкните значок <icon src="AllIcons.Actions.Execute"/> в боковой области.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BMICalculator calculator = new BMICalculator();

        System.out.println("=== Калькулятор ИМТ ===");

        try {
            System.out.print("Введите рост (см): ");
            double heightCm = scanner.nextDouble();
            Height height = Height.fromCentimeters(heightCm);

            System.out.print("Введите вес (кг): ");
            double weightKg = scanner.nextDouble();
            Weight weight = Weight.fromKilograms(weightKg);

            BMIResult result = calculator.calculate(height, weight);

            System.out.println("\n=== Результат ===");
            System.out.printf("Рост: %.0f см%n", heightCm);
            System.out.printf("Вес: %.1f кг%n", weightKg);
            System.out.printf("ИМТ: %.2f%n", result.getBmi());
            System.out.println("Категория: " + result.getCategory().getName());

        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ошибка ввода: введите корректное число");
        }

        scanner.close();
    }
}