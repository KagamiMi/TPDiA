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
import java.util.concurrent.TimeUnit;

public class Main {
	static List<NozzleMeasure> nozzleMeasures = new LinkedList<NozzleMeasure>();
	static List<TankMeasure> tankMeasures = new LinkedList<TankMeasure>();
	static List<Refuel> refuels = new LinkedList<Refuel>();
	static ObjectOutputStream os1,os2,os3;
	static int portNumber = 7;
	public static void main(String args[]) throws ParseException, IOException, InterruptedException {
		
		nozzleMeasuresLoad();
		tankMeasuresLoad();
		refuelsLoad();
		
		Socket s1 = new Socket("localhost",portNumber);
		os1 = new ObjectOutputStream(s1.getOutputStream());
		Socket s2 = new Socket("localhost",portNumber);
		os2 = new ObjectOutputStream(s2.getOutputStream());
		Socket s3 = new Socket("localhost",portNumber);
		os3 = new ObjectOutputStream(s3.getOutputStream());
		
		
//		while (true) {
//			System.out.println("waiting");
//			
//			Thread.sleep(10000);
//		}
		
		try {
			os3.writeObject("refuel");
			os3.flush();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		new RefuelsGenerator(refuels, os3).start();
		
		try {
			os1.writeObject("nozzleMeasure");
			os1.flush();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		new NozzleMeasuresGenerator(nozzleMeasures,os1).start();
		
		
		try {
			os2.writeObject("tankMeasure");
			os2.flush();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		new TankMeasuresGenerator(tankMeasures, os2).start();
		

		
		
		
		
		
		
	}
	
	private static void refuelsLoad() {
		try {
			Scanner sc = new Scanner(new File("Dane paliwowe/Zestaw 1/refuel.log").getAbsoluteFile());
		
		sc.useDelimiter(";|\\r\\n");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		while (sc.hasNext()) {	
			Date date = format.parse(sc.next());
			
			
			int tankId = sc.nextInt();
			double fuelVolume = sc.nextDouble();
			double speed = sc.nextDouble();
			
			refuels.add(new Refuel(date,tankId,fuelVolume,speed));
		}
		System.out.println("Did it");
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	
	private static void tankMeasuresLoad() {
		try {
			Scanner sc = new Scanner(new File("Dane paliwowe/Zestaw 1/tankMeasures.log").getAbsoluteFile());
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
				Integer meterId = null;
				try {
					String intString = sc.next();
					meterId = Integer.parseInt(intString);
				}
				catch(NumberFormatException ex) {
					
				}
				
				int tankId = sc.nextInt();
				double fuelHeight = sc.nextDouble();
				double fuelVolume = sc.nextDouble();
				double fuelTemperature = sc.nextDouble();
				
				Double waterHeight = null;
				try {
					String doubleString = sc.next();
					waterHeight = Double.parseDouble(doubleString);
				}
				catch(NumberFormatException ex) {
					
				}
				Double waterVolume = null;
				try {
					String doubleString = sc.next();
					waterVolume = Double.parseDouble(doubleString);
				}
				catch(NumberFormatException ex) {
					
				}
				tankMeasures.add(new TankMeasure(date,locationId,meterId,tankId,fuelHeight,fuelVolume,fuelTemperature,waterHeight,waterVolume));
			}
			System.out.println("Did it");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void nozzleMeasuresLoad() {
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
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
