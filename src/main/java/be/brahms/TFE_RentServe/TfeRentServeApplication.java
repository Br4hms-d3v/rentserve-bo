package be.brahms.TFE_RentServe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class of the application.
 * It starts the Spring Boot program.
 *
 * @author Brahim K
 */
@SpringBootApplication
public class TfeRentServeApplication {

    /**
     * Default constructor for the application.
     */
    public TfeRentServeApplication() {
    }

    /**
     * Main method.
     * It's runs the application.
     *
     * @param args program arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(TfeRentServeApplication.class, args);
    }

}
