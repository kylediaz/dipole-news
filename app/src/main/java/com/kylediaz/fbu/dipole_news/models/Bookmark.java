package com.kylediaz.fbu.dipole_news.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Parse database object for bookmarks created by users
 */
@ParseClassName("Bookmark")
public class Bookmark extends ParseObject {

    private final int userID;
    private final int eventID;

    /**
     * DO NOT USE THIS. A default constructor is required for Parse, but is never actually used.
     * Do not use this in your code.
     */
    public Bookmark() {
        this(-1, -1);
    }

    /**
     * @param userID  The ID of the user who created the bookmark (ie currently signed in user)
     * @param eventID The ID of the event to be bookmarked
     */
    public Bookmark(int userID, int eventID) {
        this.userID = userID;
        this.eventID = eventID;
    }

    public int getUserID() {
        return userID;
    }

    public int getEventID() {
        return eventID;
    }

}
