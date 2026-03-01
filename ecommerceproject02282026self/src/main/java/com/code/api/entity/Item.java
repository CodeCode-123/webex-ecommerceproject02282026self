package com.code.api.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="item")
@Getter
@Setter
public class Item implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="item_id")
	private int itemId;
	@Column(name="item_name")
	private String itemName;
	@Column(name="item_price")
	private int itemPrice;
	@Lob
	private byte[] imageData;
										   	
	//---------------------------------------item mapped to category------------------------------------------//
	@ManyToOne
	 @JoinColumn(name="category_id")
	private Category category;
	//--------------------------------------------------------------------------------------------------------//
	
	public Item() {
		this.itemId = 0;
	}
}

