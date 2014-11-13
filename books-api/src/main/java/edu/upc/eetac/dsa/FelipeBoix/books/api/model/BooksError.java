package edu.upc.eetac.dsa.FelipeBoix.books.api.model;

public class BooksError {
	private int status;
	private String message;
    //dos atributos, el status que corresponde al codigo de estado HTTP (400 o 500 si da error) 
	//Cojo constructor vacio,getters and setters y otro constructor
	public BooksError() {
		super();
	}
 
	public BooksError(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
 
	public int getStatus() {
		return status;
	}
 
	public void setStatus(int status) {
		this.status = status;
	}
 
	public String getMessage() {
		return message;
	}
 
	public void setMessage(String message) {
		this.message = message;
	}

}