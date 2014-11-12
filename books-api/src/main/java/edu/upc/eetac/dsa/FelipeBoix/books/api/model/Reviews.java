package edu.upc.eetac.dsa.FelipeBoix.books.api.model;

import java.sql.Date;

public class Reviews {
	
	private String username = null;
	private Date dateupdate = null;
	private String text = null;
	private int bookid = 0;
	private int reviewid = 0;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getDateupdate() {
		return dateupdate;
	}
	public void setDateupdate(Date dateupdate) {
		this.dateupdate = dateupdate;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getBookid() {
		return bookid;
	}
	public void setBookid(int bookid) {
		this.bookid = bookid;
	}
	public int getReviewid() {
		return reviewid;
	}
	public void setReviewid(int reviewid) {
		this.reviewid = reviewid;
	}

}
