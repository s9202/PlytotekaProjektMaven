package com.pl.maciej.prog;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.pl.maciej.services.OsobaDBManager;
	

public class Main {
	
	public static String START_MSG = "--- START APLIKACJI --------------------------------------------- ";
	
	private static Logger logger= Logger.getLogger(Main.class);
	
	
	public static void main(String args[]) {
		
		PropertyConfigurator.configure("Log4J.properties");
		logger.error(START_MSG); // komunikat wpisywany do dziennika po to, aby oddzieli� komunikaty z kolejnych uruchomie� programu
									// poziom 'error' - aby wpis dotyczy� r�wnie� dziennika administratora, 
									// do kt�rego nie s� wpisywane logi typu 'info' czy 'warn' (wg ustawie� w Log4J.properties)  
		
		KolekcjaPlyt plyty;
		
		
		plyty = new KolekcjaPlyt();
		plyty.dodajPlyte( "Budka Suflera", "Akustycznie", PlytaGatunek.rock, 1998 );
		plyty.dodajPlyte( "Budka Suflera", "Nic nie boli, tak jak zycie", PlytaGatunek.rock, 1997);
		plyty.dodajPlyte( "Modern Talking", "Back For Good", PlytaGatunek.eurodisco, 1998);
		plyty.dodajPlyte( "Modern Talking", "Back For Good", PlytaGatunek.eurodisco, 1998);

		Osoba o1 = new Osoba("Andrzej", "Nowak", plyty);
		
		
		plyty = new KolekcjaPlyt();
		plyty.dodajPlyte( "The Prodigy", "Invaders Must Die", PlytaGatunek.techno, 2009);
		plyty.dodajPlyte( "Scooter", "Jumpin' All Over the World", PlytaGatunek.techno, 2008);
		plyty.dodajPlyte( "Gigi d'Agostino", "L'Amour Toujours", PlytaGatunek.techno, 1999);
	
		Osoba o2 = new Osoba("Jan", "Kluska", plyty);
		o2.dodajPlyte("Gigi d'Agostino", "Bla bla bla", PlytaGatunek.techno, 1997);
		
		o1.wyswietlPlyty();
		o2.wyswietlPlyty();
		
		OsobaDBManager db = new OsobaDBManager();
		db.dodajOsobe(o1);
		db.dodajOsobe(o2);
		
		for(Osoba osoba: db.dajWszystkieOsoby()) {
			System.out.println(osoba);
		}
		
	}

}
