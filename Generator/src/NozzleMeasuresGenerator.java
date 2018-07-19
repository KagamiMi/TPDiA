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

public class NozzleMeasuresGenerator extends Thread {
	int size;
	int index = 0;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	List<NozzleMeasure> nozzleMeasures; 
	ObjectOutputStream os;

	public NozzleMeasuresGenerator(List<NozzleMeasure> nozzleMeasures, ObjectOutputStream os) {
		this.nozzleMeasures = nozzleMeasures;
		this.os = os;
	}
	
	
	
	public void run() {
		size = nozzleMeasures.size();
		index = 0;
		Runnable beeper = new Runnable() {
			public void run() {
				
				for (int i = 0; i <12; i++) {
					NozzleMeasure temp = nozzleMeasures.get(index);
					temp.setDate(new Date());
					System.out.println(temp.date + " " + temp.locationId + " " + temp.gunId + " " + temp.tankId
						+ " "+ temp.literCounter + " " + temp.totalCounter + " " + temp.status);
					try {
						os.writeObject(temp);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("error here! 43");
					}
					index++;
					if (index == size) {
						index = 0;
					}
				}
				try {
					os.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};
		
		final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, 0, 1, MINUTES);
		
	}

}
