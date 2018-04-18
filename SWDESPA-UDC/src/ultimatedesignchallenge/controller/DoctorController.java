package ultimatedesignchallenge.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ultimatedesignchallenge.CalendarDB;
import ultimatedesignchallenge.Doctor.DoctorModel;
import ultimatedesignchallenge.Doctor.DoctorThread;
import ultimatedesignchallenge.model.Recurring;
import ultimatedesignchallenge.model.Slot;
import ultimatedesignchallenge.model.Slot_Doc;

public class DoctorController {
	private DoctorModel model;
	
	public DoctorController(DoctorModel model) {
		this.model = model;
	}
	
	public void startThread() {
		DoctorThread dt = new DoctorThread(model);
		dt.start();
	}
	
	public boolean createFree(LocalDateTime start, LocalDateTime end) {
		System.out.println("yes!!!!");
		System.out.println(start);
		System.out.println(end);
		SlotBuilder builder = new SlotBuilder();
		
		List<Slot> slots = builder.buildSlots(start, end);
		Slot_Doc tempsd = new Slot_Doc();
		
		boolean result = false;
		
		for (Slot slotc : slots) {
			System.out.println(slotc);
			result = model.addSlot(slotc);
			if (result) {
				tempsd.setSlotId(model.getId(slotc).getId());
				tempsd.setDoctorId(model.getDoctor().getDoctorId());
				model.addSlot_Doc(tempsd);
			}
		}
		return result; //change this
	}
	
	public boolean createFreeRecurring(LocalDateTime start, LocalDateTime end, int option) {
		int tempo = 0;
		SlotBuilder builder = new SlotBuilder();
		List<Slot> slots = new ArrayList<Slot>();
		
//		System.out.println("\n\n\n Local Date Start "+start.toLocalDate());
//		System.out.println("\n\n\n Local Date after a week"+start.toLocalDate().plusWeeks(1));
//		System.out.println("\n Local Date End " +start.toLocalDate());
		
		
		Connection cnt = CalendarDB.getConnection();

        //create query
        String query = "INSERT INTO " + Recurring.TABLE + " VALUES(?)";

        try {
            //create prepared statement
            PreparedStatement ps = cnt.prepareStatement(query);

            //prepare the values
            ps.setNull(1, Types.NULL);

            //execute the update
            ps.executeUpdate();

            //close resources
            ps.close();
            

            System.out.println("[RECURRING] INSERT SUCCESS!");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("[RECURRING] INSERT FAILED!");
            e.printStackTrace();
        }
        
        query = "SELECT LAST_INSERT_ID() FROM " + Recurring.TABLE;

        try {
            //create prepared statement
            PreparedStatement ps = cnt.prepareStatement(query);
            //prepare the values
            ResultSet rs = ps.executeQuery();
            		
            rs.next();
            tempo = rs.getInt(1);
            //close resources
            ps.close();
            

            System.out.println("[RECURRING] LAST INSERT SUCCESS!");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("[RECURRING] LAST INSERT FAILED!");
            e.printStackTrace();
        }

		
		for(int i = 0; i <= option + 1; i++) {
			slots.addAll(builder.buildSlots(start, end));
			start = start.plusWeeks(1);
			end = end.plusWeeks(1);
		}
		
		Slot_Doc tempsd = new Slot_Doc();
		
		for (Slot slotc : slots) {
			System.out.println(slotc);
			model.addSlot(slotc, tempo);
			tempsd.setSlotId(model.getId(slotc).getId());
			tempsd.setDoctorId(model.getDoctor().getDoctorId());
			model.addSlot_Doc(tempsd);
		}
		
		return false; //i don't understand why the one above is false but ill do the same
	}
	
	public boolean updateFree(Slot oldSlot, LocalDateTime newStart, LocalDateTime newEnd) {
		if (model.isFree(oldSlot) && this.createFree(newStart, newEnd)) {
			model.deleteSlot(oldSlot.getId());
			return true;
		}
		return false;
	}
	
}
