package scraper;

import model.Hotel;
import model.Review;
import com.google.gson.Gson;

import java.util.List;

public class JsonTransformer{

    public JsonTransformer(){

    }

    public static String toJsonNames(List<String> hotelsNames){
        Gson gson = new Gson();
        return gson.toJson(hotelsNames);
    }

    public static String toJsonHotel(Hotel hotel){
        Gson gson = new Gson();
        return gson.toJson(hotel);
    }

    public static String toJsonComments(List<Review> reviewList){
        Gson gson = new Gson();
        return gson.toJson(reviewList);
    }


}
