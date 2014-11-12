package edu.upc.eetac.dsa.FelipeBoix.books.api.model;

import java.util.List;

import javax.ws.rs.core.Link;

public class BookRootAPI {
	private List<Link> links; //atributo con getters y setters
	 
	public List<Link> getLinks() {
		return links;
	}
 
	public void setLinks(List<Link> links) {
		this.links = links;
	}
}
