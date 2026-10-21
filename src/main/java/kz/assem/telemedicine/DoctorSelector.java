package kz.assem.telemedicine;

public final class DoctorSelector {
    public DoctorStrategy select(ConsultationRequest request) {
        if (request.urgent()) return new EarliestDoctor();
        if (request.followUp()) return new PreviousDoctor();
        return new BestRatedDoctor();
    }
}

