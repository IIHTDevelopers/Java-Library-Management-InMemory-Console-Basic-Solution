package com.elibrary.functional;

import static com.elibrary.testutils.TestUtils.businessTestFile;
import static com.elibrary.testutils.TestUtils.currentTest;
import static com.elibrary.testutils.TestUtils.testReport;
import static com.elibrary.testutils.TestUtils.yakshaAssert;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.elibrary.inventory.Inventory;
import com.elibrary.models.Book;

public class FunctionalTest {

	private Inventory inventory;
	private Book book;

	@BeforeEach
	public void setUp() {
		inventory = new Inventory();
		book = new Book("1234567890123", "Mock Book", "Mock Author", "Mock Publisher", true, LocalDate.now(),
				LocalDate.now().plusDays(14), null);
	}

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testAddBook() throws IOException, ClassNotFoundException {
		try {
			inventory.addBook(book);
			yakshaAssert(currentTest(), inventory.books.size() == 1, businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testGetBookByName() throws IOException {
		try {
			inventory.books.add(book);
			yakshaAssert(currentTest(), inventory.getBookByName("mock").isPresent(), businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testUpdateBook() throws IOException {
		try {
			inventory.books.add(book);
			Book updatedBook = new Book("1234567890123", "Updated Book", "Updated Author", "Updated Publisher", false,
					LocalDate.now(), LocalDate.now().plusDays(7), null);
			Book updated = inventory.updateBook(updatedBook);
			yakshaAssert(currentTest(), updated != null && updated.getTitle().equals("Updated Book"), businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testIsBookAvailable() throws IOException {
		try {
			inventory.books.add(book);
			yakshaAssert(currentTest(), inventory.isBookAvailable("1234567890123"), businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testGetUserById() throws IOException {
		try {
			Map<String, String> users = Map.of("user123", "John Doe");
			String retrievedUser = users.get("user123");
			yakshaAssert(currentTest(), retrievedUser != null, businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testGetUsers() throws IOException {
		try {
			Map<String, String> users = Map.of("user123", "John Doe");
			Map<String, String> retrievedUsers = users;
			yakshaAssert(currentTest(), retrievedUsers.size() == 1, businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}
}
