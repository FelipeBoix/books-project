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
import edu.upc.eetac.dsa.FelipeBoix.books.api.model.Reviews;



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
	private String GET_REVIEWS_BY_ID = " SELECT * from reviews where bookid = ?;";
	private Book getLibroFromDatabase(String bookid) {
		Book book = new Book();

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		// PreparedStatement stmt2 = null;
		try {
			// cojemos libros
			stmt = conn.prepareStatement(GET_BOOK_ID);
			stmt.setInt(1, Integer.valueOf(bookid));
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				book.setBookid(rs.getInt("bookid"));
				book.setTittle(rs.getString("tittlo"));
				book.setAuthor(rs.getString("author"));
				book.setLanguage(rs.getString("language"));
				book.setEdition(rs.getString("edition"));
				book.setEditiondate(rs.getDate("editiondate"));
				book.setDateprint(rs.getDate("dateprint"));
				book.setEditorial(rs.getString("editorial"));
			} else {
				throw new NotFoundException(
						"No se encuentra ningun libro con ID =" + bookid);
			}
			/*
			 * //cojemos resenas stmt2=conn.prepareStatement(GET_RESENA_BY_ID);
			 * stmt2.setInt(1, Integer.valueOf(id)); ResultSet rs2 =
			 * stmt2.executeQuery();
			 * 
			 * while(rs2.next()) { Resena resena = new Resena();
			 * resena.setIdres(rs.getInt("idres"));
			 * resena.setIdlibros(rs.getInt("idlibro"));
			 * resena.setUsername(rs.getString("username"));
			 * resena.setFecha(rs.getDate("fecha"));
			 * resena.setTexto(rs.getString("texto"));
			 * 
			 * }
			 */

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

		return book;
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
		System.out.println(book);
			  return book;
	}
	private Book getBookFromDatabase(String bookid) {
		Book book = new Book();

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_BOOK_ID);
			stmt.setInt(1, Integer.valueOf(bookid));
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				book.setBookid(rs.getInt("bookid"));
				book.setTittle(rs.getString("tittle"));
				book.setAuthor(rs.getString("author"));
				book.setLanguage(rs.getString("language"));
				book.setEdition(rs.getString("edition"));
				book.setEditiondate(rs.getDate("edtiondate"));
				book.setDateprint(rs.getDate("printdate"));
				book.setEditorial(rs.getString("editorial"));
			} else {
				throw new NotFoundException("No se encuentra ningun libro con ID ="
						+ bookid);
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

		return book;
	}
	
	private String INSERT_AUTHOR = "insert into author (username) values (?);";

	@POST
	@Path("/author")
	@Consumes(MediaType.BOOKS_API_BOOK)
	@Produces(MediaType.BOOKS_API_BOOK)

	public Author CreateAutor(Author author)
	{
		//solo puede el admin
		if (!security.isUserInRole("admin"))
		throw new ForbiddenException("No se le permite crear un autor");
		
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
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
				
				int idautor = rs.getInt(1);
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
	
	private String DELETE_AUTHOR = "DELETE from author where id = ?;";
	@DELETE
	@Path("/author/{id}")
	
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
	private String UPDATE_LIBRO = "UPDATE book set tittle=ifnull(?, tittle), author=ifnull(?, author), language=ifnull(?, language), edition=ifnull(?,edition), editiondate=ifnull(?, editiondate), dateprint=ifnull(?, dateprint), editorial=ifnull(?, editorial) where idauthor =?;";
	@PUT//metodo para actualizar la ficha de un libro
	@Path("/{bookid}")
	@Consumes(MediaType.BOOKS_API_BOOK)
	@Produces(MediaType.BOOKS_API_BOOK)
	public Book updateBook(@PathParam("bookid") int bookid, Book book) {

		if (!security.isUserInRole("admin"))
			throw new ForbiddenException("You are not allowed to delete a book");
		System.out.println("Eres admin!");

		//setAdministrator(security.isUserInRole("admin"));

		//ValidateBookforUpdate(book);
		System.out.println("Book validado");
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",Response.Status.SERVICE_UNAVAILABLE);
		}
		System.out.println("BD establecida");
		PreparedStatement stmt = null;

		try {
			
			
			stmt = conn.prepareStatement(UPDATE_LIBRO);
			
			stmt.setString(1, book.getTittle());
			stmt.setString(2, book.getAuthor());
			stmt.setString(3, book.getLanguage());
			stmt.setString(4, book.getEdition());
			stmt.setDate(5, book.getEditiondate());
			stmt.setDate(6, book.getDateprint());
			stmt.setString(7, book.getEditorial());
			stmt.setInt(8, Integer.valueOf(bookid)); 
			stmt.executeUpdate();
			
			int rows = stmt.executeUpdate();
			System.out.println(stmt);
			String sbookid = Integer.toString(bookid);
			System.out.println("Miramos si hay contestación row=" + sbookid);
			if (rows == 1)
				book = getBookFromDatabase(sbookid);
			else {
				throw new NotFoundException("No hay un libro con este nombre"
						+ bookid);// Updating inexistent book
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
			
	return book;
}
	//metodo para eliminar la ficha de un libro
	private String DELETE_BOOKS_QUERY= " delete from books where bookid = ?; ";
	@DELETE
	@Path("/{bookid}")
	public void DeleteBook(@PathParam("bookid") String bookid) {

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
			
			stmt = conn.prepareStatement(DELETE_BOOKS_QUERY);
			stmt.setInt(1, Integer.valueOf(bookid));

			int rows = stmt.executeUpdate();

			if (rows == 0)
				throw new NotFoundException("There's no sting with book="
						+ bookid);

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

	}
	
	//Permite actualizar la ficha de un author.
	private String UPDATE_AUTOR = "UPDATE author set username=ifnull(?, username) where id =?;";
	@PUT
	@Path("/author/{id}")
	@Consumes(MediaType.BOOKS_API_BOOK)
	@Produces(MediaType.BOOKS_API_BOOK)

	public Author UpdateAuthor(@PathParam("id") String id, Author author) {
		
		if (!security.isUserInRole("admin"))
			throw new ForbiddenException("No se le permite actualizar autor");
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 
		PreparedStatement stmt = null;
		try {
			
			stmt = conn.prepareStatement(UPDATE_AUTOR);
			stmt.setString(1, author.getUsername());
			stmt.setInt(2, Integer.valueOf(id));
	 
			int rows = stmt.executeUpdate();
			if (rows == 0)
			{
				throw new NotFoundException("No hay un autor con este nombre"+ author);// Updating inexistent sting
			}
			else
			{
				System.out.println("Author actualizado");
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
	//Permite al admin crear una ficha de libro
	private String INSERT_BOOK = "INSERT into book (tittle, author, language, edition, editiondate, printdate, editorial) values (?,?,?,?,?,?,?);";

	@POST
	@Consumes(MediaType.BOOKS_API_BOOK)
	@Produces(MediaType.BOOKS_API_BOOK)

	public Book CreateBook(Book book)
	{
		
	//solo puede el admin
	if (!security.isUserInRole("admin"))
	throw new ForbiddenException("No se le permite crear un autor");

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		
		PreparedStatement stmt = null;
		try{
			stmt = conn.prepareStatement(INSERT_BOOK);
			
			stmt.setString(1, book.getTittle());
			stmt.setString(2, book.getAuthor());
			stmt.setString(3, book.getLanguage());
			stmt.setString(4, book.getEdition());
			stmt.setDate(5, book.getEditiondate());
			stmt.setDate(6, book.getDateprint());
			stmt.setString(7, book.getEditorial());
			stmt.executeUpdate();
			System.out.println(stmt);
			ResultSet rs = stmt.getGeneratedKeys();
			
			if (rs.next()) {
				int id = rs.getInt(1);
	 
				book = getBookFromDatabase(Integer.toString(id));
				System.out.println("libro creado");
				
			} else {
				// Something has failed...
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
			


	return book;

	}
	
	private String INSERT_REVIEWS = "insert into reviews (bookid, username, dateupdate, text) values (?,?,?,?);";
	@SuppressWarnings("unused")
	private boolean registered;
	@POST
	@Path("/{bookid}/reviews")
	@Consumes(MediaType.BOOKS_API_REVIEW)
	@Produces(MediaType.BOOKS_API_REVIEW)
	public Reviews CreateReviews(@PathParam("bookid") String idlibros, Reviews reviews) {

		if (!security.isUserInRole("registered"))
			throw new ForbiddenException("No tienes permitido hacer una reseña de un libro");
		String registrado = security.getUserPrincipal().getName(); // obtengo nombre del registrado
		System.out.println("Estas registrado con nombre" + registrado);

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {

			stmt = conn.prepareStatement(INSERT_REVIEWS, Statement.RETURN_GENERATED_KEYS);

			stmt.setInt(1, Integer.valueOf(idlibros));
			stmt.setString(2, security.getUserPrincipal().getName()); // consigues el autor de la review																
			setRegistered(security.isUserInRole("registered"));			
																	
			stmt.setDate(3, reviews.getDateupdate());
			stmt.setString(4, reviews.getText());

			System.out.println(stmt);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();

			if (rs.next()) {

				int id = rs.getInt(1);
				// autor = getLibroFromDatabase(Integer.toString(idautor));
				System.out.println("REVIEW creada");
				/*
				 * autor.setIdautor(rs.getInt("id"));
				 * autor.setName(rs.getString("titulo"));
				 */

			} else {
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

		return reviews;
	}
	public void setRegistered(boolean registered) {
		this.registered = registered;
	}
	
	private String UPDATE_REVIEWS = "UPDATE reviews set text=ifnull(?, text) where bookid = ? and reviewid = ?;";

	@PUT
	@Path("/{idlibro}/resenas/{idres}")
	@Consumes(MediaType.BOOKS_API_REVIEW)
	@Produces(MediaType.BOOKS_API_REVIEW)
	public Reviews UpdateReviews(@PathParam("bookid") String bookid,
			@PathParam("reviewid") String idres, Reviews reviews) {

		// usuario registrado
		if (!security.isUserInRole("registered"))
			throw new ForbiddenException(

			"You are not allowed to create reviews for a book");
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(UPDATE_REVIEWS);
			stmt.setInt(1, Integer.valueOf(bookid));
			stmt.setInt(2, Integer.valueOf(idres));
			stmt.setString(3, reviews.getText());

			int rows = stmt.executeUpdate();
			if (rows == 0) {
				throw new NotFoundException("No hay una reseña con este id"
						+ idres + "ni con este idlibro" + bookid);
			} else {
				System.out.println("Reseña actualizado");
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

		return reviews;

	}

	// private String GET_RESENA_BY_ID =
	// " SELECT * from resenas where idlibro = ?;";

	private Reviews getReviewsFromDatabase(String bookid, String reviewid) {

		Reviews reviews = new Reviews();

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_REVIEWS_BY_ID);
			stmt.setInt(1, Integer.valueOf(bookid));
			stmt.setInt(1, Integer.valueOf(reviewid));
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				reviews.setReviewid(rs.getInt("reviewid"));
				reviews.setBookid(rs.getInt("bookid"));
				reviews.setUsername(rs.getString("username"));
				reviews.setDateupdate(rs.getDate("dateupdate"));
				reviews.setText(rs.getString("text"));

			} else {
				throw new NotFoundException("No hay una reseña con este id"+ reviewid + "ni con este idlibro" + bookid);
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

		return reviews;
	}
	
	//Metodo para borrar una Reseña hecha por él
		private String DELETE_REVIEW_QUERY="delete from reviews where reviewid=? and bookid=?;";
		@DELETE //metodo para borrar un sting concreto
		@Path("/{bookid}/reviews/{reviewid}")
		public void deleteReview(@PathParam("bookid") String bookid, @PathParam("reviewid") String reviewid) {
			//tenemos un void de manera que no devuelve nada ni consume ni produce, devuelve 204 ya que no hay contenido
		

			setRegistered(security.isUserInRole("registered"));
			setRegistered(security.isUserInRole("admin"));
			System.out.println("Entramos");
			System.out.println("Reviewid " +reviewid +"Bookid " + bookid);
			Connection conn = null;
			try {
				conn = ds.getConnection();
			} catch (SQLException e) {
				throw new ServerErrorException("Could not connect to the database",
						Response.Status.SERVICE_UNAVAILABLE);
			}
			System.out.println("Conectados a la base de datos");
		 
			PreparedStatement stmt = null;
			try {
				stmt = conn.prepareStatement(DELETE_REVIEW_QUERY);
				stmt.setString(2, bookid);
				stmt.setString(1, reviewid);
				System.out.println("Query: " + stmt);
		 
				int rows = stmt.executeUpdate();
				if (rows == 0)
					throw new NotFoundException("There's no Review with reviewid="
							+ reviewid);
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
		}

		
		
	
}