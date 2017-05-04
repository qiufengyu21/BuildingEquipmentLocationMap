package backend;

/**
 * Device object class. It stores the information of the device as a string.
 * 
 * @author Cameron Lanier
 * @author Qiufeng Yu
 * @author Balaji Sundaram
 * @author Harsh Singh
 *
 */
public class Device {
	private final String content;

	/**
	 * Constructor to store device information.
	 * 
	 * @param content
	 * 				Device information.
	 */
	public Device(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}
}
