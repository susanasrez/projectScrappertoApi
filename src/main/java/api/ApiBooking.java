package api;

import scraper.Scraper;
import spark.Spark;

import static spark.Spark.*;

public class ApiBooking implements Api {

    private static Scraper bookingScraper;
    public ApiBooking(Scraper bookingScraper) {
        ApiBooking.bookingScraper = bookingScraper;
        initializeRoutes();
    }

    public void initializeRoutes() {

        port(8080);

        Spark.get("/v1/hotels", "application/json", (req, res) -> {
            res.type("application/json");
            return bookingScraper.nameHotels();
        });
        Spark.get("/v1/hotels/:name", (req, res) -> {
            res.type("application/json");
            String name = req.params("name");
            return bookingScraper.hotelLocationScraper(name);
        });

        Spark.get("/v1/hotels/:name/services", "application/json", (req, res) -> {
            res.type("application/json");
            String name = req.params("name");
            return bookingScraper.hotelServicesScraper(name);
        });

        Spark.get("/v1/hotels/:name/ratings","application/json",  (req, res) -> {
            res.type("application/json");
            String name = req.params("name");
            return bookingScraper.hotelRatingsScraper(name);
        });

        Spark.get("/v1/hotels/:name/comments","application/json",  (req, res) -> {
            res.type("application/json");
            int page = req.queryParams("page") == null ? 1 : Integer.parseInt(req.queryParams("page"));
            String name = req.params("name");
            return bookingScraper.hotelCommentsScraper(name, page);
        });
    }
}
