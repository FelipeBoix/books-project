package edu.upc.eetac.dsa.FelipeBoix.books.api.model;

import java.util.List;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.InjectLinks;

import edu.upc.eetac.dsa.FelipeBoix.books.api.BooksResource;
import edu.upc.eetac.dsa.FelipeBoix.books.api.BooksRootAPIResource;
import edu.upc.eetac.dsa.FelipeBoix.books.api.MediaType;

public class BookRootAPI {
	@InjectLinks({
		@InjectLink(resource = BooksRootAPIResource.class, style = Style.ABSOLUTE, rel = "self bookmark home", title = "BooksRootAPI", method = "getRootAPI"),
		@InjectLink(resource = BooksResource.class, style = Style.ABSOLUTE, rel = "stings", title = "Latest stings", type = MediaType.BOOK_API_BOOKS_COLLECTION),
		@InjectLink(resource = BooksResource.class, style = Style.ABSOLUTE, rel = "create-stings", title = "Latest stings", type = MediaType.BOOKS_API_BOOK) })
	
	private List<Link> links; //atributo con getters y setters
	 
	public List<Link> getLinks() {
		return links;
	}
 
	public void setLinks(List<Link> links) {
		this.links = links;
	}
}
