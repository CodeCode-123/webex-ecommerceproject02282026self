package com.code.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {
	@NotBlank(message="Email is required")
	@Email(message="Invalid email format")
	private String email;
	@Size(min=6, max=12, message="Password must be between 6 to 12 characters")
	private String password;
	@NotBlank(message="First name is required")
	private String firstName;
	@NotBlank(message="Last name is required")
	private String lastName;
}
