package be.brahms.TFE_RentServe.models.dtos.bill;

import be.brahms.TFE_RentServe.enums.Status;
import java.time.LocalDate;

/**
 * BillDTO is a data transfer Object for bill information.
 *
 * @param id the identifier of bill
 * @param status the status of payment
 * @param createdAt date created the bill
 * @param updateAt date updated the bill
 */
public record BillDTO(long id, Status status, LocalDate createdAt, LocalDate updateAt) {}
