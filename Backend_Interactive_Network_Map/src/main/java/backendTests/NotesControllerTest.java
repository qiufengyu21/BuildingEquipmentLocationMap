package backendTests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.PrintWriter;

import org.junit.Before;
import org.junit.Test;

import backend.Notes;
import backend.NotesController;

public class NotesControllerTest {
	private NotesController nc;

	@Before
	public void setUp() throws Exception {
		nc = new NotesController();
	}

	@Test
	public void testGetNotes() {
		String contents = "{\"fileName\":\"3\",\"contents\":\"Can you see this?\"}";
		PrintWriter writer;

		try {
			writer = new PrintWriter("Notes/TestNotes.txt", "UTF-8");
			writer.println(contents);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals("Can you see this?", nc.getNotes("TestNotes"));

		assertEquals("", nc.getNotes("This name doesn't even exist!"));

		File f = new File("Notes/TestNotes.txt");
		f.delete();
	}

	@Test
	public void testSetNotes() {
		nc.setNotes("{\"fileName\":\"TestNotes\",\"contents\":\"This is a test content!\"}");
		File f = new File("Notes/TestNotes.txt");
		assertTrue(f.exists());
	}

}
