package edu.upc.eetac.dsa.FelipeBoix.books.api.model;
import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
 
public class BookCollection {
	private List<Book> books;
 
	public BookCollection() {
		super();
		books = new ArrayList<>();
	}
 
	public List<Book> getBooks() {
		return books;
	}
 
	public void setStings(List<Book> books) {
		this.books = books;
	}
 
	public void addSting(Book book) {
		books.add(book);
	}
}
