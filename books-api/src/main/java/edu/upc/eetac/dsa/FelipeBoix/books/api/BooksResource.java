package edu.upc.eetac.dsa.FelipeBoix.books.api;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import edu.upc.eetac.dsa.FelipeBoix.books.api.model.Book;
import edu.upc.eetac.dsa.FelipeBoix.books.api.model.BookCollection;

 
@Path("/books")
public class BooksResource {
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	
	private String GET_STINGS_QUERY = "select s.*, u.name from stings s, users u where u.username=s.username order by creation_timestamp desc";
	
	@GET
	@Produces(MediaType.BOOK_API_BOOKS_COLLECTION)
	public BookCollection getBooks() {
		BookCollection stings = new BookCollection();
	 
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_STINGS_QUERY);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Book book = new Book();
				book.setBookid(rs.getInt("bookid"));
				//book.setTittle(rs.getString("tittle"));
			//	book.setAuthor(rs.getString("name"));
				//book.setSubject(rs.getString("subject"));
				//book.setLastModified(rs.getTimestamp("last_modified")
						//.getTime());
				//book.setCreationTimestamp(rs
				//		.getTimestamp("creation_timestamp").getTime());
				//stings.addSting(book);
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
	 
		return stings;
	}
}
