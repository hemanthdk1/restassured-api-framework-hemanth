package example.example.util;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * The Class has all TestProperties (All properties mentioned in <strong>test.properties</strong> file).
 *
 *
 */
public class TestProperties {

	/** The Constant props. */
	private static final Properties props = new Properties();

	/**
	 * Load all propertie.
	 */
	public static void loadAllPropertie() {
		try {
			FileInputStream Locator;
			Locator = new FileInputStream("src/test/resources/propertyFiles/config.properties");
			props.load(Locator);
		} catch (IOException e) {
			System.out.println("Could not load properties : " + e.getMessage());
		}
	}

	/**
	 * Gets the property.
	 *
	 * @param key the key
	 * @return the property
	 */
	public static String getProperty(String key) {
		return props.getProperty(key);
	}

	/**
	 * Put property.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public static void putProperty(String key, String value) {
		props.put(key, value);
	}
}
