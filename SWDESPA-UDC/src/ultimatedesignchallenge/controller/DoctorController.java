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
import ultimatedesignchallenge.model.Appointment;
import ultimatedesignchallenge.model.Doctor;
import ultimatedesignchallenge.model.Recurring;
import ultimatedesignchallenge.model.Slot;
import ultimatedesignchallenge.model.Slot_Doc;
import ultimatedesignchallenge.services.DoctorService;
import ultimatedesignchallenge.services.SlotService;
import ultimatedesignchallenge.services.Slot_DocService;

public class DoctorController {
	private Doctor doctor;
	private DoctorService dsv;
	private Slot_DocService sdocService;
	private Slot_Doc temp;
	
	public DoctorController(Doctor doctor, DoctorService dsv) {
		this.doctor = doctor;
		this.dsv = dsv;
	}
	
	public boolean createFree(LocalDateTime start, LocalDateTime end) {
		System.out.println("yes!!!!");
		System.out.println(start);
		System.out.println(end);
		SlotBuilder builder = new SlotBuilder();
		SlotService service = new SlotService();
		List<Slot> slots = builder.buildSlots(start, end);
		temp = new Slot_Doc();
		sdocService = new Slot_DocService();
		
		boolean result = false;
		
		for (Slot slotc : slots) {
			System.out.println(slotc);
			result = service.addSlotC(slotc);
			if (result) {
				temp.setSlotId(service.getId(slotc).getId());
				temp.setDoctorId(doctor.getDoctorId());
				sdocService.addSlot_Doc(temp);
			}
		}
		return result; //change this
	}
	
	public boolean createFreeRecurring(LocalDateTime start, LocalDateTime end, int option) {
		int tempo = 0;
		SlotBuilder builder = new SlotBuilder();
		SlotService service = new SlotService();
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
		
		temp = new Slot_Doc();
		sdocService = new Slot_DocService();
		
		for (Slot slotc : slots) {
			System.out.println(slotc);
			service.addSlotC(slotc, tempo);
			temp.setSlotId(service.getId(slotc).getId());
			temp.setDoctorId(doctor.getDoctorId());
			sdocService.addSlot_Doc(temp);
		}
		
		return false; //i don't understand why the one above is false but ill do the same
	}
	
	public boolean updateFree(Slot oldSlot, LocalDateTime newStart, LocalDateTime newEnd) {
		SlotService ssv = new SlotService();
		if (this.createFree(newStart, newEnd)) {
			ssv.deleteSlot(oldSlot.getId());
			return true;
		}
		return false;
	}
	
}
