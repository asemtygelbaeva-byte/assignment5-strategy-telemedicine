package kz.assem.telemedicine;

import java.util.Comparator;
import java.util.List;

public final class BestRatedDoctor implements DoctorStrategy {
    public Doctor choose(ConsultationRequest request, List<Doctor> doctors) {
        return doctors.stream().max(Comparator.comparingDouble(Doctor::rating))
                .orElseThrow(() -> new IllegalArgumentException("No doctors available."));
    }
}

