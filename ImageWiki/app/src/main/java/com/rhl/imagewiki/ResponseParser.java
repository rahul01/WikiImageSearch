package com.rhl.imagewiki;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Response parser for parsing network response
 */
public class ResponseParser {

    /**
     * will parse network response
     *
     * @param response network response
     * @return page list
     */
    public static List<Page> parse(JSONObject response) {
        List<Page> pages = new ArrayList<>();

        try {
            JSONObject jsonObject = response.getJSONObject("query").getJSONObject("pages");

            Iterator iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                JSONObject pageObject = jsonObject.getJSONObject(key);
                String url = "";
                if (pageObject.has("thumbnail")) {//extract image url
                    url = pageObject.getJSONObject("thumbnail").getString("source");
                }
                Page page = new Page(pageObject.getString("title"), url);
                pages.add(page);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pages;
    }
}
