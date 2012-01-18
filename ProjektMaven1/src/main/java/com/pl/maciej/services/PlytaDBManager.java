package com.pl.maciej.services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.pl.maciej.prog.Osoba;
import com.pl.maciej.prog.Plyta;

public class PlytaDBManager {
	private Connection polaczenie;
	private Statement polecenie;
	private PreparedStatement polecenieDodaniaPlyty;
	private PreparedStatement polecenieOdczytuPlyt;
	private PreparedStatement polecenieUsunieciaWszystkich;
	
	public PlytaDBManager()
	{
		try {
			Properties wlasciwosci = new Properties();
			
			try {
				wlasciwosci.load(ClassLoader.getSystemResourceAsStream("com/pl/maciej/jdbc.properties"));
			}
			catch (IOException e) {
				System.out.println("1 blad bazy danych:" + e.getMessage());
			}
			
			polaczenie = DriverManager.getConnection(wlasciwosci.getProperty("url"));
					
			polecenie = polaczenie.createStatement();
			boolean tablicaPlytIstnieje = false;
			
			ResultSet rs = polaczenie.getMetaData().getTables(null, null, null, null);
			
			while(rs.next() && !tablicaPlytIstnieje) {
				if("plyta".equalsIgnoreCase(rs.getString("TABLE_NAME")))
					tablicaPlytIstnieje = true;
			}
						
			if(!tablicaPlytIstnieje) {

				polecenie.executeUpdate("" +
					"CREATE TABLE plyta(" +
					"id bigint IDENTITY PRIMARY KEY," +
					"wykonawca varchar(30)," +
					"tytul varchar(50)," +
					"gatunek int," +
					"rok int," + 
					"id_osoba bigint, " + "CONSTRAINT id_osoba_fk FOREIGN KEY (id_osoba) REFERENCES osoba (id)" +
					"" +
					")");
			}
			
			polecenieDodaniaPlyty = polaczenie.prepareStatement("" +
					"INSERT INTO plyta (id, wykonawca, tytul, gatunek, rok, id_osoba) VALUES (?, ?, ?, ?, ?, ?)" +
					"");
			
			polecenieOdczytuPlyt = polaczenie.prepareStatement("" +
					"SELECT * FROM plyta" +
					" WHERE id_osoba = ?" +
					"");
			
			polecenieUsunieciaWszystkich = polaczenie.prepareStatement("" +
					"DELETE FROM plyta" +
					"");
		}
		catch (SQLException e) {
			System.out.println("2 blad bazy danych:" + e.getMessage());
		}
	}
	
	public void dodajPlyte(Plyta p, Osoba o ) {
		try {
			polecenieDodaniaPlyty.setLong(1, p.dajIdPlyty());
			polecenieDodaniaPlyty.setString(2, p.wykonawca());
			polecenieDodaniaPlyty.setString(3, p.tytul());
			polecenieDodaniaPlyty.setInt(4, p.gatunekInt());
			polecenieDodaniaPlyty.setInt(5, p.rok());
			polecenieDodaniaPlyty.setLong(6, o.dajId());
			polecenieDodaniaPlyty.executeUpdate();
		} 
		catch (SQLException e) {
			System.out.println("3 blad bazy danych:" + e.getMessage());
		}
	}
	
	public void dodajKolekcjePlyt( Osoba o ) {
		for( Plyta plyta : o.dajPlyty() ) 
			dodajPlyte( plyta, o );
	}
	
	
	
	public List<Plyta> dajWszystkiePlyty(Osoba o) {
		
		List<Plyta> listaPlyt = new ArrayList<Plyta>();
		try {
			ResultSet rs; 
			polecenieOdczytuPlyt.setLong(1, o.dajId() );
			rs = polecenieOdczytuPlyt.executeQuery();
			while(rs.next()) {
				listaPlyt.add(new Plyta(rs.getLong("id"), rs.getString("wykonawca"), rs.getString("tytul"), 
				rs.getInt("gatunek"), rs.getInt("rok")));
			}
		}
		catch (SQLException e) {
			System.out.println("4 blad bazy danych:" + e.getMessage());
		}
		return listaPlyt;
		
	}
	
	public void usunWszystkiePlyty() {
		try {
			polecenieUsunieciaWszystkich.executeUpdate();
		} catch (SQLException e) {
			System.out.println("5 blad bazy danych:" + e.getMessage());
		}
	}
}
