package backendTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import backend.Blueprint;

public class BlueprintTest {
	private Blueprint b1;
	private Blueprint b3;
	private String name;
	private int height;
	private int width;
	private String content;

	@Before
	public void setUp() throws Exception {
		name = "TestBlueprint";
		height = 100;
		width = 100;
		content = "ABase64StringRepresentationOfAnImage";
		b1 = new Blueprint(content);
		b3 = new Blueprint(name, height, width, content);
	}

	@Test
	public void testGetName() {
		assertEquals("TestBlueprint", b3.getName());
	}

	@Test
	public void testSetName() {
		name = "TestSetName";
		b3.setName(name);
		assertEquals("TestSetName", name);
	}

	@Test
	public void testGetHeight() {
		assertEquals(100, b3.getHeight());
	}

	@Test
	public void testSetHeight() {
		height = 101;
		b3.setHeight(height);
		assertEquals(101, b3.getHeight());
	}

	@Test
	public void testGetWidth() {
		assertEquals(100, b3.getWidth());
	}

	@Test
	public void testSetWidth() {
		width = 101;
		b3.setWidth(width);
		assertEquals(101, width);
	}

	@Test
	public void testGetContent() {
		assertEquals("ABase64StringRepresentationOfAnImage", content);
	}

	@Test
	public void testSetContent() {
		content = "";
		b1 = new Blueprint(content);
		b1.setContent(content);
		assertEquals("", b1.getContent());
	}

}
