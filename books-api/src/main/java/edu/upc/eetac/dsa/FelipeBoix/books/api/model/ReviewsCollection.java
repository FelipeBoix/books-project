package edu.upc.eetac.dsa.FelipeBoix.books.api.model;

import java.util.ArrayList;
import java.util.List;

public class ReviewsCollection {
		private List<Book> reviews;
		
		public ReviewsCollection() {
			super();
			reviews = new ArrayList<>();
		}
	 

		public List<Book> getResenas() {
			return reviews;
		}

		public void setResenas(List<Book> reviews) {
			this.reviews = reviews;
		}
		
	}

