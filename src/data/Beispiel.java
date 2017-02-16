package data; 

import helper.NdTimeSeries;
import java.io.*;

public class Beispiel {

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		NdTimeSeries ts = readNdTimeSeriesObject("/home/lukas/Dokumente/UNI/alleDaten.jbo");
		System.out.println(ts);

	}

	public static NdTimeSeries readNdTimeSeriesObject(String filename) throws FileNotFoundException, IOException, ClassNotFoundException {
	        FileInputStream fis = new FileInputStream(filename);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object o = ois.readObject();
		if (o instanceof NdTimeSeries) {
		    return (NdTimeSeries)o;
		}
		return null;
    	}
}

