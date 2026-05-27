package by.bsu.fitness.repository;

import by.bsu.fitness.entity.Training;
import by.bsu.fitness.entity.TrainingState;
import by.bsu.fitness.entity.TrainingType;
import by.bsu.fitness.exception.RepositoryException;
import by.bsu.fitness.specification.TrainingByClientIdSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class TrainingRepositoryTest {

    private TrainingRepository repository;
    private static final LocalDateTime DT = LocalDateTime.of(2026, 6, 1, 10, 0);

    @BeforeMethod
    public void setUp() {
        repository = new TrainingRepository();
    }

    @Test
    public void testFindAllEmptyRepositoryReturnsEmptyList() {
        Assert.assertTrue(repository.findAll().isEmpty());
    }

    @Test
    public void testAddAndFindAllReturnsOneElement() throws RepositoryException {
        repository.add(new Training(1L, 1L, "Coach", TrainingType.GROUP, DT, 60, TrainingState.PLANNED));
        Assert.assertEquals(repository.findAll().size(), 1);
    }

    @Test
    public void testAddTwoFindAllReturnsTwoElements() throws RepositoryException {
        repository.add(new Training(1L, 1L, "Coach", TrainingType.GROUP, DT, 60, TrainingState.PLANNED));
        repository.add(new Training(2L, 2L, "Other", TrainingType.PERSONAL, DT, 45, TrainingState.PLANNED));
        Assert.assertEquals(repository.findAll().size(), 2);
    }

    @Test(expectedExceptions = RepositoryException.class)
    public void testAddNullThrowsRepositoryException() throws RepositoryException {
        repository.add(null);
    }

    @Test
    public void testQueryByClientIdSpecificationReturnsMatchingTrainings() throws RepositoryException {
        repository.add(new Training(1L, 1L, "Coach", TrainingType.GROUP, DT, 60, TrainingState.PLANNED));
        repository.add(new Training(2L, 2L, "Coach", TrainingType.GROUP, DT, 60, TrainingState.PLANNED));
        List<Training> result = repository.query(new TrainingByClientIdSpecification(1L));
        Assert.assertEquals(result.size(), 1);
    }

    @Test
    public void testQueryByClientIdSpecificationReturnsCorrectTraining() throws RepositoryException {
        repository.add(new Training(1L, 1L, "Coach", TrainingType.GROUP, DT, 60, TrainingState.PLANNED));
        List<Training> result = repository.query(new TrainingByClientIdSpecification(1L));
        Assert.assertEquals(result.get(0).getClientId(), 1L);
    }

    @Test
    public void testQueryNoMatchReturnsEmpty() throws RepositoryException {
        repository.add(new Training(1L, 1L, "Coach", TrainingType.GROUP, DT, 60, TrainingState.PLANNED));
        List<Training> result = repository.query(new TrainingByClientIdSpecification(999L));
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testSortByDurationAscendingFirstElementIsSmallest() throws RepositoryException {
        repository.add(new Training(1L, 1L, "Coach", TrainingType.GROUP, DT, 90, TrainingState.PLANNED));
        repository.add(new Training(2L, 1L, "Coach", TrainingType.GROUP, DT, 30, TrainingState.PLANNED));
        List<Training> sorted = repository.sort(Comparator.comparingInt(Training::getDurationMinutes));
        Assert.assertEquals(sorted.get(0).getDurationMinutes(), 30);
    }

    @Test
    public void testSortByDurationDescendingFirstElementIsLargest() throws RepositoryException {
        repository.add(new Training(1L, 1L, "Coach", TrainingType.GROUP, DT, 30, TrainingState.PLANNED));
        repository.add(new Training(2L, 1L, "Coach", TrainingType.GROUP, DT, 90, TrainingState.PLANNED));
        List<Training> sorted = repository.sort(Comparator.comparingInt(Training::getDurationMinutes).reversed());
        Assert.assertEquals(sorted.get(0).getDurationMinutes(), 90);
    }

    @Test
    public void testFindAllReturnsDefensiveCopy() throws RepositoryException {
        repository.add(new Training(1L, 1L, "Coach", TrainingType.GROUP, DT, 60, TrainingState.PLANNED));
        List<Training> list = repository.findAll();
        list.clear();
        Assert.assertEquals(repository.findAll().size(), 1);
    }
}
