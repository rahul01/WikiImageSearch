package com.rhl.imagewiki;

/**
 * model for single search result
 */
public class Page {
    public String title;
    public String imageUrl;

    /**
     * constructor
     *
     * @param title    page title
     * @param imageUrl page image
     */
    public Page(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }
}
