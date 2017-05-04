package backend;

/**
 * Layout object class. A class used to store the latitude, longitude, the name
 * of the icon and the alternative information about the layout.
 * 
 * @author Cameron Lanier
 * @author Qiufeng Yu
 * @author Balaji Sundaram
 * @author Harsh Singh
 *
 */
public class Layout {
	private double lat;
	private double lng;
	private String icon;
	private String alt;

	/**
	 * Constructor
	 * 
	 * @param lat
	 *            latitude of the icon as a double
	 * @param lng
	 *            longitude of the icon as a double
	 * @param icon
	 *            the name of the icon as a string
	 * @param alt
	 *            alternative information
	 */
	public Layout(double lat, double lng, String icon, String alt) {
		this.lat = lat;
		this.lng = lng;
		this.icon = icon;
		this.alt = alt;
	}

	
	/**
	 * Placeholder constructor.
	 */
	public Layout() {

	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}
}
