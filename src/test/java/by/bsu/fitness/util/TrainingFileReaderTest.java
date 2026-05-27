package by.bsu.fitness.util;

import by.bsu.fitness.entity.Training;
import by.bsu.fitness.validator.DataValidator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrainingFileReaderTest {

    private TrainingFileReader reader;

    @BeforeMethod
    public void setUp() {
        reader = new TrainingFileReader(new DataValidator());
    }

    @Test
    public void testParseValidLineReturnsOneTraining() {
        List<String> lines = Arrays.asList("1,Coach,GROUP,2026-06-01T10:00,60,PLANNED");
        List<String> errors = new ArrayList<>();
        Assert.assertEquals(reader.parseTrainings(lines, errors).size(), 1);
    }

    @Test
    public void testParsedTrainingHasCorrectClientId() {
        List<String> lines = Arrays.asList("5,Coach,GROUP,2026-06-01T10:00,60,PLANNED");
        List<String> errors = new ArrayList<>();
        Assert.assertEquals(reader.parseTrainings(lines, errors).get(0).getClientId(), 5L);
    }

    @Test
    public void testParseInvalidLineReturnsEmptyTrainings() {
        List<String> lines = Arrays.asList("bad");
        List<String> errors = new ArrayList<>();
        Assert.assertTrue(reader.parseTrainings(lines, errors).isEmpty());
    }

    @Test
    public void testParseInvalidLineAddsToErrors() {
        List<String> lines = Arrays.asList("bad");
        List<String> errors = new ArrayList<>();
        reader.parseTrainings(lines, errors);
        Assert.assertEquals(errors.size(), 1);
    }

    @Test
    public void testParseHeaderLineSkipped() {
        List<String> lines = Arrays.asList("clientId,coach,type,dateTime,duration,state");
        List<String> errors = new ArrayList<>();
        Assert.assertTrue(reader.parseTrainings(lines, errors).isEmpty());
    }

    @Test
    public void testParseMultipleValidLinesReturnsAll() {
        List<String> lines = Arrays.asList(
                "1,Coach,GROUP,2026-06-01T10:00,60,PLANNED",
                "2,Coach,PERSONAL,2026-07-01T11:00,45,PLANNED"
        );
        List<String> errors = new ArrayList<>();
        Assert.assertEquals(reader.parseTrainings(lines, errors).size(), 2);
    }

    @Test
    public void testParseEmptyListReturnsEmptyTrainings() {
        List<String> errors = new ArrayList<>();
        Assert.assertTrue(reader.parseTrainings(new ArrayList<>(), errors).isEmpty());
    }

    @Test
    public void testParsedTrainingHasCorrectCoachName() {
        List<String> lines = Arrays.asList("1,Иванов,GROUP,2026-06-01T10:00,60,PLANNED");
        List<String> errors = new ArrayList<>();
        List<Training> result = reader.parseTrainings(lines, errors);
        Assert.assertEquals(result.get(0).getCoachName(), "Иванов");
    }
}
