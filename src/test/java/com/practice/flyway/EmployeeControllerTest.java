package com.practice.flyway;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FlywayApplication.class)
@SpringBootTest
public class EmployeeControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;


	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void verifygetEmployees() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/employees").accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$", hasSize(1))).andDo(print());
	}

	@Test
	public void verifyaddEmployee() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/employees")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"firstName\" : \"Aarushi\", \"lastName\" : \"Jain\", \"deptName\" : \"LS\", \"age\" : \"23\" }")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.firstName").exists())
		.andExpect(jsonPath("$.lastName").exists())
		.andExpect(jsonPath("$.deptName").exists())
		.andExpect(jsonPath("$.age").exists())
		.andExpect(jsonPath("$.firstName").value("Aarushi"))
		.andExpect(jsonPath("$.lastName").value("Jain"))
		.andDo(print());
	}

	//@Test
	public void deleteEmployee() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/employee/8").accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.errorCode").value(404))
		.andExpect(jsonPath("$.message").value("Employee with id 8 could not be deleted"))
		.andDo(print());
	}

	//@Test
	public void verifyupdateEmployee() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/employee/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"firstName\" : \"Amit\", \"lastName\" : \"Jain\", \"deptName\" : \"LS\", \"age\" : \"24\" }")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.firstName").exists())
		.andExpect(jsonPath("$.lastName").exists())
		.andExpect(jsonPath("$.deptName").exists())
		.andExpect(jsonPath("$.age").exists())
		.andExpect(jsonPath("$.firstName").value("Amit"))
		.andExpect(jsonPath("$.lastName").value("Jain"))
		.andDo(print());
	}

	//@Test
	public void verifyDeleteEmployee() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/employee/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.status").value(200))
		.andExpect(jsonPath("$.message").value("Employee with id 4 is successfully deleted"))
		.andDo(print());
	}
}
