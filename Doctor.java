package com.HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
	
	private Connection connection;
	 
	
	 public Doctor(Connection connection) {
		 this.connection=connection;
		  
	 }
	public void viewDoctors() {
		String query = "select * from doctors";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet result = ps.executeQuery();
			System.out.println("Doctors: ");
			System.out.println("+-----------+------------------+----------------+");
			System.out.println("|Doctors Id | Name             | Specialization |");
			System.out.println("+-----------+------------------+----------------+");
			while (result.next()) {
				int id = result.getInt("id");
				String name = result.getString("name");
				 String specialization=result.getString("specialization");
				System.out.printf("| %-9s | %-16s | %-14s |\n",id,name,specialization);
				System.out.println(
						       "+-----------+------------------+---------------+");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean getDoctorById(int id) {
		String query = "SELECT * FROM doctors WHERE id=?";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet result = ps.executeQuery();
			if (result.next()) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}


}
