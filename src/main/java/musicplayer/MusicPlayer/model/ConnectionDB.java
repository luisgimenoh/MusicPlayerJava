package musicplayer.MusicPlayer.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConnectionDB {
	
	private final static String URL = "jdbc:mysql://localhost:3306/music_player";
	private final static String USER = "root";
	private final static String PASSWORD = "";
	
	public static Statement getConnection() {
		
		try {
			
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement statement = connection.createStatement();
			
			return statement;
		
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return null;
		
		
	}
	
}
