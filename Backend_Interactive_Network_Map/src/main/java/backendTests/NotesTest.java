package backendTests;

import static org.junit.Assert.*;

import org.junit.Test;

import backend.Notes;

public class NotesTest {
	private Notes notes;

	@Test
	public void testNotesStringString() {
		notes = new Notes("TestFileName1", "Test Content1!");
		assertEquals("TestFileName1", notes.getFileName());
		assertEquals("Test Content1!", notes.getContents());
	}

	@Test
	public void testGetFileName() {
		notes = new Notes();
		System.out.print(notes.getFileName());
		assertEquals(null, notes.getFileName());
		notes = new Notes("TestFileName2", "Test Content2!");
		assertEquals("TestFileName2", notes.getFileName());
	}

	@Test
	public void testSetFileName() {
		notes = new Notes();
		notes.setFileName("QWERTY");
		assertEquals("QWERTY", notes.getFileName());
	}

	@Test
	public void testNotes() {
		notes = new Notes();
		assertEquals(null, notes.getContents());
		assertEquals(null, notes.getFileName());
	}

	@Test
	public void testGetContents() {
		notes = new Notes("TestName", "Test Content.");
		assertEquals("Test Content.", notes.getContents());
	}

	@Test
	public void testSetContents() {
		notes = new Notes();
		notes.setContents("ASDFG!");
		assertEquals("ASDFG!", notes.getContents());
	}

}
