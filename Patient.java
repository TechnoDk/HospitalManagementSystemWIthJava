package com.HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
	private Connection connection;
	private Scanner scanner;

	public Patient(Connection connection, Scanner scanner) {
		this.connection = connection;
		this.scanner = scanner;
	}

	public void addPatient() {
		System.out.println("Enter patient Name:");
		String name = scanner.next();
		System.out.println("Enter patient age:");
		int age = scanner.nextInt();
		System.out.println("Enter Patient Gender:");
		String gender = scanner.next();

		try {
			String query = "INSERT INTO patients(name,age,gender) VALUES (?,?,?)";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, name);
			ps.setInt(2, age);
			ps.setString(3, gender);

			int affectrow = ps.executeUpdate();
			if (affectrow > 0) {
				System.out.println("Patient added successfully !!");
			} else {
				System.out.println("failed to add patient!!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void viewPatients() {
		String query = "select * from patients";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet result = ps.executeQuery();
			System.out.println("Patients: ");
			System.out.println("+-----------+-------------+------+------------+");
			System.out.println("|Patient Id | Name        | Age  | Gender     |");
			System.out.println("+-----------+-------------+------+------------+");
			while (result.next()) {
				int id = result.getInt("id");
				String name = result.getString("name");
				int age = result.getInt("age");
				String gender = result.getString("gender");
				System.out.printf("| %-9s | %-11s | %-4s | %-10s |\n",id,name,age,gender);
				System.out.println(
						"+-----------+-------------+------+------------+");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean getPatientById(int id) {
		String query = "SELECT * FROM patients WHERE id=?";
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
