package com.kylediaz.fbu.dipole_news.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.kylediaz.fbu.dipole_news.utils.StandardDateFormats;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Article implements Parcelable {

    private int id;

    private Date publishedAt;
    private Date addedAt;

    private String title;
    private String author;
    private String source;

    private String URL;
    private String imageURL;

    private String description;
    private String content;

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public static List<Article> fromJSONArray(JSONArray jsonArray) throws JSONException {
        List<Article> output = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i ++) {
            output.add(Article.fromJSON(jsonArray.getJSONObject(i)));
        }
        return output;
    }

    /**
     * @param jsonObject JSON object with required entries id, published_at, added_at, title,
     *                   source, author, url, image_url, description. The entry "content" is
     *                   optional so articles can be lazy-loaded from the server.
     * @throws JSONException missing entry in jsonObject or published_at or added_at were not in
     *                       the correct format
     */
    public static Article fromJSON(JSONObject jsonObject) throws JSONException {
        Article output = new Article();
        output.id = jsonObject.getInt("id");
        try {
            SimpleDateFormat ISOFormat = StandardDateFormats.getISODateFormat();
            output.publishedAt = ISOFormat.parse(jsonObject.getString("publishedAt"));
            output.addedAt = ISOFormat.parse(jsonObject.getString("addedAt"));
        } catch (ParseException e) {
            throw new JSONException("Date was not in the correct format", e);
        }
        output.title = jsonObject.getString("title");
        output.source = jsonObject.getJSONObject("source").getString("name");
        output.author = jsonObject.getString("author");
        output.URL = jsonObject.getString("url");
        output.imageURL = jsonObject.getString("urlToImage");
        output.description = jsonObject.getString("description");
        output.content = jsonObject.optString("content");
        return output;
    }

    public Article() {}

    private Article(Parcel parcel) {
        id = parcel.readInt();
        publishedAt = (Date) parcel.readSerializable();
        addedAt = (Date) parcel.readSerializable();
        title = parcel.readString();
        author = parcel.readString();
        source = parcel.readString();
        URL = parcel.readString();
        imageURL = parcel.readString();
        description = parcel.readString();
        boolean contentIsNull = parcel.readBoolean();
        if (!contentIsNull) {
            content = parcel.readString();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeSerializable(publishedAt);
        dest.writeSerializable(addedAt);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(source);
        dest.writeString(URL);
        dest.writeString(imageURL);
        dest.writeString(description);
        dest.writeBoolean(content == null);
        if (content != null) {
            dest.writeString(content);
        }
    }

    public int getId() {
        return id;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public Date getAddedAt() {
        return addedAt;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getSource() {
        return source;
    }

    public String getURL() {
        return URL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getDescription() {
        return description;
    }

    public @Nullable String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

