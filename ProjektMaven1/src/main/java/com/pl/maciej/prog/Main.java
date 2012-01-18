package com.pl.maciej.prog;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.pl.maciej.services.OsobaDBManager;
import com.pl.maciej.services.PlytaDBManager;
	

public class Main {
	
	public static String START_MSG = "--- START APLIKACJI --------------------------------------------- ";
	
	private static Logger logger= Logger.getLogger(Main.class);
	
	
	public static void main(String args[]) {
		
		PropertyConfigurator.configure("Log4J.properties");
		logger.error(START_MSG); // komunikat wpisywany do dziennika po to, aby oddzieliæ komunikaty z kolejnych uruchomieñ programu
									// poziom 'error' - aby wpis dotyczy³ równie¿ dziennika administratora, 
									// do którego nie s¹ wpisywane logi typu 'info' czy 'warn' (wg ustawieñ w Log4J.properties)  
		
		KolekcjaPlyt plyty;
		
		
		plyty = new KolekcjaPlyt();
		plyty.dodajPlyte( 1, "Budka Suflera", "Akustycznie", PlytaGatunek.rock, 1998 );
		plyty.dodajPlyte( 2, "Budka Suflera", "Nic nie boli, tak jak zycie", PlytaGatunek.rock, 1997);
		plyty.dodajPlyte( 3, "Modern Talking", "Back For Good", PlytaGatunek.eurodisco, 1998);

		Osoba o1 = new Osoba("Andrzej", "Nowak", plyty, 1);
		
		
		plyty = new KolekcjaPlyt();
		plyty.dodajPlyte( 4, "The Prodigy", "Invaders Must Die", PlytaGatunek.techno, 2009);
		plyty.dodajPlyte( 5, "Scooter", "Jumpin' All Over the World", PlytaGatunek.techno, 2008);
		plyty.dodajPlyte( 6, "Gigi d'Agostino", "L'Amour Toujours", PlytaGatunek.techno, 1999);
	
		Osoba o2 = new Osoba("Jan", "Kluska", plyty, 2);
		o2.dodajPlyte( 7, "Gigi d'Agostino", "Bla bla bla", PlytaGatunek.techno, 1997);
		
		o1.wyswietlPlyty();
		o2.wyswietlPlyty();
		
		System.out.println("\nKoniec programu, poczatek bazy.\n");
//		Plyta plyta1 = new Plyta( "Budka Suflera", "Akustycznie", PlytaGatunek.rock, 1998 );
		
		OsobaDBManager odb = new OsobaDBManager();
		PlytaDBManager pdb = new PlytaDBManager();
		
		odb.dodajOsobe(o1);
		odb.dodajOsobe(o2);
		
		pdb.dodajKolekcjePlyt( o1 );
		pdb.dodajKolekcjePlyt( o2 );
		
		for( Osoba osoba: odb.dajWszystkieOsoby() ) {
			System.out.println("Plyty nalezace do: " + osoba);
			for( Plyta plyta: pdb.dajWszystkiePlyty(osoba) ) {
				System.out.println( plyta.plyta() );
			}
		}
	}

}
