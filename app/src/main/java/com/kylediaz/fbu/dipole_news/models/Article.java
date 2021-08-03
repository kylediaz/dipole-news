package com.kylediaz.fbu.dipole_news.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.kylediaz.fbu.dipole_news.utils.StandardDateFormats;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = "article")
public class Article implements Parcelable {

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

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "published_at")
    private Date publishedAt;
    @ColumnInfo(name = "added_at")
    private Date addedAt;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "author")
    private String author;
    @ColumnInfo(name = "publisher")
    private String publisher;
    @ColumnInfo(name = "url")
    private String URL;
    @ColumnInfo(name = "image_url")
    private String imageURL;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "content")
    private String content;

    /**
     * Constructor is public for Room, but Articles should only be constructed through fromJSON
     */
    public Article() {
    }

    public Article(int id, Date publishedAt, Date addedAt, String title, String author,
                   String publisher, String URL, String imageURL, String description,
                   String content) {
        this.id = id;
        this.publishedAt = publishedAt;
        this.addedAt = addedAt;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.URL = URL;
        this.imageURL = imageURL;
        this.description = description;
        this.content = content;
    }

    private Article(Parcel parcel) {
        id = parcel.readInt();
        publishedAt = (Date) parcel.readSerializable();
        addedAt = (Date) parcel.readSerializable();
        title = parcel.readString();
        author = parcel.readString();
        publisher = parcel.readString();
        URL = parcel.readString();
        imageURL = parcel.readString();
        description = parcel.readString();
        boolean contentIsNull = parcel.readInt() == 1;
        if (!contentIsNull) {
            content = parcel.readString();
        }
    }

    public static List<Article> fromJSONArray(JSONArray jsonArray) throws JSONException {
        List<Article> output = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
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
            output.publishedAt = ISOFormat.parse(jsonObject.getString("published_at"));
            output.addedAt = ISOFormat.parse(jsonObject.getString("added_at"));
        } catch (ParseException e) {
            throw new JSONException("Date was not in the correct format", e);
        }
        output.title = jsonObject.getString("title");
        output.publisher = jsonObject.getString("publisher");
        output.author = jsonObject.getString("author");
        output.URL = jsonObject.getString("url");
        output.imageURL = jsonObject.getString("image_url");
        output.description = jsonObject.getString("description");
        output.content = jsonObject.optString("content");
        return output;
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
        dest.writeString(publisher);
        dest.writeString(URL);
        dest.writeString(imageURL);
        dest.writeString(description);
        dest.writeInt(content == null ? 1 : 0);
        if (content != null) {
            dest.writeString(content);
        }
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }
    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Date getAddedAt() {
        return addedAt;
    }
    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {

    }

    public String getURL() {
        return URL;
    }
    public void setURL(String url) {
        this.URL = URL;
    }

    public String getImageURL() {
        return imageURL;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String setDescription) {
        this.description = description;
    }


    public @Nullable
    String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
