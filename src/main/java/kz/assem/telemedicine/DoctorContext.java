package kz.assem.telemedicine;

import java.util.List;
import java.util.Objects;

public final class DoctorContext {
    private DoctorStrategy strategy;

    public DoctorContext(DoctorStrategy strategy) {
        setStrategy(strategy);
    }

    public void setStrategy(DoctorStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy);
    }

    public Doctor choose(ConsultationRequest request, List<Doctor> doctors) {
        return strategy.choose(request, doctors);
    }
}

