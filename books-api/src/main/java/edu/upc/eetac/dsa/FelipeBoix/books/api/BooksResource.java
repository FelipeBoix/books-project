package edu.upc.eetac.dsa.FelipeBoix.books.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
//import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import edu.upc.eetac.dsa.FelipeBoix.books.api.model.Author;
import edu.upc.eetac.dsa.FelipeBoix.books.api.model.Book;
import edu.upc.eetac.dsa.FelipeBoix.books.api.model.BookCollection;



@Path("/books")
public class BooksResource {
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	@Context
	private SecurityContext security;
	
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
	
	// busqueda para un titulo, autor o titulo y autor
	private String GET_BOOKS_AUTHOR = "select * from books where author like;?";
	private String GET_BOOKS_TITTLE = " select * from books where tittle like;?";
	private String GET_BOOKS_AUTHOR_AND_TITTLE = " select * from books where author like? and tittle like ?";
	
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
	//busqueda de un libro por una id
	private String GET_BOOK_ID = "select * from books where bookid = ?";
	@GET
	@Path("/{bookid}")
	@Produces(MediaType.BOOKS_API_BOOK)
	public Book getBook(@PathParam("bookid") int id) {
		Book book = new Book();
		Connection conn= null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		
		try{
			stmt = conn.prepareStatement(GET_BOOK_ID);
			stmt.setInt(1, Integer.valueOf(id));
			System.out.println(stmt);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				book.setBookid(rs.getInt("bookid"));
				book.setTittle(rs.getString("tittle"));
				book.setAuthor(rs.getString("author"));
				book.setEdition(rs.getString("edition"));
				book.setEditorial(rs.getString("editorial"));
				book.setEditiondate(rs.getDate("editiondate"));
				book.setDateprint(rs.getDate("dateprint"));
				book.setLanguage(rs.getString("lengua"));
			}
				
				else{
					throw new NotFoundException("There's no libro with bookid =" + id);
				}
		}
			catch (SQLException e) {
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
			
			  return book;
	}
	
	private String INSERT_AUTHOR = "insert into author (username) value ?";
	@POST
	@Path("/author)")
	@Produces(MediaType.BOOKS_API_BOOK)
	@Consumes(MediaType.BOOKS_API_BOOK)
	
		public Author CreateAuthor(Author author){
		
		if (!security.isUserInRole("admin"))// Solo lo realiza el admin
			throw new ForbiddenException("You are not allowed to create a autor");
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(INSERT_AUTHOR,Statement.RETURN_GENERATED_KEYS);
					
	 
			stmt.setString(1, author.getUsername());
			
			System.out.println(author.getUsername());
			System.out.println(stmt);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			
		  	if (rs.next()) {
				
				int idauthor = rs.getInt(1);
				//autor = getLibroFromDatabase(Integer.toString(idautor));
				System.out.println("autor creado");
				/*autor.setIdautor(rs.getInt("id"));
				autor.setName(rs.getString("titulo"));*/
				
			 } 
			else {
				throw new BadRequestException("No se le permite crear al autor");
			}
	           
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		
		return author;
		
		}
	private String DELETE_AUTHOR = "DELETE author (username) value ?";
	@DELETE
	@Path("/autor/{idautor}")
	
	public void DeleteAuthor (@PathParam("id") String id){
		//solo puede el admin
		if (!security.isUserInRole("admin"))
			throw new ForbiddenException("You are not allowed to delete a book");
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(DELETE_AUTHOR);
		    stmt.setInt(1, Integer.valueOf(id));
			System.out.println(stmt);
			
			int rows = stmt.executeUpdate();
			if (rows == 0)
			{
				throw new NotFoundException("No hay un autor con este nombre"+ id);// Updating inexistent sting
			}
			else
			{
				System.out.println("Autor eliminado");
			}
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				    conn.close();
			} catch (SQLException e) {
			}
		}
		
	}
	

	
	
	
}