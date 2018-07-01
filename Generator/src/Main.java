import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String args[]) throws ParseException, IOException {
		List<NozzleMeasure> nozzleMeasures = new LinkedList<NozzleMeasure>();
		ObjectOutputStream os;
		ObjectInputStream is;
		try {
			Scanner sc = new Scanner(new File("Dane paliwowe/Zestaw 1/nozzleMeasures.log").getAbsoluteFile());
			sc.useDelimiter(";|\\r\\n");
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			while (sc.hasNext()) {	
				Date date = format.parse(sc.next());
				Integer locationId = null;
				try {
					String intString = sc.next();
					locationId = Integer.parseInt(intString);
				}
				catch(NumberFormatException ex) {
					
				}
				int gunId = sc.nextInt();
				int tankId = sc.nextInt();
				double literCounter = sc.nextDouble();
				double totalCounter = sc.nextDouble();
				boolean status = true;
				//System.out.println("\\"+sc.next()+"\\");
				if (sc.nextInt() != 0) { status = true;} else {status = false;}
				
				nozzleMeasures.add(new NozzleMeasure(date,locationId,gunId,tankId,literCounter,totalCounter,status));
			}
			System.out.println("Did it");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		Socket s = new Socket("localhost",7);
		os = new ObjectOutputStream(s.getOutputStream());
		if (os == null) {System.out.println("error");}
		is = new ObjectInputStream(s.getInputStream());
		Generator generator = new Generator();
		generator.send(nozzleMeasures,os,is);
	}
}
