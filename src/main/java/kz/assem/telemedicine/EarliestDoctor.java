package kz.assem.telemedicine;

import java.util.Comparator;
import java.util.List;

public final class EarliestDoctor implements DoctorStrategy {
    public Doctor choose(ConsultationRequest request, List<Doctor> doctors) {
        return doctors.stream().min(Comparator.comparingInt(Doctor::waitMinutes))
                .orElseThrow(() -> new IllegalArgumentException("No doctors available."));
    }
}

