package kz.assem.telemedicine;

public final class MembershipPricing implements PricingStrategy {
    private final FeeSource fees;

    public MembershipPricing(FeeSource fees) { this.fees = fees; }

    public int quote(ConsultationRequest request) {
        int extraMinutes = Math.max(0, request.minutes() - request.includedMinutes());
        return ((extraMinutes + 14) / 15) * fees.blockFee();
    }
}

