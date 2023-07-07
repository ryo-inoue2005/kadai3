package service;

import java.sql.SQLException;

import common.DataBaseAcess;

public class RegisterTables {

	private final DataBaseAcess dba = new DataBaseAcess();

	public boolean hasData() throws ClassNotFoundException, SQLException {

		try {
			dba.connection();
			dba.setSql("SELECT unsei_code FROM unseimaster");
			return dba.hasData();
		} finally {
			dba.close();
		}
	}

	public void registerUnseimaster() throws ClassNotFoundException, SQLException {

		try {
			dba.connection();

			dba.setSql(
					"INSERT INTO UNSEIMASTER (UNSEI_NAME, CREATE_BY, CREATE_DATE) VALUES ('吉', 'Ryo.inoue', CURRENT_DATE)");

			System.out.println(dba.updateSql());

		} finally {
			dba.close();
		}

	}

}
