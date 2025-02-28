package com.elibrary.exception;

import static com.elibrary.testutils.TestUtils.currentTest;
import static com.elibrary.testutils.TestUtils.exceptionTestFile;
import static com.elibrary.testutils.TestUtils.testReport;
import static com.elibrary.testutils.TestUtils.yakshaAssert;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.elibrary.inventory.Inventory;
import com.elibrary.models.Book;

class BookExceptionTest {

	private Inventory inventory;

	@BeforeEach
	public void setUp() {
		inventory = new Inventory();
	}

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testAddBookWithDuplicateISBN() throws IOException {
		try {
			Book existingBook = new Book("1234567890123", "Existing Book", "Author", "Publisher", true, LocalDate.now(),
					LocalDate.now().plusDays(14), null);
			inventory.books.add(existingBook);
			Book duplicateBook = new Book("1234567890123", "Duplicate Book", "Author", "Publisher", true,
					LocalDate.now(), LocalDate.now().plusDays(14), null);
			inventory.addBook(duplicateBook);
			yakshaAssert(currentTest(), false, exceptionTestFile);
		} catch (ISBNAlreadyExistsException ex) {
			yakshaAssert(currentTest(), true, exceptionTestFile);
		}
	}

	@Test
	public void testGetBookByNameNotFound() throws IOException {
		try {
			inventory.books.add(new Book("1234567890123", "Book1", "Author1", "Publisher1", true, LocalDate.now(),
					LocalDate.now().plusDays(14), null));
			inventory.books.add(new Book("2345678901234", "Book2", "Author2", "Publisher2", true, LocalDate.now(),
					LocalDate.now().plusDays(14), null));
			Optional<Book> result = inventory.getBookByName("NonExistentBook");
			yakshaAssert(currentTest(), result != null && result.isEmpty(), exceptionTestFile);
		} catch (ISBNAlreadyExistsException ex) {
			yakshaAssert(currentTest(), false, exceptionTestFile);
		}
	}

	@Test
	public void testUpdateBookNotFound() throws IOException {
		try {
			Book updatedBook = new Book("1234567890123", "Updated Book", "Updated Author", "Updated Publisher", true,
					LocalDate.now(), LocalDate.now().plusDays(14), null);
			Book book = inventory.updateBook(updatedBook);
			yakshaAssert(currentTest(), book == null, exceptionTestFile);
		} catch (ISBNAlreadyExistsException ex) {
			yakshaAssert(currentTest(), false, exceptionTestFile);
		}
	}
}
