import java.util.Date;

public class NozzleMeasure {
	Date date;
	Integer locationId;
	int gunId;
	int tankId;
	double literCounter;
	double totalCounter;
	boolean status;
	
	public NozzleMeasure(Date date, Integer locationId, int gunId, int tankId, double literCounter, double totalCounter, boolean status ) {
		this.date = date;
		this.locationId = locationId;
		this.gunId = gunId;
		this.tankId = tankId;
		this.literCounter = literCounter;
		this.totalCounter = totalCounter;
		this.status = status;
	}
}
