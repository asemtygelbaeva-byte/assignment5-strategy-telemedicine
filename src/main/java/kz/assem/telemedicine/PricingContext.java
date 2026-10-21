package kz.assem.telemedicine;

public final class PricingContext {
    private PricingChoice choice;

    public PricingContext(PricingChoice choice) {
        this.choice = choice;
    }

    public void setStrategy(PricingChoice choice) {
        this.choice = choice;
    }

    public int quote(ConsultationRequest request) {
        return switch (choice.type()) {
            case FLAT -> choice.fees().sessionFee();
            case TIME_BLOCK -> ((request.minutes() + 14) / 15) * choice.fees().blockFee();
            case MEMBERSHIP -> ((Math.max(0, request.minutes() - request.includedMinutes()) + 14) / 15)
                    * choice.fees().blockFee();
        };
    }
}

