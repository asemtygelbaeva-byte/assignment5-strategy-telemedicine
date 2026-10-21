package kz.assem.telemedicine;

public final class PricingContext {
    private PricingStrategy strategy;

    public PricingContext(PricingStrategy strategy) {
        setStrategy(strategy);
    }

    public void setStrategy(PricingStrategy strategy) {
        this.strategy = java.util.Objects.requireNonNull(strategy);
    }

    public int quote(ConsultationRequest request) {
        return strategy.quote(request);
    }
}

