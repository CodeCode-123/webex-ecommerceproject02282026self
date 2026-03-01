package com.code.api.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
public class Users {
	// member Variable
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private int id;
	@Column(name="first_name",length=50,nullable=false)
	private String firstName;
	@Column(name="last_name",length=50,nullable=false)
	private String lastName;
	@Column(name="gender",length=10,nullable=false)
	private String gender;
	@Column(name="languages",length=50,nullable=false)
	private String[] languages;
	@Column(name="email_id",length=50,nullable=false,unique=true)
	private String emailId;
	@Lob
	@Column(name="image_data")
	private byte[] imageData;
	@Column(name="country",length=50)
	private String country;
	@Column(name="password",length=20,nullable=false)
	private String password;
	@Column(name="role",length=20,nullable=false)
	private String role="Customer";

}
