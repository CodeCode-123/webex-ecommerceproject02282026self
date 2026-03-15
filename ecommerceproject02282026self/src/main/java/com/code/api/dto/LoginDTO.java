package com.code.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
	@NotBlank(message="Email is required")
	@Email(message="Invalid email format")
	private String email;
	@NotBlank(message="Password is required")
	@Size(min=6, max=12, message="Password must be between 6 to 12 characters")
	private String password;
}
