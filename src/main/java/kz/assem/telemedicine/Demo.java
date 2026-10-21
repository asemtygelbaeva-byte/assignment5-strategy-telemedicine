package kz.assem.telemedicine;

import java.util.List;

public final class Demo {
    public static void main(String[] args) {
        FeeSource fees = new DemoFeeSource();
        var visit = new ConsultationRequest(50, true, 30, false, true, "D3");
        var doctors = List.of(
                new Doctor("D1", "Dr Ali", 4.6, 5),
                new Doctor("D2", "Dr Lee", 4.9, 20),
                new Doctor("D3", "Dr Kim", 4.7, 10));

        System.out.println("Telemedicine Strategy demo");
        System.out.println("Same visit: 50 minutes, member, 30 included minutes, previous doctor D3.");
        for (PricingStrategy strategy : List.of(
                new FlatPricing(fees), new TimeBlockPricing(fees), new MembershipPricing(fees))) {
            System.out.println(strategy.getClass().getSimpleName() + ": " + strategy.quote(visit) + " KZT");
        }
        for (DoctorStrategy strategy : List.of(
                new EarliestDoctor(), new BestRatedDoctor(), new PreviousDoctor())) {
            System.out.println(strategy.getClass().getSimpleName() + ": " + strategy.choose(visit, doctors).name());
        }

        var pricing = new PricingContext(new FlatPricing(fees));
        System.out.println("Manual pricing: " + pricing.quote(visit));
        pricing.setStrategy(new MembershipPricing(fees));
        System.out.println("After runtime switch: " + pricing.quote(visit));
        pricing.setStrategy(new PricingSelector(fees).select(visit));
        System.out.println("Automatic pricing: " + pricing.quote(visit));

        var matching = new DoctorContext(new BestRatedDoctor());
        System.out.println("Manual doctor: " + matching.choose(visit, doctors).name());
        matching.setStrategy(new PreviousDoctor());
        System.out.println("After runtime switch: " + matching.choose(visit, doctors).name());
        matching.setStrategy(new DoctorSelector().select(visit));
        System.out.println("Automatic doctor: " + matching.choose(visit, doctors).name());
    }
}

