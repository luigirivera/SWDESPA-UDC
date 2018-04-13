package ultimatedesignchallenge.controller;

import java.util.ArrayList;
import java.util.List;

public class SlotC {
	private List<Interval> intervals = new ArrayList<Interval>();
	
	public void addInterval(Interval interval) {
		intervals.add(interval);
	}
	
	public List<Interval> getIntervals(){
		return intervals;
	}
}
