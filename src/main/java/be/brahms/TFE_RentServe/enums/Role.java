package be.brahms.TFE_RentServe.enums;

/**
 * This enum represents the different roles a user can have.
 */
public enum Role {
    /**
     * A regular user with basic access.
     */
    MEMBER,
    /**
     * A user with special privileges to manage content or users.
     */
    MODERATOR,
    /**
     * A user with full access to all features and settings.
     */
    ADMIN,
}
