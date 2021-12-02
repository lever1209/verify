package core.IO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectIO {
	public static void writeObject(Object obj, String path) throws IOException {

		FileOutputStream fileO = new FileOutputStream(new File(path));
		ObjectOutputStream objO = new ObjectOutputStream(fileO);

		objO.writeObject(obj);

		objO.close();
		fileO.close();
	}

	public static Object readObject(String path) throws IOException {

		FileInputStream fileI = new FileInputStream(new File(path));
		ObjectInputStream objI = new ObjectInputStream(fileI);

		Object obj = null;
		try {
			obj = objI.readObject();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		objI.close();
		fileI.close();
		if (obj != null) {
			return obj;
		} else {
			throw new NullPointerException();
		}
	}

}
