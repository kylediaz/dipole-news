package com.kylediaz.fbu.dipole_news.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.kylediaz.fbu.dipole_news.utils.StandardDateFormats;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Event implements Parcelable {

    public static final Comparator<Event> MOST_ARTICLES_COMPARATOR =
            (o1, o2) -> o2.articles.length - o1.articles.length;

    public static final Comparator<Event> LATEST_UPDATE_COMPARATOR =
            (o1, o2) -> o2.lastUpdatedAt.compareTo(o1.lastUpdatedAt);

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    private int id;

    private Date createdAt;
    private Date lastUpdatedAt;

    private String title;

    private int[] articles;

    // Event should only be instantiated through static function fromJSON
    private Event() {
    }

    private Event(@NotNull Parcel parcel) {
        id = parcel.readInt();
        createdAt = (Date) parcel.readSerializable();
        lastUpdatedAt = (Date) parcel.readSerializable();
        title = parcel.readString();
        int articlesArraySize = parcel.readInt();
        articles = new int[articlesArraySize];
        parcel.readIntArray(articles);
    }

    public static List<Event> fromJSONArray(JSONArray jsonArray) throws JSONException {
        List<Event> output = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            output.add(Event.fromJSON(jsonArray.getJSONObject(i)));
        }
        return output;
    }

    /**
     * @param jsonObject JSONObject with entries id, created_at, published_at, title,
     *                   title_manually_given, and articles
     * @throws JSONException jsonObject is missing ne of the required entries or created_at does
     *                       not contain a date (are not in ISO date format)
     */
    public static Event fromJSON(JSONObject jsonObject) throws JSONException {
        Event output = new Event();
        output.id = jsonObject.getInt("id");
        try {
            SimpleDateFormat ISOFormat = StandardDateFormats.getISODateFormat();
            output.createdAt = ISOFormat.parse(jsonObject.getString("created_at"));
            output.lastUpdatedAt = ISOFormat.parse(jsonObject.getString("last_updated_at"));
        } catch (ParseException e) {
            throw new JSONException("Date was not in the correct format", e);
        }
        output.title = jsonObject.getString("title");
        JSONArray articlesJsonArray = jsonObject.getJSONArray("articles");
        if (articlesJsonArray != null) {
            output.articles = JSONArrayToIntegerList(articlesJsonArray);
        }
        return output;
    }

    /**
     * @param jsonArray JSONArray where all elements are parsable by .getInt()
     * @throws JSONException an element was not parsable by .getInt()
     */
    private static int[] JSONArrayToIntegerList(JSONArray jsonArray) throws JSONException {
        int[] output = new int[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            output[i] = jsonArray.getInt(i);
        }
        return output;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeSerializable(createdAt);
        dest.writeSerializable(lastUpdatedAt);
        dest.writeString(title);
        dest.writeInt(articles.length);
        dest.writeIntArray(articles);
    }

    public int getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public String getTitle() {
        return title;
    }

    public int[] getArticles() {
        return articles;
    }

}
