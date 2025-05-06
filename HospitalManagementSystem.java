package com.HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {
	private static final String url = "jdbc:mysql://localhost:3306/hospital";
	private static final String username = "root";

	private static final String password = "DK12@sah";

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Scanner scanner = new Scanner(System.in);

		try {
			Connection connection = DriverManager.getConnection(url, username,
					password);
			Patient patient = new Patient(connection, scanner);
			Doctor doctor = new Doctor(connection);

			while (true) {
				System.out.println("HOSPITAL MANAGEMENT SYSTEM ");
				System.out.println("1. Add Patient");
				System.out.println("2. View Patients");
				System.out.println("3. View Doctors");

				System.out.println("4. Book Apointment");
				System.out.println("5. Exit");
				System.out.println("Enter Your choice : ");
				int choice = scanner.nextInt();

				switch (choice) {
					case 1 :
						// Add Patient
						patient.addPatient();
						System.out.println();
						break;

					case 2 :
						// View Patient
						patient.viewPatients();
						System.out.println();
						break;
					case 3 :
						// View Doctors
						doctor.viewDoctors();
						System.out.println();
						break;
					case 4 :
						// Book Appointment
						bookAppointment(patient, doctor, connection, scanner);
						System.out.println();
						break;

					case 5 :
						// Exit
						System.out.println("Thank for Using !!");
						return;
					default :
						System.out.println("Enter Valid choice!!");
						break;

				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void bookAppointment(Patient patient, Doctor doctor,
			Connection connection, Scanner scanner) {
		System.out.println("Enter patient id: ");
		int patientId = scanner.nextInt();
		System.out.println("Enter Doctor id: ");
		int doctorId = scanner.nextInt();
		System.out.println("Enter appointment date(YYYY-MM-DD): ");
		String appointmentDate = scanner.next();

		if (patient.getPatientById(patientId)
				&& doctor.getDoctorById(doctorId)) {

			if (checkDoctorAvailability(doctorId, appointmentDate,
					connection)) {
				String appointmentQuery = "INSERT INTO appointments(patient_id,doctor_id,appointment_date) VALUES(?,?,?)";

				try {
					PreparedStatement ps = connection
							.prepareStatement(appointmentQuery);
					ps.setInt(1, patientId);
					ps.setInt(2, doctorId);
					ps.setString(3, appointmentDate);

					int rowaffect = ps.executeUpdate();
					if (rowaffect >= 0) {
						System.out.println("Appointment Booked!!");
					} else {
						System.out.println("Failed to book appointment");
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}

			} else {
				System.out.println("Doctor is not available on this date!!");
			}

		} else {
			System.out.println("Either doctor or patient doesn't exist!!");
		}

	}
	public static boolean checkDoctorAvailability(int doctorId,
			String appointmentDate, Connection connection) {

		String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id=? AND appointment_date=?";
		try {

			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, doctorId);
			ps.setString(2, appointmentDate);
			ResultSet set = ps.executeQuery();
			if (set.next()) {
				int count = set.getInt(1);
				if (count == 0) {
					return true;
				} else {
					return false;
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
