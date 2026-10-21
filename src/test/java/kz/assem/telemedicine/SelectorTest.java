package kz.assem.telemedicine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class SelectorTest {
    private final PricingSelector pricing = new PricingSelector(new DemoFeeSource());
    private final DoctorSelector doctors = new DoctorSelector();

    @Test
    void memberRuleHasPriorityOverLongDuration() {
        assertInstanceOf(MembershipPricing.class, pricing.select(visit(50, true, false, false)));
    }

    @Test
    void nonMemberAboveThirtyMinutesUsesTimeBlocks() {
        assertInstanceOf(TimeBlockPricing.class, pricing.select(visit(31, false, false, false)));
    }

    @Test
    void nonMemberAtThirtyMinutesUsesFlatPricing() {
        assertInstanceOf(FlatPricing.class, pricing.select(visit(30, false, false, false)));
    }

    @Test
    void urgentVisitHasPriorityOverFollowUp() {
        assertInstanceOf(EarliestDoctor.class, doctors.select(visit(20, false, true, true)));
    }

    @Test
    void nonUrgentFollowUpUsesPreviousDoctor() {
        assertInstanceOf(PreviousDoctor.class, doctors.select(visit(20, false, false, true)));
    }

    @Test
    void ordinaryVisitUsesBestRatedDoctor() {
        assertInstanceOf(BestRatedDoctor.class, doctors.select(visit(20, false, false, false)));
    }

    private ConsultationRequest visit(int minutes, boolean member, boolean urgent, boolean followUp) {
        return new ConsultationRequest(minutes, member, 30, urgent, followUp, "D3");
    }
}

