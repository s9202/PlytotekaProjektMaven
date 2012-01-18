package com.pl.maciej.prog;

import java.util.List;


public class Osoba {
	
	private String 			imie;
	private	String 			nazwisko;
	private	KolekcjaPlyt 	plyty = new KolekcjaPlyt();
	private	long			id = -1;
	
	public Osoba(String par_imie, String par_nazwisko, KolekcjaPlyt par_plyty) {
		imie 		= par_imie;
		nazwisko 	= par_nazwisko;
		if ( par_plyty != null )
			plyty 	= par_plyty;
	}
	
	public Osoba(String par_imie, String par_nazwisko, KolekcjaPlyt par_plyty, long par_id) {
		this(par_imie, par_nazwisko, par_plyty);
		id = par_id;
	}
	
	public void dodajPlyte(long pId, String pWykonawca, String pTytul, PlytaGatunek pGatunek, int pRok) {
		plyty.dodajPlyte(pId, pWykonawca, pTytul, pGatunek, pRok);
	}
	
	public void usunPlyte( int pNumerPlyty ) {
		plyty.usunPlyte( pNumerPlyty );
	}
	
	public void usunPlyte( String pWykonawca, String pTytul, PlytaGatunek pGatunek, int pRok ) {
		plyty.usunPlyte(pWykonawca, pTytul, pGatunek, pRok );
	}
	
	public int wyszukajPlyte( String pWykonawca, String pTytul, PlytaGatunek pGatunek, int pRok ) {
		return plyty.wyszukajPlyte( pWykonawca, pTytul, pGatunek, pRok );
	}
	
	public void edytujPlyte( int pNumerPlyty, long pId, String nowyWyk, String nowyTyt, PlytaGatunek nowyGat, int nowyRok ) {
		plyty.edytujPlyte( pNumerPlyty, pId, nowyWyk, nowyTyt, nowyGat, nowyRok );
	}
	
	public void edytujPlyte( String pWykonawca, String pTytul, PlytaGatunek pGatunek, int pRok,
			 long nowyId, String nowyWyk, String nowyTyt, PlytaGatunek nowyGat, int nowyRok ) {
		plyty.edytujPlyte( pWykonawca, pTytul, pGatunek, pRok, nowyId, nowyWyk, nowyTyt, nowyGat, nowyRok );
	}
	
	public void wyswietlPlyty()
	{
		System.out.println("\nPLYTY W POSIADANIU OSOBY: " + imie + " " + nazwisko);
		Plyta robPlyty;
		for (int i = 0; i< plyty.liczbaPlyt(); i++ ) {
			robPlyty = plyty.dajPlyte(i);
			robPlyty.wyswietlPlyte();
		}
	}

	public void ustawImie( String nImie ) {
		imie = nImie;
	}
	
	public void ustawNazwisko( String nNazwisko ) {
		imie = nNazwisko;
	}
	
	public String dajImie() {
		return imie;
	}
	
	public String dajNazwisko() {
		return nazwisko;
	}
	
	public int	liczbaPlyt() {
		return	plyty.liczbaPlyt();
	}
	
	public String toString() {
		return imie + " " + nazwisko;
	}
	
	public	long	dajId() {
		return	id;
	}
	
	public	void ustawId( long nId ) {
		id	= nId;
	}
	
	public List<Plyta> dajPlyty() {
		return plyty.dajPlyty(); 
	}
}
