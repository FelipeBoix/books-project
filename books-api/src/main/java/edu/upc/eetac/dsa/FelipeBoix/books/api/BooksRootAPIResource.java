package edu.upc.eetac.dsa.FelipeBoix.books.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import edu.upc.eetac.dsa.FelipeBoix.books.api.model.BookRootAPI;

@Path("/")
public class BooksRootAPIResource {
	@GET
	public BookRootAPI getRootAPI() {
		BookRootAPI api = new BookRootAPI();
		return api;
	
	}
}