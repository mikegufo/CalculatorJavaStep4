class Calculator {

    public static String calc(String input) throws Exception {
        String[] parts = input.split("\\s+");

        if (parts.length != 3 || parts[1].length() != 1 || !"+-*/".contains(parts[1])) {
            throw new Exception("Формат математической операции не удовлетворяет заданию");
        }

        if (parts[0].contains(".") || parts[0].contains(",") || parts[2].contains(".") || parts[2].contains(",")) {
            throw new Exception("Калькулятор умеет работать только с целыми числами");
        }

        int num1, num2;
        boolean isRoman = isRoman(parts[0]) && isRoman(parts[2]);
        try {
            if (isRoman) {
                num1 = romanToArabic(parts[0]);
                num2 = romanToArabic(parts[2]);
            } else {
                num1 = Integer.parseInt(parts[0]);
                num2 = Integer.parseInt(parts[2]);
            }
        } catch (NumberFormatException e) {
            throw new Exception("Используются одновременно разные системы счисления");
        }

        if (isRoman) {
            if ((num1 < 1 || num1 > 10) || (num2 < 1 || num2 > 10))
                throw new Exception("Римские числа должны быть от 1 до 10 включительно");
        }

        if (!isRoman) {
            if ((num1 < -10 || num1 > 10) || (num2 < -10 || num2 > 10))
                throw new Exception("Арабские числа должны быть в диапазоне от -10 до 10 включительно");
        }

        String operator = parts[1];
        int result = switch (operator) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            case "/" -> {
                if (num2 == 0)
                    throw new Exception("Деление на ноль");
                yield num1 / num2;
            }
            default -> throw new Exception("Недопустимая арифметическая операция");
        };

        if (isRoman && result < 1) {
            throw new Exception("В римской системе нет отрицательных чисел");
        }

        return isRoman ? arabicToRoman(Math.abs(result)) : Integer.toString(result);
    }

    public static boolean isRoman(String input) {
        return input.matches("^[IVXLCDM]+$");
    }

    public static int romanToArabic(String roman) {
        int result = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            int curValue = romanCharToInt(roman.charAt(i));

            if (curValue < prevValue)
                result -= curValue;
            else
                result += curValue;

            prevValue = curValue;
        }

        return result;
    }

    public static String arabicToRoman(int num) {

        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder roman = new StringBuilder();

        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                num -= values[i];
                roman.append(symbols[i]);
            }
        }

        return roman.toString();
    }

    public static int romanCharToInt(char romanChar) {
        return switch (romanChar) {
            case 'I' -> 1;
            case 'V' -> 5;
            case 'X' -> 10;
            case 'L' -> 50;
            case 'C' -> 100;
            case 'D' -> 500;
            case 'M' -> 1000;
            default -> throw new IllegalArgumentException("Недопустимый символ: " + romanChar);
        };
    }
}
