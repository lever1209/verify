package core.IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONIO {

	@SuppressWarnings("unchecked")
	public static void keyWrite(String key, String value, String path)
			throws FileNotFoundException, IOException, ParseException {

		File file = new File(path);

		StringBuilder sB = new StringBuilder();
		Scanner myReader = new Scanner(file);
		while (myReader.hasNextLine()) {
			sB.append(myReader.nextLine());
		}
		myReader.close();

		if (!sB.toString().isBlank()) {
			JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader(file));

			if (obj.get(key) != null) {
				obj.replace(key, value);
			} else {
				obj.put(key, value);
			}

			FileWriter fW = new FileWriter(path);
			fW.write(obj.toJSONString());
			fW.close();
		} else {
			JSONObject obj = new JSONObject();

			if (obj.get(key) != null) {
				obj.replace(key, value);
			} else {
				obj.put(key, value);
			}

			FileWriter fW = new FileWriter(path);
			fW.write(obj.toJSONString());
			fW.close();
		}

	}

	public static Object keyRead(String key, String path) throws FileNotFoundException, IOException, ParseException {

		JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader(new File(path)));

		return obj.get(key);

	}

	// public static void keyReadArray(String[] key, String value, String path)
	// throws FileNotFoundException, IOException, ParseException {}

}
