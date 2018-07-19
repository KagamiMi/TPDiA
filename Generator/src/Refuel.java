import java.util.Date;

public class Refuel implements java.io.Serializable {
	Date date;
	int tankId;
	double fuelVolume;
	double speed;
	
	public Refuel(Date date, int tankId, double fuelVolume, double speed) {
		this.date = date;
		this.tankId = tankId;
		this.fuelVolume = fuelVolume;
		this.speed = speed;
	}
	
	void setDate(Date ndate) {
		date = ndate;
	}
}
