package edu.upc.eetac.dsa.FelipeBoix.books.api.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;

public class Book {
	
	private int bookid;
	private String tittle;
	private String author;
	private String language;
	private String edition;
	private Date editiondate;
	private Date dateprint;
	private String editorial;
	
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
	/*public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public void add(Link book) {
		links.add(book);
	}*/
	

}
