package be.brahms.TFE_RentServe.exceptions.category;


public class CategoryNotFoundException extends CategoryException {
    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException() {
        super("La categorie na pas été retrouvé");
    }
}
