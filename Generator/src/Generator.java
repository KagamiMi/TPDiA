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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Generator {
	
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private static List<NozzleMeasure> nozzleMeasures = new LinkedList<NozzleMeasure>();
	int size;
	int index = 0;
	static ObjectOutputStream os;
	static ObjectInputStream is;
	
	public void send() {
		size = nozzleMeasures.size();
		Runnable beeper = new Runnable() {
			public void run() {
				//for (int i = 0; i <12; i++) {
					NozzleMeasure temp = nozzleMeasures.get(index);
					temp.setDate(new Date());
					System.out.println(temp.date + " " + temp.locationId + " " + temp.gunId + " " + temp.tankId
						+ " "+ temp.literCounter + " " + temp.totalCounter + " " + temp.status);
					try {
						//os.writeInt(56);
						
						os.writeObject(temp);
						os.flush();
						System.out.println("sended");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("error here! 43");
					}
					index++;
					if (index == size) {
						index = 0;
					}
				//}
				
			}
		};
		
		final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, 1, 30, SECONDS);
//		scheduler.schedule(new Runnable() {
//			public void run() {beeperHandle.cancel(true);}
//		}, nozzleMeasures.size(), SECONDS);
		
	}
	
	public static void main(String[] args) throws ParseException, IOException {
		
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
		generator.send();
	}

}
