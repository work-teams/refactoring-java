
import java.util.HashMap;

public class RentalInfo {

    // Constantes para los códigos de tipo de película
    private static final String REGULAR = "regular";
    private static final String NEW_RELEASE = "new";
    private static final String CHILDRENS = "childrens";

    public String statement(Customer customer) {
        HashMap<String, Movie> movies = createMoviesMap();

        double totalAmount = 0;
        int frequentEnterPoints = 0;

        // Utilizar StringBuilder para construir el resultado
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("Rental Record for ").append(customer.getName()).append("\n");

        for (MovieRental rental : customer.getRentals()) {
            double thisAmount = calculateAmount(rental, movies);
            frequentEnterPoints += calculateFrequentEnterPoints(rental, movies);

            // Utilizar StringBuilder para construir la línea de resultados
            resultBuilder.append("\t")
                    .append(movies.get(rental.getMovieId()).getTitle())
                    .append("\t")
                    .append(thisAmount)
                    .append("\n");

            totalAmount += thisAmount;
        }

        // Utilizar StringBuilder para construir las líneas de resumen
        resultBuilder.append("Amount owed is ").append(totalAmount).append("\n");
        resultBuilder.append("You earned ").append(frequentEnterPoints).append(" frequent points\n");

        return resultBuilder.toString();
    }

    // Crear el mapa de películas
    private HashMap<String, Movie> createMoviesMap() {
        HashMap<String, Movie> movies = new HashMap<>();
        movies.put("F001", new Movie("You've Got Mail", REGULAR));
        movies.put("F002", new Movie("Matrix", REGULAR));
        movies.put("F003", new Movie("Cars", CHILDRENS));
        movies.put("F004", new Movie("Fast & Furious X", NEW_RELEASE));
        return movies;
    }

    // Calcular el monto de la película según su tipo y días de alquiler
    private double calculateAmount(MovieRental rental, HashMap<String, Movie> movies) {
        Movie movie = movies.get(rental.getMovieId());
        double amount = 0;
        String movieCode = movie.getCode();

        switch (movieCode) {
            case REGULAR -> {
                amount = 2;
                if (rental.getDays() > 2) {
                    amount += (rental.getDays() - 2) * 1.5;
                }
            }
            case NEW_RELEASE ->
                amount = rental.getDays() * 3;
            case CHILDRENS -> {
                amount = 1.5;
                if (rental.getDays() > 3) {
                    amount += (rental.getDays() - 3) * 1.5;
                }
            }
            default -> {
            }
        }
        return amount;
    }

    // Calcular los puntos frecuentes según el tipo de película y días de alquiler
    private int calculateFrequentEnterPoints(MovieRental rental, HashMap<String, Movie> movies) {
        Movie movie = movies.get(rental.getMovieId());
        int frequentEnterPoints = 1;
        String movieCode = movie.getCode();

        if (movieCode.equals(NEW_RELEASE) && rental.getDays() > 2) {
            frequentEnterPoints++;
        }
        return frequentEnterPoints;
    }
}
