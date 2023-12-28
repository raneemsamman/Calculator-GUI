/**
 * Calculations.java
 * @description Handles calculation operations
 * @author R Samman
 * @version 2.0 04-04-2022
 */

/**
 * Calculations class represents a the operations handling for a calculator.
 */
    public class Calculations {

        private String currentOperation;
        private Integer storedValue = 0;
        private boolean multipleEquals = false;
/**
 * Constructor.
 */
    public Calculations() {}

/**
 * Setting operation handling.
 */
    public void handleOperation(String number, String op) {
        storedValue = Integer.parseInt(number);
        currentOperation = op;
        multipleEquals = false;
    }
/**
 * Sets the calculations algorihtim/logic.
 */
    public String performCalculation(String secondNumber) {
        int secondNum = Integer.parseInt(secondNumber);
        int remainder = 0;
        int result;

        switch (currentOperation) {
            case "+":
                result = storedValue + secondNum;
                break;
            case "-":
                result = storedValue - secondNum;
                break;
            case "\u00D7":
                result = storedValue * secondNum;
                break;
            case "\u00F7":
                remainder = storedValue % secondNum;
                result = storedValue / secondNum;
                break;
            default:
                return "";
        }

        if (!multipleEquals) {
            storedValue = secondNum;
        }
        multipleEquals = true;

        return (remainder == 0) ? Integer.toString(result) : Integer.toString(result) + " R" + remainder;
    }
/**
 * Gets the stored value in memory.
 */
    public Integer getStoredValue() {
        return storedValue;
    }
/**
 * Main method.
 */
    public static void main(String[] args) {
        Calculations testCalculator = new Calculations();
        testCalculator.handleOperation("23", "*");
        System.out.println(testCalculator.performCalculation("4"));
    }
}
