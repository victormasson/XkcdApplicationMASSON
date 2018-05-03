package masson.diiage.org.xkcdapplicationmasson.models;

import android.text.format.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Comic {
    public static class ComicBuilder {
        public static Comic builder(JSONObject jsonObject) throws JSONException {
            Comic comic = new Comic();

            comic.setAlt(jsonObject.getString("alt"));
            comic.setDay(jsonObject.getString("day"));
            comic.setImg(jsonObject.getString("img"));
            comic.setLink(jsonObject.getString("link"));
            comic.setMonth(jsonObject.getString("month"));
            comic.setNews(jsonObject.getString("news"));
            comic.setSafe_title(jsonObject.getString("safe_title"));
            comic.setTitle(jsonObject.getString("title"));
            comic.setTranscript(jsonObject.getString("transcript"));
            comic.setYear(jsonObject.getString("year"));
            comic.setNum(jsonObject.getInt("num"));

            return comic;
        }
    }

    private String alt;
    private String day;
    private String img;
    private String link;
    private String month;
    private String news;
    private String safe_title;
    private String title;
    private String transcript;
    private String year;
    private int num; // Id du comic

    public Comic() {
    }

    public Comic(String alt, String day, String img, String link, String month, String news, String safe_title, String title, String transcript, String year, int num) {
        this.alt = alt;
        this.day = day;
        this.img = img;
        this.link = link;
        this.month = month;
        this.news = news;
        this.safe_title = safe_title;
        this.title = title;
        this.transcript = transcript;
        this.year = year;
        this.num = num;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getSafe_title() {
        return safe_title;
    }

    public void setSafe_title(String safe_title) {
        this.safe_title = safe_title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTranscript() {
        return transcript;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Date getDate() throws ParseException {
        if ((this.getYear() != null || !this.getYear().equals(""))
                && (this.getMonth() != null || !this.getMonth().equals(""))
                && (this.getDay() != null || !this.getDay().equals(""))) {
            int day = Integer.parseInt(this.getDay());
            int month = Integer.parseInt(this.getMonth());
            int year = Integer.parseInt(this.getYear());

            return new GregorianCalendar(year, month, day).getTime();
        }

        Calendar today = Calendar.getInstance();
        return today.getTime();
    }
}
