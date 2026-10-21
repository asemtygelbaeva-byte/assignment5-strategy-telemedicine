package kz.assem.telemedicine;

import java.util.List;

public interface DoctorStrategy {
    Doctor choose(ConsultationRequest request, List<Doctor> doctors);
}

