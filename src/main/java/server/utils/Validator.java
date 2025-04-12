package server.utils;

import server.utils.exception.badrequest.ConstraintViolationException;

public class Validator {

    public static void min(Integer input, Integer min) throws ConstraintViolationException {
        if (input < min)
            throw new ConstraintViolationException("Input must be at least " + min);
    }

    public static void max(Integer input, Integer max) throws ConstraintViolationException {
        if (input > max)
            throw new ConstraintViolationException("Input must be at most " + max);
    }

    public static void notNull(Object input) throws ConstraintViolationException {
        if (input == null)
            throw new ConstraintViolationException("Input cannot be null");
    }

    public static void notEmpty(String input) throws ConstraintViolationException {
        if (input.isEmpty())
            throw new ConstraintViolationException("Input cannot be empty");
    }

    public static void between(Integer min, Integer max, Integer input) throws ConstraintViolationException {
        if (input >= min && input <= max) {
            throw new ConstraintViolationException("The input is now between %s and %s".formatted(min, max));
        }
    }

    public static void length (String input, int min, int max) throws ConstraintViolationException {
        if (input == null) {
            throw new ConstraintViolationException("The input is null");
        }
        if (input.length() >= max - min) {
            throw new ConstraintViolationException("The input is too long");
        }
    }

    public static void size(Object[] arr, Integer min, Integer max) throws ConstraintViolationException {
        if (arr.length >= max - min)
            throw new ConstraintViolationException("The input (array) is too long");
    }

}
