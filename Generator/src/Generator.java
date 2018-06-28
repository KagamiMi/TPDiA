import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Generator {
	
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private static List<NozzleMeasure> nozzleMeasures = new LinkedList<NozzleMeasure>();
	
	public void beepForAnHour() {
		
		final Runnable beeper = new Runnable() {
			public void run() { System.out.println("beep");}
		};
		
		final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, 10, 10, SECONDS);
		scheduler.schedule(new Runnable() {
			public void run() {beeperHandle.cancel(true);}
		}, 60*60, SECONDS);
		
	}
	
	public static void main(String[] args) throws ParseException {
		Generator generator = new Generator();
		//generator.beepForAnHour();
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
			System.out.println("Fail");
		}
	}

}
