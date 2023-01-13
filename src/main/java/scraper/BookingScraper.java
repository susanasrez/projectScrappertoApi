package scraper;

import model.Hotel;
import model.Review;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static scraper.Connexion.getHtmlDocument;
import static scraper.Connexion.getStatusConnectionCode;


public class BookingScraper implements Scraper {

    public BookingScraper(){

    }

    public String nameHotels() {
        String url = "https://www.booking.com/searchresults.es.html?label=es-JCB2UqznXtCO_RDP_nj5CAS410545262609%3Apl%3Ata%3Ap1%3Ap22.563.000%3Aac%3Aap%3Aneg%3Afi%3Atikwd-65526620%3Alp9047034%3Ali%3Adec%3Adm%3Appccp%3DUmFuZG9tSVYkc2RlIyh9Ye8F2ouj63ytkBtrYs5TAfs&aid=376371&ss=Gran+Canaria%2C+Espa%C3%B1a&ssne=Islas+Canarias&ssne_untouched=Islas+Canarias&lang=es&src=index&dest_id=754&dest_type=region&ac_position=0&ac_click_type=b&ac_langcode=es&ac_suggestion_list_length=5&search_selected=true&search_pageview_id=f5f479603f410111&ac_meta=GhBmNWY0Nzk2MDNmNDEwMTExIAAoATICZXM6CUdyYW4gQ2FuYUAASgBQAA%3D%3D&checkin=2023-01-26&checkout=2023-01-29&group_adults=2&no_rooms=1&group_children=0&sb_travel_purpose=leisure&nflt=ht_id%3D204&offset=";
        String path;
        List<String> hotelsNames = new ArrayList<>();
        for (int i = 0; i < 175; i += 25) {
            path = url + i;
            if (getStatusConnectionCode(path) == 200) {
                Document document = getHtmlDocument(path);
                Elements names = document.select("div.fcab3ed991.a23c043802");
                for (Element elem : names){
                    hotelsNames.add(elem.text());
                }
            }
        }
        return JsonTransformer.toJsonNames(hotelsNames);
    }

    public String hotelLocationScraper(String name){
        String url = "https://www.booking.com/hotel/es/" + name + ".es.html";
        Hotel hotel = new Hotel();
        if(getStatusConnectionCode(url) == 200){
            Document document = getHtmlDocument(url);
            hotel.setName( document.select("h2.d2fee87262.pp-header__title").text());
            hotel.setLocation(document.select("span.hp_address_subtitle.js-hp_address_subtitle.jq_tooltip").text());
            hotel.setUrl(url + "#map_opened-hotel_header");
        }
        return JsonTransformer.toJsonHotel(hotel);
    }

    public String hotelServicesScraper(String name){
        String url = "https://www.booking.com/hotel/es/" + name + ".es.html";
        Hotel hotel = new Hotel();
        Map<String, String> hotelServices = new HashMap<>();
        if(getStatusConnectionCode(url) == 200){
            Document document = getHtmlDocument(url);
            hotel.setName( document.select("h2.d2fee87262.pp-header__title").text());
            Elements services = document.select("div.hotel-facilities-group");
            for(Element elements: services){
                ArrayList<String> servicesPoints = new ArrayList<>();
                Elements elementsServices = elements.select("div.bui-list__description");
                for(Element element: elementsServices){
                    servicesPoints.add(element.text());
                }
                hotelServices.put(elements.select("div.bui-title__text.hotel-facilities-group__title-text").text(),
                        servicesPoints.toString());
            }
        }
        hotel.setServices(hotelServices);
        return JsonTransformer.toJsonHotel(hotel);
    }

    public String hotelRatingsScraper(String name){
        String url = "https://www.booking.com/hotel/es/" + name + ".es.html";
        Map<String, String> hotelRatings = new HashMap<>();
        Hotel hotel = new Hotel();
        if(getStatusConnectionCode(url) == 200){
            Document document = getHtmlDocument(url);
            hotel.setName( document.select("h2.d2fee87262.pp-header__title").text());
            Elements ratings = document.select("div.d46673fe81");
            Elements ratingsTitle = ratings.select("span.d6d4671780");
            Elements number = ratings.select("div.ee746850b6.b8eef6afe1");

            for(int i = 0; i < 8; i++) {
                hotelRatings.put(ratingsTitle.get(i).text(), number.get(i).text());
            }
        }
        hotel.setRatings(hotelRatings);
        return JsonTransformer.toJsonHotel(hotel);
    }

    public String hotelCommentsScraper(String name, int page){
        String url = "https://www.booking.com/reviews/es/hotel/" + name + ".es.html?page=" + page;
        if(getStatusConnectionCode(url) == 200){
            Document document = getHtmlDocument(url);
            List<Review> reviewList = new ArrayList<>();
            Elements reviews = document.select("li.review_item");
            for(Element review: reviews){
                String title = review.getElementsByClass("review_item_header_content").text();
                String date = review.getElementsByClass("review_item_date").text();
                String score = review.getElementsByClass("review-score-badge").text();
                reviewList.add(new Review(title,date,score, getReviewsText(review, "review_pos"), getReviewsText(review, "review_neg")));
            }
            return JsonTransformer.toJsonComments(reviewList);
        }
        else{
            return null;
        }
        
    }

    private List<String> getReviewsText(Element review, String type){
        List<String> reviewList = new ArrayList<>();
        Elements reviews = review.select("div.review_item_review_content");
        for(Element reviewtext: reviews){
            reviewList.add(reviewtext.getElementsByClass(type).select("span").text());
        }
        return reviewList;
    }


}
