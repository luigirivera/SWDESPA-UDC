package ultimatedesignchallenge.controller;

public class AppointmentInterval implements Interval{
	private String startTime;
	private String endTime;
	private String doctor;
	private String client;
	
	public AppointmentInterval(String startTime, String endTime, String doctor, String client) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.doctor = doctor;
		this.client = client;
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
		return client;
	}
	
}
