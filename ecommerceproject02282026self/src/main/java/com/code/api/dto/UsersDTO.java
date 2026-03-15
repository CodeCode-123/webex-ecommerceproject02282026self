package com.code.api.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersDTO {
	@Size(min=1, message="First name should be at least 1 character")
	private String firstName;
	@Size(min=1, message="Last name should be at least 1 character")
	private String lastName;
	private String gender;
	private String[] languages;
	private byte[] imageData;
	private String country;
}
