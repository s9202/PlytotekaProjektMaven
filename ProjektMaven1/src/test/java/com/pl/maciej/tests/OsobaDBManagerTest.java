package com.pl.maciej.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.pl.maciej.prog.KolekcjaPlyt;
import com.pl.maciej.prog.Osoba;
import com.pl.maciej.services.OsobaDBManager;
import com.pl.maciej.services.PlytaDBManager;

public class OsobaDBManagerTest {
	
	static final String BLAD_KOM_DOD_OSOBY 	= "Niepoprawne dodawanie lub wyszukiwanie osoby";
	static final String BLAD_KOM_USU_OSOB 	= "Niepoprawne usuwanie osob, lub dodawanie/usuwanie osob.";
	
	static	long			id;				
	static	String			imie;
	static	String			nazwisko;
			OsobaDBManager	osobaDb;
			PlytaDBManager	plytaDb;
			KolekcjaPlyt	plytoteka;
			Osoba			osoba;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		id			= 100000;
		imie		= "Jan";
		nazwisko	= "Kowalski";
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		imie		= null;
		nazwisko	= null;
	}

	@Before
	public void setUp() throws Exception {
		osobaDb = new OsobaDBManager();
		plytaDb = new PlytaDBManager();
	}

	@After
	public void tearDown() throws Exception {
		osobaDb = null;
		plytaDb = null;
	}
/*
	@Test
	public final void testOsobaDBManager() {
		fail("Not yet implemented"); // TODO
	}
*/
	@Test
	public final void testDodajOsobe() {
		osoba = new Osoba(imie, nazwisko, plytoteka, id);
		osobaDb.dodajOsobe(osoba);
		assertNotNull( BLAD_KOM_DOD_OSOBY, osobaDb.dajOsobe( id ) );
	}

	@Test
	public final void testUsunOsobe() {
		osoba = new Osoba(imie, nazwisko, plytoteka, id);
		osobaDb.usunOsobe(osoba, plytaDb);
		assertNull( BLAD_KOM_DOD_OSOBY, osobaDb.dajOsobe( id ) );
	}
/*	
	@Test
	public final void testUsunWszystkieOsoby() {
		id = id + 1;
		osoba = new Osoba(imie, nazwisko, plytoteka, id);
		osobaDb.dodajOsobe(osoba);
		plytaDb.usunWszystkiePlyty();
		osobaDb.usunWszystkieOsoby();
		assertTrue( BLAD_KOM_DOD_OSOBY, osobaDb.dajWszystkieOsoby().isEmpty());
	}
*/
}
