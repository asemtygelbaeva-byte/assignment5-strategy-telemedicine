package kz.assem.telemedicine;

public final class DemoFeeSource implements FeeSource {
    public int sessionFee() { return 6000; }
    public int blockFee() { return 2000; }
}

