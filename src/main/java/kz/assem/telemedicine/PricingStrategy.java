package kz.assem.telemedicine;

public interface PricingStrategy {
    int quote(ConsultationRequest request);
}

