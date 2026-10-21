package kz.assem.telemedicine;

public final class PricingSelector {
    private final FeeSource fees;

    public PricingSelector(FeeSource fees) {
        this.fees = fees;
    }

    public PricingChoice select(ConsultationRequest request) {
        if (request.member()) return new PricingChoice(PricingChoice.Type.MEMBERSHIP, fees);
        if (request.minutes() > 30) return new PricingChoice(PricingChoice.Type.TIME_BLOCK, fees);
        return new PricingChoice(PricingChoice.Type.FLAT, fees);
    }
}

