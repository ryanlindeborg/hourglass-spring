package com.ryanlindeborg.hourglass.hourglassspring.model;

public enum OAuthProviderType {
    GITHUB;

    // Note: Spring specifies the lowercase string names for OAuthProviderType registration id
    public static OAuthProviderType fromSpringRegistrationId(String registrationId) {
        switch(registrationId) {
            case "github":
                return GITHUB;
            default:
                throw new IllegalArgumentException("Unsupported OAuth registration Id :" + registrationId);
        }
    }
}
