package edu.upc.eetac.dsa.FelipeBoix.books.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;

import edu.upc.eetac.dsa.FelipeBoix.books.api.model.Book;
import edu.upc.eetac.dsa.FelipeBoix.books.api.model.BookCollection;



@Path("/books")
public class BooksResource {
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	
	private String GET_BOOKS_QUERY = "select * from books";
	
	@GET
	@Produces(MediaType.BOOK_API_BOOKS_COLLECTION)
	public BookCollection getBooks() {
		
		System.out.println("no conectados a la BD"); 
		BookCollection books = new BookCollection();
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("conectados a la BD"); 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_BOOKS_QUERY);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Book book = new Book();
				book.setBookid(rs.getInt("bookid"));
				book.setTittle(rs.getString("tittle"));
			    book.setAuthor(rs.getString("author"));
				book.setLanguage(rs.getString("language"));
				book.setEdition(rs.getString("edition"));
				book.setEditiondate(rs.getDate("editiondate"));
				book.setDateprint(rs.getDate("printdate"));
				
				books.add(book);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
	 
		return books;
	}
	
	private String GET_BOOKS_AUTHOR = "select * from books where author like ?";
	private String GET_BOOKS_TITTLE = " select * from books where tittle like ?";
	private String GET_BOOKS_AUTHOR_AND_TITTLE = " select * from books where author like ? and tittle like ?";
	
	@GET
	@Path("/search")
	@Produces(MediaType.BOOK_API_BOOKS_COLLECTION)
	public BookCollection SearchBooks(@QueryParam("tittle") String tittle,
			@QueryParam("author") String author) {
		  BookCollection books = new BookCollection();
			 
				Connection conn = null;
				try {
					conn = ds.getConnection();// Conectamos con la base de datos
				} catch (SQLException e) {
					throw new ServerErrorException("Could not connect to the databes", 
							Response.Status.SERVICE_UNAVAILABLE);
				}
				System.out.println(" datos: " + author);
				System.out.println(" datos: " +tittle);
				PreparedStatement stmt = null;
				
				try {
			    	
			    	if (tittle != null && author != null)
			        {
					stmt = conn.prepareStatement(GET_BOOKS_AUTHOR_AND_TITTLE);
					stmt.setString(1, tittle);
					stmt.setString(2, author);
					
			        }
			    	else if(tittle != null && author == null)
			    	{
			        stmt = conn.prepareStatement(GET_BOOKS_TITTLE);
			        stmt.setString(1, tittle);
			    	}
			    	else if ( author != null && tittle == null)
			    	{
			    		stmt = conn.prepareStatement(GET_BOOKS_AUTHOR);
			    		stmt.setString(1, author);
			    	}
			    	
			    	
			    	ResultSet rs = stmt.executeQuery();
					while (rs.next()) {
						Book book = new Book();
						book.setBookid(rs.getInt("bookid"));
						//book.setId(rs.getInt("id"));
						book.setTittle(rs.getString("tittle"));
						book.setAuthor(rs.getString("author"));
						book.setEdition(rs.getString("edition"));
						book.setEditorial(rs.getString("editorial"));
						book.setEditiondate(rs.getDate("editiondate"));
						book.setDateprint(rs.getDate("dateprint"));
						book.setLanguage(rs.getString("lengua"));
		                
						
						
						books.add(book);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						if (stmt != null)
							stmt.close();
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			 
				return books;
			}
}