package kz.assem.telemedicine;

public final class PricingSelector {
    private final FeeSource fees;

    public PricingSelector(FeeSource fees) {
        this.fees = fees;
    }

    public PricingStrategy select(ConsultationRequest request) {
        if (request.member()) return new MembershipPricing(fees);
        if (request.minutes() > 30) return new TimeBlockPricing(fees);
        return new FlatPricing(fees);
    }
}

