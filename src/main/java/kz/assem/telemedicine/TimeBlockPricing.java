package kz.assem.telemedicine;

public final class TimeBlockPricing implements PricingStrategy {
    private final FeeSource fees;

    public TimeBlockPricing(FeeSource fees) { this.fees = fees; }

    public int quote(ConsultationRequest request) {
        int blocks = (request.minutes() + 14) / 15;
        return blocks * fees.blockFee();
    }
}

