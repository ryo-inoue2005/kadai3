package main;

import java.sql.ResultSet;
import java.sql.SQLException;

import common.DataBaseAcess;
import service.RegisterTables;

public class Main {

	public static void main(String[] args) {

		ResultSet resultSet = null;
		DataBaseAcess dba = new DataBaseAcess();

		try {
			RegisterTables registerTables = new RegisterTables();
			
			System.out.println(registerTables.hasData());

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				dba.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
