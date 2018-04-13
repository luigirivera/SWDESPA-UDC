package ultimatedesignchallenge.controller;

public class AvailableInterval implements Interval{
	private String startTime;
	private String endTime;
	private String doctor;
	
	public AvailableInterval(String startTime, String endTime, String doctor) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.doctor = doctor;
	}
	
	@Override
	public String startTime() {
		return startTime;
	}

	@Override
	public String endTime() {
		return endTime;
	}

	@Override
	public String doctor() {
		return doctor;
	}

	@Override
	public String client() {
		return null;
	}
	
}
