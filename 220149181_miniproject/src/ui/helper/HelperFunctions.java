package ui.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nodes.Civilian;
import nodes.SecurityCompany;
import nodes.CommunityPolice;
import nodes.Incident;

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
			fos = new FileOutputStream(pathToWrite, true);

			if (f.length() == 0) {
				System.out.println("attempting to add correctly");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(objectToAdd);
				oos.close();
			} else {

				MyObjectOutputStream oos = null;
				oos = new MyObjectOutputStream(fos);
				oos.writeObject(objectToAdd);
				oos.close();
			}
			fos.close();

			return true;
		} 
		catch (Exception e) {

			System.out.println(e);
			return false;
		}
	}

	public static List<Object> readClassesFromFile(String pathToRead, Class<?> className) {
		File f = new File(pathToRead);
		try {

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
					 if (className.isInstance(obj)) {
		                    objects.add(obj);
		                }
				}

				ois.close();
				fis.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return objects;
	}

	
	public static void removeCivilianFromFile(String pathToRead, UUID classID) {

        List<Object> civilians = readClassesFromFile(pathToRead, Civilian.class);
        Iterator<Object> civilianIterator = civilians.iterator();
        

        
        while (civilianIterator.hasNext()) {
            Object civilian = civilianIterator.next();
            if (((Civilian) civilian).getId().equals(classID)) {
            	civilianIterator.remove();
        		System.out.println(((Civilian) civilian).getName());

            	
            }
        }
        
        File civFile = new File(pathToRead);
        civFile.delete();
        
        for(Object civilian: civilians) {
        	Civilian civ = (Civilian) civilian;
        	appendClassToFile(pathToRead, civ);
        }
        
        
       
        
        List<Object> incidents = readClassesFromFile("./data/Incident.binary", Incident.class);
        Iterator<Object> incidentsIterator = incidents.iterator();
        while (incidentsIterator.hasNext()) {
            Object incident = incidentsIterator.next();
            if (((Incident) incident).civilian.getId().equals(classID)) {
            	incidentsIterator.remove();
            }
        }
        
        File inciFile = new File("./data/Incident.binary");
        inciFile.delete();
        
        for(Object incident: incidents) {
        	Incident inc = (Incident) incident;
        	appendClassToFile("./data/Incident.binary", inc);
        }
        
        
	}
	
	public static void removeSecurityCompanyFromFile(String pathToRead, UUID classID) {

        List<Object> securityCompanies = readClassesFromFile(pathToRead, SecurityCompany.class);
        Iterator<Object> securityCompanyIterator = securityCompanies.iterator();
        

        
        while (securityCompanyIterator.hasNext()) {
            Object security = securityCompanyIterator.next();
            if (((SecurityCompany) security).getId().equals(classID)) {
            	securityCompanyIterator.remove();
            	
            }
        }
        
        File secFile = new File(pathToRead);
        secFile.delete();
        
        for(Object security: securityCompanies) {
        	SecurityCompany sec = (SecurityCompany) security;
        	appendClassToFile(pathToRead, sec);
        }
        
        
       
        
        List<Object> incidents = readClassesFromFile("./data/Incident.binary", Incident.class);
        Iterator<Object> incidentsIterator = incidents.iterator();
        while (incidentsIterator.hasNext()) {
            Object incident = incidentsIterator.next();
            if (((Incident) incident).securityCompany.getId().equals(classID)) {
            	incidentsIterator.remove();
            }
        }
        
        File inciFile = new File("./data/Incident.binary");
        inciFile.delete();
        
        for(Object incident: incidents) {
        	Incident inc = (Incident) incident;
        	appendClassToFile("./data/Incident.binary", inc);
        }
        
        
	}
	
	public static void removeCommunityPoliceFromFile(String pathToRead, UUID classID) {

        List<Object> communityPolices = readClassesFromFile(pathToRead, CommunityPolice.class);
        Iterator<Object> communityPoliceIterator = communityPolices.iterator();
        

        
        while (communityPoliceIterator.hasNext()) {
            Object comPoplice = communityPoliceIterator.next();
            if (((CommunityPolice) comPoplice).getId().equals(classID)) {
            	communityPoliceIterator.remove();
            	
            }
        }
        
        File comPoFile = new File(pathToRead);
        comPoFile.delete();
        
        for(Object police: communityPolices) {
        	CommunityPolice popo = (CommunityPolice) police;
        	appendClassToFile(pathToRead, popo);
        }
        
        
       
        
        List<Object> incidents = readClassesFromFile("./data/Incident.binary", Incident.class);
        Iterator<Object> incidentsIterator = incidents.iterator();
        while (incidentsIterator.hasNext()) {
            Object incident = incidentsIterator.next();
            if (((Incident) incident).communityPolice.getId().equals(classID)) {
            	incidentsIterator.remove();
            }
        }
        
        File inciFile = new File("./data/Incident.binary");
        inciFile.delete();
        
        for(Object incident: incidents) {
        	Incident inc = (Incident) incident;
        	appendClassToFile("./data/Incident.binary", inc);
        }
        
        
	}
	
	public static Double[] extractCoords(String input) {
	    String regex = "(\\d+(\\.\\d+)?):(\\d+(\\.\\d+)?)";

	    Pattern pattern = Pattern.compile(regex);

	    Matcher matcher = pattern.matcher(input);

	    if (matcher.find()) {
	        try {
	            double firstDouble = Double.parseDouble(matcher.group(1));
	            double secondDouble = Double.parseDouble(matcher.group(3));
	            return new Double[]{firstDouble, secondDouble};
	        } catch (NumberFormatException e) {
	            System.err.println(e);
	            return null;
	        }
	    } else {
	        System.err.println("Error");
	        return null;
	    }
	}
	
	

}
