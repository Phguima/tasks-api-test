package br.com.phferreira.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend/";
	}
	
	@Test
	public void deveRetornarTasks() {
		RestAssured.given()
		.when()
			.get("todo")
		.then()
			.statusCode(200);
	}
	
	@Test
	public void deveIncluirTaskComSucesso() {
		RestAssured.given()
			.body("{\"task\":\"Teste via RestAssured\",\"dueDate\":\"2022-12-30\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("todo")
		.then()
			.log().all()
			.statusCode(201);
	}
	
	@Test
	public void naoDeveIncluirTaskComDataPassada() {
		RestAssured.given()
			.body("{\"task\":\"Teste via RestAssured\",\"dueDate\":\"2015-12-30\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("todo")
		.then()
			.body("message", CoreMatchers.is("Due date must not be in past"))
			.statusCode(400)
			.log().all();
	}
}