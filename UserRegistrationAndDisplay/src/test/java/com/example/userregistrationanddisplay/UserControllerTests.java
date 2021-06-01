/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.userregistrationanddisplay;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;
	
	private String name = "Frodo";
	
	private String greetingUser = "Welcome to this example application ";
	private String greetingUnknownUser = "Welcome to this example application unknown user.";

	@BeforeEach
	public void deleteAllBeforeTests() throws Exception {
		userRepository.deleteAll();
	}

	@Test
	public void shouldReturnRepositoryIndex() throws Exception {
		mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk()).andExpect(
				jsonPath("$._links.user").exists());
	}

	@Test
	public void shouldCreateUser() throws Exception {
		mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(
				"{\"name\": \"Frodo\""
				+ ", \"birthdate\": \"2000-01-01\""
				+ ", \"country\": \"france\"" 
				+ ", \"mailAddress\": \"Pierre@doe.fr\"}"))
		.andExpect(status().isCreated());
	}

	@Test
	public void shouldCreateAndRetrieveUser() throws Exception {

		mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(
				"{\"name\": \"Frodo\", \"birthdate\":\"2000-01-01\", "
				+ "\"country\":\"France\", \"mailAddress\":\"Pierre@doe.fr\"}")).andExpect(
						status().isCreated());

		MvcResult getResult = mockMvc.perform(get("/user/" + name).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andReturn();
		
		Assert.assertTrue(getResult.getResponse().getContentAsString().contains(
				"\"mailAddress\":\"Pierre@doe.fr\"," +
				"\"job\":null," +
				"\"name\":\"Frodo\"," +
				"\"country\":\"France\"," +
				"\"birthdate\":\"2000-01-01\""
				));
	}
	
	@Test
	public void shouldGreetKnownUser() throws Exception {
		mockMvc.perform(get("/user/welcomeUser?name={name}", name))
		.andExpect(status().isAccepted())
		.andReturn().getResponse().getContentAsString()
		.equals(greetingUser + name + ".");
	}
	
	@Test
	public void shouldGreetUnknownUser() throws Exception {
		mockMvc.perform(get("/user/welcomeUser"))
		.andExpect(status().isAccepted())
		.andReturn().getResponse().getContentAsString()
		.equals(greetingUnknownUser);
	}

	@Test
	public void shouldRejectUserCreationBirthdate() throws Exception {
		mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(
				"{\"name\": \"Frodo\""
						+ ", \"birthdate\": \"2020-01-01\""
						+ ", \"country\": \"france\"" 
						+ ", \"mailAddress\": \"Pierre@doe.fr\"}"))
		.andExpect(status().isUnprocessableEntity());
	}
	
	@Test
	public void shouldRejectUserCreationCountry() throws Exception {
		mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(
				"{\"name\": \"Frodo\""
						+ ", \"birthdate\": \"2000-01-01\""
						+ ", \"country\": \"allemagne\"" 
						+ ", \"mailAddress\": \"Pierre@doe.fr\"}"))
		.andExpect(status().isUnprocessableEntity());
	}
}
