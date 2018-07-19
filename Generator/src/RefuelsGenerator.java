import static java.util.concurrent.TimeUnit.MINUTES;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class RefuelsGenerator extends Thread {
	int size;
	int index = 0;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	List<Refuel> refuels;
	ObjectOutputStream os;
	
	public RefuelsGenerator(List<Refuel> refuels, ObjectOutputStream os) {
		this.refuels = refuels;
		this.os = os;
	}
	
	public void run() {
		size = refuels.size();
		index = 0;
		Runnable beeper = new Runnable() {
			public void run() {
			
				//for (int i = 0; i < 4; i++) {
					Refuel temp = refuels.get(index);
					temp.setDate(new Date());
					System.out.println(temp.date + " " + temp.tankId + " " + temp.fuelVolume + " " + temp.speed);
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
				//}
				try {
					os.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};
		
		final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, 0, 30, MINUTES);
		
	}
}
