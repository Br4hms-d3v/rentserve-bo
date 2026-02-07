package be.brahms.TFE_RentServe.exceptions.category;

public class CategoryExistingException extends RuntimeException {
    public CategoryExistingException(String message) {
        super(message);
    }

    public CategoryExistingException() {
        super("La categorie existe déjà");
    }
}
