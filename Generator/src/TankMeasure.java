import java.util.Date;

public class TankMeasure implements java.io.Serializable {
	Date date;
	Integer locationId;
	Integer meterId;
	int tankId;
	double fuelHeight;
	double fuelVolume;
	double fuelTemperature;
	Double waterHeight;
	Double waterVolume;
	
	public TankMeasure(Date date, Integer locationId, Integer meterId, int tankId, double fuelHeight, double fuelVolume, double fuelTemperature,
	Double waterHeight, Double waterVolume) {
		this.date = date;
		this.locationId = locationId;
		this.meterId = meterId;
		this.tankId = tankId;
		this.fuelHeight = fuelHeight;
		this.fuelVolume = fuelVolume;
		this.fuelTemperature = fuelTemperature;
		this.waterHeight = waterHeight;
		this.waterVolume = waterVolume;
	}
	
	void setDate(Date ndate) {
		date = ndate;
	}
}
