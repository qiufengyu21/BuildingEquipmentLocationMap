package backendTests;

import static org.junit.Assert.*;
import backend.Layout;

import org.junit.Before;
import org.junit.Test;

public class LayoutTest {
	
	// Delta is used to denote the amount of error the test can tolerate, since
	// float calculations can have round-off errors.
	private static final double DELTA = 1e-15;
	
	private double lat;
	private double lng;
	private String icon;
	private String alt;
	private Layout layout;

	@Before
	public void setUp() throws Exception {
		// For coverage purposes.
		layout = new Layout();
		
		lat = 10.73;
		lng = 25.41;
		icon = "routerIcon";
		alt = "Bob's router";
		layout = new Layout(lat, lng, icon, alt);
	}

	@Test
	public void testGetLat() {
		assertEquals(lat, layout.getLat(), DELTA);
	}
	
	@Test
	public void testSetLat() {
		double lat = 21.73;
		layout.setLat(lat);
		assertEquals(lat, layout.getLat(), DELTA);
	}
	
	@Test
	public void testGetLng() {
		assertEquals(lng, layout.getLng(), DELTA);
	}
	
	@Test
	public void testSetLng() {
		double lng = 15.11;
		layout.setLng(lng);
		assertEquals(lng, layout.getLng(), DELTA);
	}
	
	@Test
	public void testGetIcon() {
		assertEquals(icon, layout.getIcon());
	}
	
	@Test
	public void testSetIcon() {
		String icon = "switchIcon";
		layout.setIcon(icon);
		assertEquals(icon, layout.getIcon());
	}
	
	@Test
	public void testGetAlt() {
		assertEquals(alt, layout.getAlt());
	}
	
	@Test
	public void testSetAlt() {
		String alt = "Rob's switch";
		layout.setAlt(alt);
		assertEquals(alt, layout.getAlt());
	}

}
