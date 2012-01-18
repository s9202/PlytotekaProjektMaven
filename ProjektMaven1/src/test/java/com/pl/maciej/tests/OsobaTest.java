package com.pl.maciej.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.pl.maciej.prog.KolekcjaPlyt;
import com.pl.maciej.prog.Osoba;
import com.pl.maciej.prog.Plyta;
import com.pl.maciej.prog.PlytaGatunek;


public class OsobaTest {
	final static String	KOM_BLAD_NAZWISKO	= "Niepoprawnie nadane (przez konstruktor) lub odczytane nazwisko!";
	final static String	KOM_BLAD_IMIE		= "Niepoprawnie nadane (przez konstruktor) lub odczytane imi�!";
	final static String	KOM_BLAD_PLYTA		= "Niepoprawnie dodana lub wyszukana p�yta!";
	final static String	BLAD_KOM_KONSTR		= "Niepoprawny konstruktor lub jedna z metod pobrania: imienia, nazwiska, liczby p�yt w kolekcji!";
	static final String BLAD_KOM_DOD_PLYT 	= "Niepoprawne dodawanie lub wyszukiwanie p�yty!";
	static final String BLAD_KOM_ROK_PLYT 	= "Dodanie p�yty z niepoprawnym rokiem wydania lub niepoprawne wyszukiwanie p�yty!";
	static final String BLAD_KOM_WYK_PLYT 	= "Dodanie p�yty z nieokre�lonym wykonawc� lub niepoprawne wyszukiwanie p�yty!";
	static final String BLAD_KOM_TYT_PLYT 	= "Dodanie p�yty z pustym tytu�em lub niepoprawne wyszukiwanie p�yty!";
	static final String BLAD_KOM_USU_PLYT 	= "Niepoprawne usuwanie plyty, lub dodawanie/usuwanie plyt.";
	static final String BLAD_KOM_SZU_PLYT 	= "Niepoprawne szukanie plyty, lub dodawanie plyt.";
	static final String BLAD_KOM_EDY_PLYT 	= "Niepoprawne edytowanie plytu, lub wyszukiwanie plyt.";
	
	static	long			id;
	static	String			imie;
	static	String			nazwisko;
			Plyta			plyta;
	static	String			wykonawca;
	static	String			wykonawca2;
	static	String			tytul;
	static	String			tytul2;
	static	PlytaGatunek	gatunek;
	static	int				rokWydania;
	static	int				rokWydania2;
			KolekcjaPlyt	plytoteka;
			Osoba			osoba; 

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		id			= 1;
		imie		= "Jan";
		nazwisko	= "Kowalski";
		wykonawca	= "Dire Straits";
		wykonawca2	= null;
		tytul		= "Sultans of swing";
		tytul2		= "";
		gatunek		= PlytaGatunek.rock;
		rokWydania	= 1978;
		rokWydania2 = 1798;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		imie		= null;
		nazwisko	= null;
		wykonawca	= null;
		tytul		= null;
		tytul2		= null;
		rokWydania	= 0;
		rokWydania2	= 0;
	}

	@Before
	public void setUp() throws Exception {
		osoba 		= new Osoba( imie, nazwisko, plytoteka );
	}

	@After
	public void tearDown() throws Exception {
		osoba		= null;
	}

	@Test
	public final void testOsoba() {
		plytoteka	= new KolekcjaPlyt();
		osoba		= new Osoba( imie, nazwisko, plytoteka );
		assertTrue( BLAD_KOM_KONSTR, osoba.dajImie().equals(imie) && osoba.dajNazwisko().equals(nazwisko) && osoba.liczbaPlyt() == plytoteka.liczbaPlyt());
	}

	@Test
	public final void testDodajPlyte() {
		osoba.dodajPlyte(id, wykonawca, tytul, gatunek, rokWydania);
		assertTrue( BLAD_KOM_DOD_PLYT, osoba.wyszukajPlyte(wykonawca, tytul, gatunek, rokWydania) >= 0 );
	}
	@Test
	public final void testDodajPlyte_RokWydania() {
		osoba.dodajPlyte(id, wykonawca, tytul, gatunek, rokWydania2);
		assertFalse( BLAD_KOM_ROK_PLYT, osoba.wyszukajPlyte(wykonawca, tytul, gatunek, rokWydania2) >= 0 );
	}
/*
	@Test
	public final void testDodajPlyte_Tytul() {
		osoba.dodajPlyte(wykonawca, tytul2, gatunek, rokWydania);
		assertFalse( BLAD_KOM_TYT_PLYT, osoba.wyszukajPlyte(wykonawca, tytul2, gatunek, rokWydania) >= 0 );
	}
*/
	@Test
	public final void testUsunPlyteStringStringPlytaGatunekInt() {
		osoba.dodajPlyte(id, wykonawca, tytul, gatunek, rokWydania);
		osoba.usunPlyte(wykonawca, tytul, gatunek, rokWydania);
		assertFalse( BLAD_KOM_USU_PLYT, osoba.wyszukajPlyte(wykonawca, tytul, gatunek, rokWydania) >= 0 );
	}

	@Test
	public final void testWyszukajPlyte() {
		osoba.dodajPlyte(id, wykonawca, tytul, gatunek, rokWydania);
		assertTrue( BLAD_KOM_SZU_PLYT, osoba.wyszukajPlyte(wykonawca, tytul, gatunek, rokWydania) == 0 );
	}

	@Test
	public final void testEdytujPlyteStringStringPlytaGatunekIntStringStringPlytaGatunekInt() {
		osoba.dodajPlyte(id, wykonawca, tytul, gatunek, rokWydania);
		osoba.edytujPlyte(wykonawca, tytul, gatunek, rokWydania, id, wykonawca, tytul, gatunek, rokWydania2);
		assertFalse( BLAD_KOM_EDY_PLYT, osoba.wyszukajPlyte(wykonawca, tytul, gatunek, rokWydania2) != 0 );
	}

	@Test
	public final void testUstawImie() {
		assertTrue( KOM_BLAD_IMIE, osoba.dajImie().equals( imie ) );
	}

	@Test
	public final void testUstawNazwisko() {
		assertTrue( KOM_BLAD_NAZWISKO, osoba.dajNazwisko().equals( nazwisko ) );
	}

	@Test
	public final void testDajImie() {
		assertTrue( KOM_BLAD_IMIE, osoba.dajImie().equals( imie ) );
	}

	@Test
	public final void testDajNazwisko() {
		assertTrue( KOM_BLAD_NAZWISKO, osoba.dajNazwisko().equals( nazwisko ) );
	}

}
