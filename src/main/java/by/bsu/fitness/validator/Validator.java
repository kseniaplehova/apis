package by.bsu.fitness.validator;

public interface Validator<T> {
    boolean validate(T value);
}
