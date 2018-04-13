package ultimatedesignchallenge.controller;

public class SlotBuilder {

	public SlotC buildDoc1Available(String startTime, String endTime) { //change if needed (slotC because its the slot in controller)
		SlotC slot = new SlotC();
		
		int intervals = timeToInterval(startTime, endTime);
		String temp = startTime;
		for(int i = 0; i < intervals; i++) {
			slot.addInterval(new AvailableInterval(temp, incrementTime(temp), "doctor1")); //change name later
			temp = incrementTime(temp);
			System.out.println(temp);
		}
		
		return slot;
	}
	
	public SlotC buildDoc1Appointment(String startTime, String endTime, String client) {
		SlotC slot = new SlotC();
		
		int intervals = timeToInterval(startTime, endTime);
		String temp = startTime;
		for(int i = 0; i < intervals; i++) {
			slot.addInterval(new AppointmentInterval(temp, incrementTime(temp), "doctor1", client));
			temp = incrementTime(temp);
		}
		
		return slot;
	}
	
	public SlotC buildDoc2Available(String startTime, String endTime) { //change if needed (slotC because its the slot in controller)
		SlotC slot = new SlotC();
		
		int intervals = timeToInterval(startTime, endTime);
		String temp = startTime;
		for(int i = 0; i < intervals; i++) {
			slot.addInterval(new AvailableInterval(temp, incrementTime(temp), "doctor2"));
			temp = incrementTime(temp);
		}
		
		return slot;
	}
	
	public SlotC buildDoc2Appointment(String startTime, String endTime, String client) {
		SlotC slot = new SlotC();
		
		int intervals = timeToInterval(startTime, endTime);
		String temp = startTime;
		for(int i = 0; i < intervals; i++) {
			slot.addInterval(new AppointmentInterval(temp, incrementTime(temp), "doctor2", client));
			temp = incrementTime(temp);
		}
		
		return slot;
	}
	
	private int timeToInterval(String start, String end) {
		int intervals = 0;
		int startHour = Integer.parseInt(start.split(":")[0]);
		int endHour = Integer.parseInt(end.split(":")[0]);
		int startMin = Integer.parseInt(start.split(":")[1]);
		int endMin = Integer.parseInt(end.split(":")[1]);
		
		intervals = (endHour - startHour) * 2;
		
		if(endMin != startMin) {
			intervals++;
		}
		
		return intervals;
	}
	
	private String incrementTime(String end) {
		
		int endHour = Integer.parseInt(end.split(":")[0]);
		int endMin = Integer.parseInt(end.split(":")[1]);
		String result = null;
		
		if(endMin == 30) {
			endMin = 0;
			endHour += 1;
		}else
			endMin += 30;
		
		if(endMin == 0)
			result = endHour + ":00";
		else
			result = endHour + ":" +endMin;
		
		return result;
	}
	
}
