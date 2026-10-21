package kz.assem.telemedicine;

public final class FlatPricing implements PricingStrategy {
    private final FeeSource fees;

    public FlatPricing(FeeSource fees) { this.fees = fees; }

    public int quote(ConsultationRequest request) {
        return fees.sessionFee();
    }
}

