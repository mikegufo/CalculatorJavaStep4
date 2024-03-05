import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (scanner) {
            System.out.println("Введите арифметическое выражение (Формат a + b) :");
            String input = scanner.nextLine();
            String result = Calculator.calc(input);
            System.out.println("Результат: " + result);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка ввода чисел: " + e.getMessage());
        } catch (IllegalArgumentException | ArithmeticException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
