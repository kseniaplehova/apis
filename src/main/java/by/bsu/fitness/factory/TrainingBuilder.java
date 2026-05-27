package by.bsu.fitness.factory;

import by.bsu.fitness.entity.Training;
import by.bsu.fitness.entity.TrainingState;
import by.bsu.fitness.entity.TrainingType;
import by.bsu.fitness.util.IdGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.function.Supplier;

/**
 * Fluent Training builder with two usage modes:
 *
 * 1. Classic fluent API (backward-compatible):
 *    new TrainingBuilder().clientId(1).coachName("X").type(GROUP).build()
 *
 * 2. Elegant Builder via {@link #builder()} — compiler enforces the order of
 *    required fields (clientId → coachName → type) through step interfaces.
 *    The final {@link OptionalStep} extends {@code Supplier<Training>}, so a
 *    configured (but not yet built) builder can be used wherever a
 *    {@code Supplier<Training>} is expected.
 */
public class TrainingBuilder implements Supplier<Training> {

    private static final Logger LOGGER = LogManager.getLogger(TrainingBuilder.class);

    // ─── Elegant Builder step interfaces ─────────────────────────────────────

    /** Required step 1: must set clientId first. */
    public interface ClientIdStep {
        CoachStep clientId(long clientId);
    }

    /** Required step 2: must set coachName after clientId. */
    public interface CoachStep {
        TypeStep coachName(String coachName);
    }

    /** Required step 3: must set type after coachName. */
    public interface TypeStep {
        OptionalStep type(TrainingType type);
    }

    /**
     * Optional step: set remaining fields, then call {@link #build()}.
     * Extends {@code Supplier<Training>} — a configured builder can be stored
     * or passed as a lazy factory without calling build() immediately.
     */
    public interface OptionalStep extends Supplier<Training> {
        OptionalStep dateTime(LocalDateTime dateTime);
        OptionalStep durationMinutes(int durationMinutes);
        OptionalStep state(TrainingState state);
        Training build();
    }

    // ─── Entry points ─────────────────────────────────────────────────────────

    /** Elegant Builder entry point — required fields enforced at compile time. */
    public static ClientIdStep builder() {
        return new StepBuilder();
    }

    /**
     * Creates a classic fluent builder pre-populated from a {@code Supplier<Training>}.
     * Useful for copying and modifying a previously configured training.
     */
    public static TrainingBuilder of(Supplier<Training> supplier) {
        Training t = supplier.get();
        TrainingBuilder b = new TrainingBuilder();
        b.clientId = t.getClientId();
        b.coachName = t.getCoachName();
        b.type = t.getType();
        b.dateTime = t.getDateTime();
        b.durationMinutes = t.getDurationMinutes();
        b.state = t.getState();
        LOGGER.debug("TrainingBuilder created from Supplier");
        return b;
    }

    // ─── Classic fluent builder ───────────────────────────────────────────────

    private long clientId;
    private String coachName;
    private TrainingType type;
    private LocalDateTime dateTime;
    private int durationMinutes;
    private TrainingState state;

    public TrainingBuilder clientId(long clientId) {
        this.clientId = clientId;
        return this;
    }

    public TrainingBuilder coachName(String coachName) {
        this.coachName = coachName;
        return this;
    }

    public TrainingBuilder type(TrainingType type) {
        this.type = type;
        return this;
    }

    public TrainingBuilder dateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public TrainingBuilder durationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
        return this;
    }

    public TrainingBuilder state(TrainingState state) {
        this.state = state;
        return this;
    }

    public Training build() {
        long id = IdGenerator.nextId();
        Training training = new Training(id, clientId, coachName, type, dateTime, durationMinutes, state);
        LOGGER.debug("Training built: {}", training);
        return training;
    }

    /** {@code Supplier<Training>} implementation — delegates to {@link #build()}. */
    @Override
    public Training get() {
        return build();
    }

    // ─── Private step-builder implementation ─────────────────────────────────

    private static class StepBuilder implements ClientIdStep, CoachStep, TypeStep, OptionalStep {

        private long clientId;
        private String coachName;
        private TrainingType type;
        private LocalDateTime dateTime;
        private int durationMinutes;
        private TrainingState state;

        @Override
        public CoachStep clientId(long clientId) {
            this.clientId = clientId;
            return this;
        }

        @Override
        public TypeStep coachName(String coachName) {
            this.coachName = coachName;
            return this;
        }

        @Override
        public OptionalStep type(TrainingType type) {
            this.type = type;
            return this;
        }

        @Override
        public OptionalStep dateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        @Override
        public OptionalStep durationMinutes(int durationMinutes) {
            this.durationMinutes = durationMinutes;
            return this;
        }

        @Override
        public OptionalStep state(TrainingState state) {
            this.state = state;
            return this;
        }

        @Override
        public Training build() {
            long id = IdGenerator.nextId();
            Training training = new Training(id, clientId, coachName, type, dateTime, durationMinutes, state);
            LOGGER.debug("Training built via Elegant Builder: {}", training);
            return training;
        }

        @Override
        public Training get() {
            return build();
        }
    }
}
