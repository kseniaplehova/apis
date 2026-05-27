package by.bsu.fitness.validator;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataValidatorTest {

    private DataValidator validator;

    @BeforeMethod
    public void setUp() {
        validator = new DataValidator();
    }

    @DataProvider(name = "validLines")
    public Object[][] validLines() {
        return new Object[][]{
            {"1,Иванов,GROUP,2026-06-01T10:00,60,PLANNED"},
            {"2,Петров,PERSONAL,2026-07-01T11:00,45,PLANNED"},
            {"1,a,b,c,d,e"}
        };
    }

    @DataProvider(name = "invalidLines")
    public Object[][] invalidLines() {
        return new Object[][]{
            {null},
            {""},
            {"1,2,3"},
            {"a,b,c,d"}
        };
    }

    @Test(dataProvider = "validLines")
    public void testValidLineReturnsTrue(String line) {
        Assert.assertTrue(validator.validate(line));
    }

    @Test(dataProvider = "invalidLines")
    public void testInvalidLineReturnsFalse(String line) {
        Assert.assertFalse(validator.validate(line));
    }

    @Test
    public void testValidatorImplementsValidatorInterface() {
        Assert.assertTrue(validator instanceof Validator);
    }

    @Test
    public void testFiveFieldsLineIsValid() {
        Assert.assertTrue(validator.validate("a,b,c,d,e"));
    }
}
