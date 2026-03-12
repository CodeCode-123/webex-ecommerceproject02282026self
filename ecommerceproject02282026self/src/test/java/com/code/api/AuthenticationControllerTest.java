package com.code.api;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.transaction.annotation.Transactional;

import com.code.api.Ecommerceproject02282026selfApplication;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@AutoConfigureMockMvc
@SpringBootTest(classes=Ecommerceproject02282026selfApplication.class)
@Transactional
public class AuthenticationControllerTest {
	private static MockHttpServletRequest request;
	@PersistenceContext
	private EntityManager entityManger;
	

}
