package model;

import java.util.List;

public class Review {
    String title;
    String date;
    String mark;
    List<String> reviewListPos;
    List<String> reviewListNeg;

    public Review(String title, String date, String mark, List<String> reviewListPos, List<String> reviewListNeg) {
        this.title = title;
        this.date = date;
        this.mark = mark;
        this.reviewListPos = reviewListPos;
        this.reviewListNeg = reviewListNeg;
    }
}
