package com.topics.appointment.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.topics.appointment.model.bean.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

	
	   @Query(value = "SELECT a.appointment_id, a.member_id, a.pet_id, "
			    + "CONVERT(VARCHAR, a.appointment_date, 23) AS appointmentDate, "
	            + "a.appointment_timeslot, a.appointment_total, "
	            + "CAST(a.appointment_status AS INT) AS appointmentStatus, "  
	            + "CAST(a.payment_status AS INT) AS paymentStatus, "  
	            + "STRING_AGG(i.item_name, ', ') AS serviceNames, "
	            + "STRING_AGG(sp.package_description, ', ') AS additionalPackages "
	            + "FROM Appointments a "
	            + "LEFT JOIN Member m ON m.member_id = a.member_id "
	            + "LEFT JOIN Pets p ON p.pet_id = a.pet_id "
	            + "LEFT JOIN item_details id ON id.appointment_id = a.appointment_id "
	            + "LEFT JOIN Items i ON i.item_id = id.item_id "
	            + "LEFT JOIN package_details pd ON pd.appointment_id = a.appointment_id "
	            + "LEFT JOIN packages sp ON sp.package_id = pd.package_id "
	            + "WHERE m.phone= :phone "
	            + "GROUP BY a.appointment_id, a.member_id, a.pet_id, "
	            + "CONVERT(VARCHAR, a.appointment_date, 23), "
	            + "a.appointment_timeslot, a.appointment_total, "
	            + "CAST(a.appointment_status AS INT), "
	            + "CAST(a.payment_status AS INT)", 
	            nativeQuery = true)
	    List<Object[]> findAppointmentsByPhoneNumber(@Param("phone")String phone);

	    @Query(value = "SELECT  a.appointment_id, a.member_id, a.pet_id, "
	    		+ "CONVERT(VARCHAR, a.appointment_date, 23) AS appointmentDate, "
	            + "a.appointment_timeslot, a.appointment_total, "
	    		+ "CAST(a.appointment_status AS INT) AS appointmentStatus, "  
	            + "CAST(a.payment_status AS INT) AS paymentStatus, "  
	            + "STRING_AGG(i.item_name, ', ') AS serviceNames, "
	            + "STRING_AGG(sp.package_description, ', ') AS additionalPackages "
	            + "FROM Appointments a "
	            + "LEFT JOIN Member m ON m.member_id = a.member_id "
	            + "LEFT JOIN Pets p ON p.pet_id = a.pet_id "
	            + "LEFT JOIN item_details id ON id.appointment_id = a.appointment_id "
	            + "LEFT JOIN Items i ON i.item_id = id.item_id "
	            + "LEFT JOIN package_details pd ON pd.appointment_id = a.appointment_id "
	            + "LEFT JOIN packages sp ON sp.package_id = pd.package_id "
	            + "GROUP BY a.appointment_id, a.member_id, a.pet_id, "
	            + "CONVERT(VARCHAR, a.appointment_date, 23), "
	            + "a.appointment_timeslot, a.appointment_total, "
	            + "CAST(a.appointment_status AS INT), "
	            + "CAST(a.payment_status AS INT)",nativeQuery = true)
	    List<Object[]> findAllAppointments();
	    
	    @Query(value = "SELECT a.appointment_id, a.member_id, a.pet_id, "
                + "CONVERT(VARCHAR, a.appointment_date, 23) AS appointmentDate, "
                + "a.appointment_timeslot, a.appointment_total, "
                + "CAST(a.appointment_status AS INT) AS appointmentStatus, "
                + "CAST(a.payment_status AS INT) AS paymentStatus, "
                + "STRING_AGG(i.item_name, ', ') AS serviceNames, "
                + "STRING_AGG(sp.package_description, ', ') AS additionalPackages "
                + "FROM Appointments a "
                + "LEFT JOIN Member m ON m.member_id = a.member_id "
                + "LEFT JOIN Pets p ON p.pet_id = a.pet_id "
                + "LEFT JOIN item_details id ON id.appointment_id = a.appointment_id "
                + "LEFT JOIN Items i ON i.item_id = id.item_id "
                + "LEFT JOIN package_details pd ON pd.appointment_id = a.appointment_id "
                + "LEFT JOIN packages sp ON sp.package_id = pd.package_id "
                + "WHERE a.appointment_id = :appointment "
                + "GROUP BY a.appointment_id, a.member_id, a.pet_id, "
                + "CONVERT(VARCHAR, a.appointment_date, 23), "
                + "a.appointment_timeslot, a.appointment_total, "
                + "CAST(a.appointment_status AS INT), "
                + "CAST(a.payment_status AS INT)", 
                nativeQuery = true)
	    List<Object[]> findAppointmentById(@Param("appointment") int appointment);
	    
	    @Query("SELECT a.appointmentTimeslot FROM Appointment a WHERE a.appointmentDate = :date")
	    List<String> findBookedTimeslotsByDate(@Param("date") String appointmentDate);
	    
	    boolean existsByAppointmentDateAndAppointmentTimeslot(String appointmentDate, String appointmentTimeslot);
	    
	    @Query(value = "SELECT a.appointment_id, " +
	               "p.pet_name, " +
	               "CONVERT(VARCHAR, a.appointment_date, 23) AS appointmentDate, " +
	               "a.appointment_timeslot, " +
	               "a.appointment_total, " +
	               "CAST(a.appointment_status AS INT) AS appointmentStatus, "+
	               "CAST(a.payment_status AS INT) AS paymentStatus, " +
	               "STRING_AGG(i.item_name, ', ') AS serviceNames, " +
	               "STRING_AGG(sp.package_description, ', ') AS additionalPackages " +
	               "FROM appointments a " +
	               "JOIN pets p ON a.pet_id = p.pet_id " +
	               "LEFT JOIN item_details id ON id.appointment_id = a.appointment_id " +
	               "LEFT JOIN items i ON i.item_id = id.item_id " +
	               "LEFT JOIN package_details pd ON pd.appointment_id = a.appointment_id " +
	               "LEFT JOIN packages sp ON sp.package_id = pd.package_id " +
	               "WHERE a.member_id = :memberId AND a.appointment_status = :appointmentStatus " +
	               "GROUP BY a.appointment_id, p.pet_name, a.appointment_date, a.appointment_timeslot, a.appointment_total, a.appointment_status, a.payment_status", 
	       nativeQuery = true)
	List<Object[]> findAppointmentDetailsByOwner_MemberIdAndAppointmentStatus(
	    @Param("memberId") int memberId, 
	    @Param("appointmentStatus") int appointmentStatus);

}
