package com.teamthree.conferencescheduler.constants.errors;

public final class ErrorHandlingConstants {
    public static final String EMAIL_EXISTS = "Sorry, this username already exists. Please try new one!";
    public static final String EMAIL_FIELD_IS_EMPTY = "Username field, must not be empty!";
    public static final String NAME_FIELD_IS_EMPTY = "Name field, must not be empty!";
    public static final String PASSWORD_FIELD_IS_EMPTY = "Password field, must not be empty!";
    public static final String CONFIRM_PASS_NOT_MATCHING = "Confirm password is not matching the password!";

    public static final String VENUE_CANNOT_BE_NULL = "Venue cannot be empty!";
    public static final String VENUE_ALREADY_EXISTS = "Venue with that name already exists, please choose new one!";

    private ErrorHandlingConstants() {
        super();
    }
}
