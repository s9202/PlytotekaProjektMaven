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

public class OsobaDBManager {
	
	private Connection polaczenie;
	private Statement polecenie;
	private PreparedStatement polecenieDodaniaOsoby;
	private PreparedStatement polecenieOdczytuOsob;
	private PreparedStatement polecenieUsunieciaWszystkich;
	private PreparedStatement polecenieUsunieciaOsoby;
	private PreparedStatement polecenieOdczytuOsoby;
	
	
	public OsobaDBManager()
	{
		try {
			Properties wlasciwosci = new Properties();
			
			try {
				wlasciwosci.load(ClassLoader.getSystemResourceAsStream("com/pl/maciej/jdbc.properties"));
			}
			catch (IOException e) {
				System.out.println("6 blad bazy danych:" + e.getMessage());
			}
			
			polaczenie = DriverManager.getConnection(wlasciwosci.getProperty("url"));
					
			polecenie = polaczenie.createStatement();
			boolean tablicaOsobIstnieje = false;
			
			ResultSet rs = polaczenie.getMetaData().getTables(null, null, null, null);
			
			while(rs.next() && !tablicaOsobIstnieje) {
				if("osoba".equalsIgnoreCase(rs.getString("TABLE_NAME")))
					tablicaOsobIstnieje = true;
			}
						
			if(!tablicaOsobIstnieje)
			{
				polecenie.executeUpdate("" +
					"CREATE TABLE osoba(" +
					"id bigint IDENTITY PRIMARY KEY," +
					"imie varchar(20)," +
					"nazwisko varchar(20)," +
					"" +
					")");
			}
			
			polecenieDodaniaOsoby = polaczenie.prepareStatement("" +
					"INSERT INTO osoba (id, imie, nazwisko) VALUES (?, ?, ?)" +
					"");
			
			polecenieOdczytuOsob = polaczenie.prepareStatement("" +
					"SELECT * FROM osoba" +
					"");
						
			polecenieUsunieciaWszystkich = polaczenie.prepareStatement("" +
					"DELETE FROM osoba" +
					"");
			
			polecenieUsunieciaOsoby = polaczenie.prepareStatement("" +
					"DELETE FROM osoba WHERE id = ?" +
					"");
			
			polecenieOdczytuOsoby = polaczenie.prepareStatement("" +
					"SELECT * FROM osoba WHERE id = ?" +
					"");
			
		}
		catch (SQLException e) {
			System.out.println("7 blad bazy danych:" + e.getMessage());
		}
	}
	
	public void dodajOsobe(Osoba o) {
		try {
			polecenieDodaniaOsoby.setLong(1, o.dajId());
			polecenieDodaniaOsoby.setString(2, o.dajImie());
			polecenieDodaniaOsoby.setString(3, o.dajNazwisko());
			polecenieDodaniaOsoby.executeUpdate();
		} 
		catch (SQLException e) {
			System.out.println("dodajOsobe (id=" + Long.toString(o.dajId())  + ") - blad bazy danych:" + e.getMessage());
			//e.printStackTrace();
		}
	
	}
	
	public Osoba dajOsobe( long id ) {
		Osoba osoba = null;
		try {
			polecenieOdczytuOsoby.setLong(1, id);
			ResultSet rs = polecenieOdczytuOsoby.executeQuery();
			if(rs.next()) {
				osoba = new Osoba( rs.getString("imie"), rs.getString("nazwisko"), null, rs.getLong("id") );
			}
		}
		catch (SQLException e) {
			System.out.println("dajOsobe - blad bazy danych:" + e.getMessage());
		}
			
		return osoba;
	}
	
	public List<Osoba> dajWszystkieOsoby() {
		
		List<Osoba> listaOsob = new ArrayList<Osoba>();
		try {
			ResultSet rs = polecenieOdczytuOsob.executeQuery();
			while(rs.next()) {
				listaOsob.add(new Osoba(rs.getString("imie"), rs.getString("nazwisko"), null, rs.getLong("id")));
			}
		}
		catch (SQLException e) {
			System.out.println("9 blad bazy danych:" + e.getMessage());
		}
		return listaOsob;
		
	}
	
	public void usunOsobe( Osoba o, PlytaDBManager plytaDB ) {
		try {
			if( o != null ) {
				if( plytaDB != null ) {
					for( Plyta p : o.dajPlyty() )
						plytaDB.usunPlyte( p );
				}
				polecenieUsunieciaOsoby.setLong(1, o.dajId());
				polecenieUsunieciaOsoby.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println("usunPlyte - blad bazy danych:" + e.getMessage());
	}
	}
	
	public void usunWszystkieOsoby() {
		try {
			PlytaDBManager plyta = new PlytaDBManager();
			plyta.usunWszystkiePlyty();
			polecenieUsunieciaWszystkich.executeUpdate();
		} catch (SQLException e) {
			System.out.println("10 blad bazy danych:" + e.getMessage());
		}
	}

}
