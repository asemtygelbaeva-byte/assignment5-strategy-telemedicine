package kz.assem.telemedicine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DoctorStrategyTest {
    private static final List<Doctor> DOCTORS = List.of(
            new Doctor("D1", "Dr Ali", 4.6, 5),
            new Doctor("D2", "Dr Lee", 4.9, 20),
            new Doctor("D3", "Dr Kim", 4.7, 10));
    private static final ConsultationRequest VISIT =
            new ConsultationRequest(20, false, 0, false, true, "D3");

    @Test
    void earliestDoctorUsesWaitingTime() {
        assertEquals("D1", new EarliestDoctor().choose(VISIT, DOCTORS).id());
    }

    @Test
    void bestRatedDoctorUsesPatientRatings() {
        assertEquals("D2", new BestRatedDoctor().choose(VISIT, DOCTORS).id());
    }

    @Test
    void previousDoctorPreservesContinuity() {
        assertEquals("D3", new PreviousDoctor().choose(VISIT, DOCTORS).id());
    }

    @Test
    void missingPreviousDoctorFallsBackToEarliest() {
        var request = new ConsultationRequest(20, false, 0, false, true, "missing");
        assertEquals("D1", new PreviousDoctor().choose(request, DOCTORS).id());
    }

    @Test
    void contextSwitchesFromRatingToContinuityAtRuntime() {
        var context = new DoctorContext(new BestRatedDoctor());
        assertEquals("D2", context.choose(VISIT, DOCTORS).id());
        context.setStrategy(new PreviousDoctor());
        assertEquals("D3", context.choose(VISIT, DOCTORS).id());
    }

    @ParameterizedTest
    @MethodSource("strategies")
    void rejectsAnEmptyDoctorList(DoctorStrategy strategy) {
        assertThrows(IllegalArgumentException.class, () -> strategy.choose(VISIT, List.of()));
    }

    static Stream<DoctorStrategy> strategies() {
        return Stream.of(new EarliestDoctor(), new BestRatedDoctor(), new PreviousDoctor());
    }
}

