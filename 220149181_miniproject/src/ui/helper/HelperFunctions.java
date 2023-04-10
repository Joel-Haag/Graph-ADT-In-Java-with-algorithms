package ui.helper;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class HelperFunctions {

	public static boolean appendClassToFile(String pathToWrite, Object objectToAdd) {
		try {
			File f = new File(pathToWrite);

			// check if the directory to write the file exists
			File directory = f.getParentFile();
			if (directory != null && !directory.exists()) {
				if (!directory.mkdirs()) {
					System.out.println("Failed to create directory: " + directory.getAbsolutePath());
					return false;
				}
			}

			// create a FileOutputStream with append mode set to true
			FileOutputStream fos = null;
			fos = new FileOutputStream(
					pathToWrite, true);
			
			 if (f.length() == 0) {
                 ObjectOutputStream oos
                     = new ObjectOutputStream(fos);
                 oos.writeObject(objectToAdd);
                 oos.close();
             }
			    else {
			    	 
                    MyObjectOutputStream oos = null;
                    oos = new MyObjectOutputStream(fos);
                    oos.writeObject(objectToAdd);
 
                    oos.close();
                }
			 fos.close();

			return true;
		} // Catch block to handle the exceptions
		catch (Exception e) {
			// Print the exception along with the
			// display message
			System.out.println("Error Occurred" + e);
			return false;
		}
	}

	public static List<Object> readClassesFromFile(String pathToRead) {
		File f = new File(pathToRead);
		// Try block to check for exceptions
		try {

			// Creating new file using File object above
			f.createNewFile();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		List<Object> objects = new ArrayList<Object>();

		if (f.length() != 0) {
			try {
				FileInputStream fis = null;
				fis = new FileInputStream(pathToRead);
				ObjectInputStream ois = new ObjectInputStream(fis);
				Object obj = null;
				while (fis.available() != 0) {
					obj = (Object) ois.readObject();
					objects.add(obj);
				}

				ois.close();
				fis.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return objects;
	}
}