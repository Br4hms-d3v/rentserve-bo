package be.brahms.TFE_RentServe.exceptions.favor;

public class FavorNotFoundException extends FavourException {
    public FavorNotFoundException(String message) {
        super(message);
    }

    public FavorNotFoundException() {
        super("Le service n'a pas été trouvé.");
    }
}
