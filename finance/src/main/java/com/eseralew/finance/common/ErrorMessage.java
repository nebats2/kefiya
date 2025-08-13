package com.kefiya.home.common;

public enum ErrorMessage {
    mobile_already_registered ("mobile is already registered"),
    invalid_parameter ("Invalid parameters"),
    bad_request("Bad request"),
    entity_is_not_active ("Invalid parameters"),
    entity_is_deleted("Entity is Deleted"),
    entity_is_suspended("Entity is Suspended"),
    entity_is_blocked("Entity is blocked"),
    entity_is_not_found("Entity is not found"),
    name_is_not_valid("Name is not valid"),
    unexpected_error("Unexpected error"),
    wrong_confirmation_code("Wrong confirmation code"),
    invalid_date("Invalid date"),
    try_again_later("Try again later"),
    invalid_mobile("Invalid mobile"),
    invalid_email("Invalid email"),
    user_has_no_permission("user has no permission"),
    incorrect_credentials("Incorrect credentials"),
    request_new_otp("Request new OTP"),
    mobile_is_not_confirmed("Mobile is not confirmed"),

    app_client_is_not_found("App client is not found"),
    user_mobile_already_registered ("User mobile is already registered"),
    profile_is_not_active("Profile is not active"),
    user_reviewed_for_profile("User reviewed for the profile"),
    user_already_has_profile("user already has a profile");

    final String title;

    ErrorMessage(String title) {
        this.title = title;
    }
}
