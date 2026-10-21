package kz.assem.telemedicine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PricingStrategyTest {
    private static final FeeSource FEES = new FakeFees();
    private static final ConsultationRequest VISIT =
            new ConsultationRequest(50, true, 30, false, true, "D3");

    @Test
    void flatPricingUsesTheInjectedSessionFee() {
        assertEquals(6000, new FlatPricing(FEES).quote(VISIT));
    }

    @Test
    void timeBlockPricingRoundsUpPartialBlocks() {
        assertEquals(8000, new TimeBlockPricing(FEES).quote(VISIT));
        assertEquals(4000, new TimeBlockPricing(FEES).quote(
                new ConsultationRequest(30, false, 0, false, false, "")));
    }

    @Test
    void membershipSubtractsIncludedMinutes() {
        assertEquals(4000, new MembershipPricing(FEES).quote(VISIT));
        assertEquals(0, new MembershipPricing(FEES).quote(
                new ConsultationRequest(30, true, 30, false, false, "")));
    }

    @Test
    void contextReplacesTheAlgorithmAtRuntime() {
        var context = new PricingContext(new FlatPricing(FEES));
        assertEquals(6000, context.quote(VISIT));
        context.setStrategy(new MembershipPricing(FEES));
        assertEquals(4000, context.quote(VISIT));
        context.setStrategy(request -> 1234);
        assertEquals(1234, context.quote(VISIT));
    }

    @Test
    void aDifferentDataSourceChangesRatesWithoutChangingStrategies() {
        FeeSource changedFees = new FeeSource() {
            public int sessionFee() { return 7000; }
            public int blockFee() { return 1500; }
        };
        assertEquals(7000, new FlatPricing(changedFees).quote(VISIT));
        assertEquals(6000, new TimeBlockPricing(changedFees).quote(VISIT));
    }

    @ParameterizedTest
    @MethodSource("strategies")
    void priceIsNeverNegative(PricingStrategy strategy) {
        for (int minutes : List.of(1, 15, 16, 30, 31, 120)) {
            for (int included : List.of(0, 30, 120)) {
                var request = new ConsultationRequest(minutes, true, included, false, false, "");
                assertTrue(strategy.quote(request) >= 0);
            }
        }
    }

    @Test
    void rejectsInvalidRequestDurations() {
        assertThrows(IllegalArgumentException.class,
                () -> new ConsultationRequest(0, false, 0, false, false, ""));
        assertThrows(IllegalArgumentException.class,
                () -> new ConsultationRequest(121, false, 0, false, false, ""));
    }

    static Stream<PricingStrategy> strategies() {
        return Stream.of(new FlatPricing(FEES), new TimeBlockPricing(FEES), new MembershipPricing(FEES));
    }

    private static class FakeFees implements FeeSource {
        public int sessionFee() { return 6000; }
        public int blockFee() { return 2000; }
    }
}

