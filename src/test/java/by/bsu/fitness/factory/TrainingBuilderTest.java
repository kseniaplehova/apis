package by.bsu.fitness.factory;

import by.bsu.fitness.entity.Training;
import by.bsu.fitness.entity.TrainingState;
import by.bsu.fitness.entity.TrainingType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.function.Supplier;

public class TrainingBuilderTest {

    private static final LocalDateTime DT = LocalDateTime.of(2026, 6, 1, 10, 0);

    @Test
    public void testClassicBuildReturnsNotNull() {
        Training t = new TrainingBuilder()
                .clientId(1L).coachName("Coach").type(TrainingType.GROUP)
                .dateTime(DT).durationMinutes(60).state(TrainingState.PLANNED)
                .build();
        Assert.assertNotNull(t);
    }

    @Test
    public void testClassicBuildSetsClientId() {
        Training t = new TrainingBuilder()
                .clientId(7L).coachName("Coach").type(TrainingType.GROUP)
                .dateTime(DT).durationMinutes(60).state(TrainingState.PLANNED)
                .build();
        Assert.assertEquals(t.getClientId(), 7L);
    }

    @Test
    public void testClassicBuildSetsCoachName() {
        Training t = new TrainingBuilder()
                .clientId(1L).coachName("Петров").type(TrainingType.GROUP)
                .dateTime(DT).durationMinutes(60).state(TrainingState.PLANNED)
                .build();
        Assert.assertEquals(t.getCoachName(), "Петров");
    }

    @Test
    public void testClassicBuildSetsType() {
        Training t = new TrainingBuilder()
                .clientId(1L).coachName("Coach").type(TrainingType.PERSONAL)
                .dateTime(DT).durationMinutes(30).state(TrainingState.PLANNED)
                .build();
        Assert.assertEquals(t.getType(), TrainingType.PERSONAL);
    }

    @Test
    public void testClassicBuildSetsDuration() {
        Training t = new TrainingBuilder()
                .clientId(1L).coachName("Coach").type(TrainingType.GROUP)
                .dateTime(DT).durationMinutes(90).state(TrainingState.PLANNED)
                .build();
        Assert.assertEquals(t.getDurationMinutes(), 90);
    }

    @Test
    public void testClassicBuildSetsState() {
        Training t = new TrainingBuilder()
                .clientId(1L).coachName("Coach").type(TrainingType.GROUP)
                .dateTime(DT).durationMinutes(60).state(TrainingState.ACTIVE)
                .build();
        Assert.assertEquals(t.getState(), TrainingState.ACTIVE);
    }

    @Test
    public void testClassicBuildAssignsPositiveId() {
        Training t = new TrainingBuilder()
                .clientId(1L).coachName("Coach").type(TrainingType.GROUP)
                .dateTime(DT).durationMinutes(60).state(TrainingState.PLANNED)
                .build();
        Assert.assertTrue(t.getId() > 0);
    }

    @Test
    public void testElegantBuilderReturnsNotNull() {
        Training t = TrainingBuilder.builder()
                .clientId(1L).coachName("Coach").type(TrainingType.GROUP)
                .durationMinutes(60).state(TrainingState.PLANNED)
                .build();
        Assert.assertNotNull(t);
    }

    @Test
    public void testElegantBuilderSetsClientId() {
        Training t = TrainingBuilder.builder()
                .clientId(5L).coachName("Coach").type(TrainingType.GROUP)
                .durationMinutes(60).state(TrainingState.PLANNED)
                .build();
        Assert.assertEquals(t.getClientId(), 5L);
    }

    @Test
    public void testElegantBuilderSetsCoachName() {
        Training t = TrainingBuilder.builder()
                .clientId(1L).coachName("Иванов").type(TrainingType.GROUP)
                .durationMinutes(60).state(TrainingState.PLANNED)
                .build();
        Assert.assertEquals(t.getCoachName(), "Иванов");
    }

    @Test
    public void testElegantBuilderSetsType() {
        Training t = TrainingBuilder.builder()
                .clientId(1L).coachName("Coach").type(TrainingType.PERSONAL)
                .durationMinutes(45).state(TrainingState.PLANNED)
                .build();
        Assert.assertEquals(t.getType(), TrainingType.PERSONAL);
    }

    @Test
    public void testOptionalStepIsSupplier() {
        TrainingBuilder.OptionalStep step = (TrainingBuilder.OptionalStep) TrainingBuilder.builder()
                .clientId(1L).coachName("Coach").type(TrainingType.GROUP);
        Assert.assertNotNull(step.get());
    }

    @Test
    public void testBuilderAsSupplierReturnsTraining() {
        Supplier<Training> supplier = TrainingBuilder.builder()
                .clientId(1L).coachName("Coach").type(TrainingType.GROUP)
                .durationMinutes(60).state(TrainingState.PLANNED);
        Assert.assertNotNull(supplier.get());
    }

    @Test
    public void testGetDelegatesToBuild() {
        TrainingBuilder builder = new TrainingBuilder()
                .clientId(1L).coachName("Coach").type(TrainingType.GROUP)
                .durationMinutes(60).state(TrainingState.PLANNED);
        Assert.assertNotNull(builder.get());
    }

    @Test
    public void testOfFromSupplierCopiesClientId() {
        Training original = new Training(1L, 3L, "X", TrainingType.GROUP, DT, 60, TrainingState.PLANNED);
        TrainingBuilder copy = TrainingBuilder.of(() -> original);
        Assert.assertEquals(copy.build().getClientId(), 3L);
    }

    @Test
    public void testOfFromSupplierCopiesCoachName() {
        Training original = new Training(1L, 1L, "Сидоров", TrainingType.GROUP, DT, 60, TrainingState.PLANNED);
        TrainingBuilder copy = TrainingBuilder.of(() -> original);
        Assert.assertEquals(copy.build().getCoachName(), "Сидоров");
    }

    @Test
    public void testTwoBuildsHaveDifferentIds() {
        TrainingBuilder builder = new TrainingBuilder()
                .clientId(1L).coachName("Coach").type(TrainingType.GROUP)
                .durationMinutes(60).state(TrainingState.PLANNED);
        long id1 = builder.build().getId();
        long id2 = builder.build().getId();
        Assert.assertNotEquals(id1, id2);
    }
}
