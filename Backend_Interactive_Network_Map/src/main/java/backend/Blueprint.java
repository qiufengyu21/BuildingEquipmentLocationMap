package backend;

/**
 * Class for handling blueprints. Its objects contain the name, height, width,
 * and the actual content of the blueprint.
 * 
 * @author Cameron Lanier
 * @author Qiufeng Yu
 * @author Balaji Sundaram
 * @author Harsh Singh
 *
 */
public class Blueprint {
	private String name;
	private int height;
	private int width;
	private String content;

	/**
	 * Placeholder constructor.
	 */
	public Blueprint() {

	}

	/**
	 * Constructor to initialize the content variable.
	 * 
	 * @param content
	 *            Content of the blueprint.
	 * 
	 */
	public Blueprint(String content) {
		this.content = content;
	}

	/**
	 * Constructor to initialize the name, height, width and content varables.
	 * 
	 * @param name
	 *            Name of the blueprint.
	 * @param height
	 *            Height of the blueprint.
	 * @param width
	 *            Width of the blueprint.
	 * @param content
	 *            Content of the blueprint.
	 * 
	 */
	public Blueprint(String name, int height, int width, String content) {
		super();
		this.name = name;
		this.height = height;
		this.width = width;
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}