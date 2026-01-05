package be.brahms.TFE_RentServe.enums;

/**
 * This enum represents different statuses of a process or transaction.
 */
public enum Status {
    /**
     * The process or transaction is waiting to be started.
     */
    PENDING,
    /**
     * The process or transaction is currently in progress.
     */
    IN_PROGRESS,
    /**
     * The process or transaction has been completed and paid.
     */
    PAID,
    /**
     * The process or transaction has been cancelled.
     */
    CANCELLED,
    /**
     * The process or transaction is past the due date.
     */
    OVERDUE,
    /**
     * The process or transaction has failed.
     */
    FAILED,
}
