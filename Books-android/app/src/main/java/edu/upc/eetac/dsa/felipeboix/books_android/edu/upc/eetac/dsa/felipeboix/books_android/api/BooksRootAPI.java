package edu.upc.eetac.dsa.felipeboix.books_android.edu.upc.eetac.dsa.felipeboix.books_android.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Felipe on 11/12/2014.
 */
public class BooksRootAPI {
    private Map<String, Link> links;

    public BooksRootAPI() {
        links = new HashMap<String, Link>();
    }

    public Map<String, Link> getLinks() {
        return links;
    }

}
