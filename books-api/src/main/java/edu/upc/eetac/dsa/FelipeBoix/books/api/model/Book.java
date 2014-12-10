package edu.upc.eetac.dsa.FelipeBoix.books.api.model;

import java.sql.Date;
import org.glassfish.jersey.linking.Binding;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;

import edu.upc.eetac.dsa.FelipeBoix.books.api.BooksResource;
import edu.upc.eetac.dsa.FelipeBoix.books.api.BooksRootAPIResource;
import edu.upc.eetac.dsa.FelipeBoix.books.api.MediaType;

public class Book {
	@InjectLinks({
		@InjectLink(resource = BooksResource.class, style = Style.ABSOLUTE, rel = "stings", title = "Latest stings", type = MediaType.BOOK_API_BOOKS_COLLECTION),
		@InjectLink(resource = BooksResource.class, style = Style.ABSOLUTE, rel = "self edit", title = "Sting", type = MediaType.BOOKS_API_BOOK, method = "getBooks", bindings = @Binding(name = "stingid", value = "${instance.stingid}")) })
	
	private int bookid;
	private String tittle;
	private String author;
	private String language;
	private String edition;
	private Date editiondate;
	private Date dateprint;
	private String editorial;
	List<Reviews> reviews= new ArrayList<Reviews>();
	
	private List<Link> links = new ArrayList<Link>();
	public int getBookid() {
		return bookid;
	}
	public void setBookid(int bookid) {
		this.bookid = bookid;
	}
	public String getTittle() {
		return tittle;
	}
	public void setTittle(String tittle) {
		this.tittle = tittle;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getEdition() {
		return edition;
	}
	public void setEdition(String edition) {
		this.edition = edition;
	}
	public Date getEditiondate() {
		return editiondate;
	}
	public void setEditiondate(Date editiondate) {
		this.editiondate = editiondate;
	}
	public Date getDateprint() {
		return dateprint;
	}
	public void setDateprint(Date dateprint) {
		this.dateprint = dateprint;
	}
	public String getEditorial() {
		return editorial;
	}
	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}
	public List<Reviews> getReviews() {
		return reviews;
	}
	public void addReviews(Reviews review) {
		reviews.add(review);
	}
	public void setReviews(List<Reviews> review) {
		this.reviews= review;
	}
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	

}
