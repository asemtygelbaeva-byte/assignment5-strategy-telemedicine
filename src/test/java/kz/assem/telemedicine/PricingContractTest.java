package kz.assem.telemedicine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PricingContractTest {
    private final FeeSource fees = new FakeFees();

    @Test
    void shortVisitHasFlatFee() {
        assertEquals(6000, quote(20, false, 0));
    }

    @Test
    void thirtyMinutesStillHasFlatFee() {
        assertEquals(6000, quote(30, false, 0));
    }

    @Test
    void longVisitUsesStartedFifteenMinuteBlocks() {
        assertEquals(6000, quote(31, false, 0));
    }

    @Test
    void membershipBillsOnlyMinutesOutsideAllowance() {
        assertEquals(2000, quote(35, true, 30));
    }

    @Test
    void allowanceCanCoverTheWholeVisit() {
        assertEquals(0, quote(20, true, 30));
    }

    @Test
    void membershipRuleWinsForALongVisit() {
        assertEquals(4000, quote(50, true, 30));
    }

    private int quote(int minutes, boolean member, int included) {
        var request = new ConsultationRequest(minutes, member, included, false, false, "");
        var context = new PricingContext(new PricingSelector(fees).select(request));
        return context.quote(request);
    }

    private static class FakeFees implements FeeSource {
        public int sessionFee() { return 6000; }
        public int blockFee() { return 2000; }
    }
}

