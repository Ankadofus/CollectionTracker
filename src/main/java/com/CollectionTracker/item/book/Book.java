package com.CollectionTracker.item.book;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.CollectionTracker.item.Item;
import com.CollectionTracker.item.ItemType;

@Entity
@Table(name = "item")
public class Book extends Item{
	
	@Column(name = "publisher")
	private String publisher;
	
	@Column(name = "date_published")
	private Date datePublished;
	
	@Column(name = "edition")
	private String edition;
	
	@Column(name = "author")
	private String author;


	public Book(Long userId, String bookName, ItemType itemType, int howMany, String description, String publisher, Date datePublished, 
			String edition, String author) {
		
		super(userId, bookName, ItemType.BOOK, 1, description);
		this.publisher = publisher;
		this.datePublished = datePublished;
		this.edition = edition;
		this.author = author;
	}


	public String getPublisher() {
		return publisher;
	}


	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}


	public Date getDatePublished() {
		return datePublished;
	}


	public void setDatePublished(Date datePublished) {
		this.datePublished = datePublished;
	}


	public String getEdition() {
		return edition;
	}


	public void setEdition(String edition) {
		this.edition = edition;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}

	
	
}
