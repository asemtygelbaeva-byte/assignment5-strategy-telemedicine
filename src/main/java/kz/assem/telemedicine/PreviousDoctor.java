package kz.assem.telemedicine;

import java.util.List;

public final class PreviousDoctor implements DoctorStrategy {
    public Doctor choose(ConsultationRequest request, List<Doctor> doctors) {
        return doctors.stream().filter(doctor -> doctor.id().equals(request.previousDoctorId()))
                .findFirst().orElseGet(() -> new EarliestDoctor().choose(request, doctors));
    }
}

