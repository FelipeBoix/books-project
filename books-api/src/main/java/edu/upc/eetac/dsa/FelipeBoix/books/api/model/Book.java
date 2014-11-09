package edu.upc.eetac.dsa.FelipeBoix.books.api.model;

import java.sql.Date;

public class Book {
	
	private int bookid;
	private String tittle;
	private String author;
	private String language;
	private String edition;
	private Date editiondate;
	private Date dateprint;
	private String editorial;
	
	
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
	

}
