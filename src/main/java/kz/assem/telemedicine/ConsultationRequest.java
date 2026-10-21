package kz.assem.telemedicine;

import java.util.Objects;

public record ConsultationRequest(int minutes, boolean member, int includedMinutes,
                                  boolean urgent, boolean followUp, String previousDoctorId) {
    public ConsultationRequest {
        if (minutes < 1 || minutes > 120 || includedMinutes < 0 || includedMinutes > 120) {
            throw new IllegalArgumentException("Duration must be 1-120; included minutes must be 0-120.");
        }
        Objects.requireNonNull(previousDoctorId, "Previous doctor id cannot be null.");
    }
}

