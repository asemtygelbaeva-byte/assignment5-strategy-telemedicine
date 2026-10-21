package kz.assem.telemedicine;

public record PricingChoice(Type type, FeeSource fees) {
    public enum Type { FLAT, TIME_BLOCK, MEMBERSHIP }
}

