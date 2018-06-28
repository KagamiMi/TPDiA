import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.*;

import java.io.File;
import java.io.FileNotFoundException;

public class Generator {
	
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	public void beepForAnHour() {
		
		final Runnable beeper = new Runnable() {
			public void run() { System.out.println("beep");}
		};
		
		final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, 10, 10, SECONDS);
		scheduler.schedule(new Runnable() {
			public void run() {beeperHandle.cancel(true);}
		}, 60*60, SECONDS);
		
	}
	
	public static void main(String[] args) {
		Generator generator = new Generator();
		generator.beepForAnHour();
//		try {
//			Scanner sc = new Scanner(new File("C:/Users/Kagami/Desktop/Dane paliwowe/Dane paliwowe/Zestaw 1/nozzleMeasures.log"));
//			sc.useDelimiter(";");
//			while (sc.hasNext()) {
//				System.out.println(sc.next());
//			}
//			System.out.println("Did it");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("Fail");
//		}
	}
//	public static void main(String[] args) {
//		System.out.println("One: " + new Date());
//		try {
//			Thread.sleep(1000);
//			System.out.println("Two: "+ new Date());
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
}
