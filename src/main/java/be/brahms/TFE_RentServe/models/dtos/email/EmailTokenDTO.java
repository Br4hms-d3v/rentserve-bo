package be.brahms.TFE_RentServe.models.dtos.email;

import java.util.UUID;

/**
 * Data for email token.
 *
 * @param confirmationToken The token.
 */
public record EmailTokenDTO(
        String confirmationToken
) {
    /**
     * Makes a new EmailTokenDTO.
     */
    public EmailTokenDTO() {
        this(UUID.randomUUID().toString());
    }
}
