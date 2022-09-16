package extra;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Testproperties {
	public static FileInputStream fisConf;
	public static FileInputStream fisOR;

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Properties config = new Properties();
		Properties OR = new Properties();
		fisConf = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/props/config.properties");

		fisOR = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/props/OR.properties");

		config.load(fisConf);
		OR.load(fisOR);

		System.out.println(config.getProperty("browser"));
		System.out.println(OR.getProperty("BMLoginBtn"));
	}

}
