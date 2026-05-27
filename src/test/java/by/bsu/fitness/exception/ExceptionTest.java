package by.bsu.fitness.exception;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ExceptionTest {

    @Test
    public void testFitnessExceptionMessage() throws FitnessException {
        FitnessException e = new FitnessException("test message");
        Assert.assertEquals(e.getMessage(), "test message");
    }

    @Test
    public void testFitnessExceptionCause() throws FitnessException {
        RuntimeException cause = new RuntimeException("cause");
        FitnessException e = new FitnessException("msg", cause);
        Assert.assertEquals(e.getCause(), cause);
    }

    @Test
    public void testFitnessExceptionIsException() {
        FitnessException e = new FitnessException("msg");
        Assert.assertTrue(e instanceof Exception);
    }

    @Test
    public void testRepositoryExceptionMessage() throws RepositoryException {
        RepositoryException e = new RepositoryException("repo error");
        Assert.assertEquals(e.getMessage(), "repo error");
    }

    @Test
    public void testRepositoryExceptionIsFitnessException() {
        RepositoryException e = new RepositoryException("msg");
        Assert.assertTrue(e instanceof FitnessException);
    }

    @Test
    public void testRepositoryExceptionWithCause() {
        RuntimeException cause = new RuntimeException("root");
        RepositoryException e = new RepositoryException("msg", cause);
        Assert.assertEquals(e.getCause(), cause);
    }

    @Test
    public void testServiceExceptionMessage() throws ServiceException {
        ServiceException e = new ServiceException("service error");
        Assert.assertEquals(e.getMessage(), "service error");
    }

    @Test
    public void testServiceExceptionIsFitnessException() {
        ServiceException e = new ServiceException("msg");
        Assert.assertTrue(e instanceof FitnessException);
    }

    @Test
    public void testServiceExceptionWithCause() {
        RuntimeException cause = new RuntimeException("root");
        ServiceException e = new ServiceException("msg", cause);
        Assert.assertEquals(e.getCause(), cause);
    }

    @Test
    public void testValidationExceptionMessage() throws ValidationException {
        ValidationException e = new ValidationException("validation error");
        Assert.assertEquals(e.getMessage(), "validation error");
    }

    @Test
    public void testValidationExceptionIsFitnessException() {
        ValidationException e = new ValidationException("msg");
        Assert.assertTrue(e instanceof FitnessException);
    }

    @Test
    public void testValidationExceptionWithCause() {
        RuntimeException cause = new RuntimeException("root");
        ValidationException e = new ValidationException("msg", cause);
        Assert.assertEquals(e.getCause(), cause);
    }
}
