package edu.upc.eetac.dsa.FelipeBoix.books.api.model;
//import java.awt.print.Book;
import java.util.ArrayList;
import org.glassfish.jersey.linking.Binding;
import java.util.List;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;

import edu.upc.eetac.dsa.FelipeBoix.books.api.BooksResource;
import edu.upc.eetac.dsa.FelipeBoix.books.api.BooksRootAPIResource;
import edu.upc.eetac.dsa.FelipeBoix.books.api.MediaType;
 
public class BookCollection {
	 @InjectLinks({
		  @InjectLink(resource = BooksResource.class, style = Style.ABSOLUTE, rel = "self", title = "libros", type = MediaType.BOOK_API_BOOKS_COLLECTION),
		  @InjectLink(resource = BooksResource.class, style = Style.ABSOLUTE, rel = "libro", title = "libro", type = MediaType.BOOKS_API_BOOK)
	  }) 
	private List<Book> books;
 
	public BookCollection() {
		super();
		books = new ArrayList<>();
	}
 
	public List<Book> getBooks() {
		return books;
	}
 
	public void setBooks(List<Book> books) {
		this.books = books;
	}
 
	public void add(Book book) {
		books.add(book);
	}
}
