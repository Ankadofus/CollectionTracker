package com.CollectionTracker.item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "item")
public class Item {

	@Id
	@SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
	private Long id;
	
	@Column(name = "user_id", nullable = false)
	private Long userId;
	
	@Column(name = "item_name", nullable = false)
	private String itemName;
	
	@Column(name = "item_type", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private ItemType itemType;
	
	@Column(name = "how_many", nullable = false)
	private int howMany;
	
	@Column(name = "description")
	private String description;

	public Item(Long userId, String itemName, ItemType itemType, int howMany, String description) {
		this.userId = userId;
		this.itemName = itemName;
		this.itemType = itemType;
		this.howMany = howMany;
		this.description = description;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	public int getHowMany() {
		return howMany;
	}

	public void setHowMany(int howMany) {
		this.howMany = howMany;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
