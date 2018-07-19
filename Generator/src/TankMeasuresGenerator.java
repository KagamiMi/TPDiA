import static java.util.concurrent.TimeUnit.MINUTES;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class TankMeasuresGenerator extends Thread {
	int size;
	int index = 0;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	List<TankMeasure> tankMeasures; 
	ObjectOutputStream os;
	
	public TankMeasuresGenerator(List<TankMeasure> tankMeasures, ObjectOutputStream os) {
		this.tankMeasures = tankMeasures;
		this.os = os;
	}
	
	public void run() {
		size = tankMeasures.size();
		index = 0;
		Runnable beeper = new Runnable() {
			public void run() {
				
				for (int i = 0; i < 4; i++) {
					TankMeasure temp = tankMeasures.get(index);
					temp.setDate(new Date());
					System.out.println(temp.date + " " + temp.locationId + " " + temp.meterId + " " + temp.tankId
						+ " "+ temp.fuelHeight + " " + temp.fuelVolume + " " + temp.fuelTemperature + " " + temp.waterHeight + " " + temp.waterVolume);
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
		
		final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, 0, 5, MINUTES);
		
	}
}
