package be.brahms.TFE_RentServe.utilities;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * This class helps to hash passwords and check them.
 */
@Component
public class BCryptUtil {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Constructor to create BCryptUtil.
     *
     * @param bCryptPasswordEncoder The encoder to hash passwords.
     */
    public BCryptUtil(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Hashes a password
     *
     * @param password The password to hash.
     * @return The hashed password.
     */
    public String hash(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    /**
     * Checks if a password matches a hashed password
     *
     * @param password       The password to check.
     * @param hashedPassword The hashed password to compare with.
     * @return True if the password matches, false otherwise.eturn
     */
    public boolean checkPassword(String password, String hashedPassword) {
        return bCryptPasswordEncoder.matches(password, hashedPassword);
    }

}
