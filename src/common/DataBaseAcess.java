package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DataBaseAcess {

	private Connection connection;
	private PreparedStatement preparedStatement;

	public void connection() throws ClassNotFoundException, SQLException {
		// ドライバ読み込み
		Class.forName("org.postgresql.Driver");
		this.connection = DriverManager.getConnection("jdbc:postgresql://localhost/uranai", "postgres", "aaaaaajh");
	}

	public void setSql(String sql) throws SQLException {
		this.preparedStatement = this.connection.prepareStatement(sql);
	}

	public void setData(int index, String data) throws SQLException {
		this.preparedStatement.setString(index, data);
	}

	public void setData(int index, int data) throws SQLException {
		this.preparedStatement.setInt(index, data);
	}

	public ResultSet selectSql() throws SQLException {
		return this.preparedStatement.executeQuery();
	}

	public int updateSql() throws SQLException {
		return this.preparedStatement.executeUpdate();
	}
	
	public boolean hasData() throws SQLException {
		return this.preparedStatement.execute();
	}

	public void close() throws SQLException {

		if (this.connection != null) {
			this.connection.close();
		}

		if (this.preparedStatement != null) {
			this.preparedStatement.close();
		}

	}

}
