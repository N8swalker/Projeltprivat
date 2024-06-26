//von Tobias mit Unterstuetzung von Tim und Thomas
package Spiel;

import Gui.*;

public class Steuerung {

  // Attribute
  Player player[];
  String spielerNamen[];
  Feld feld;
  Wuerfel wuerfel;
  GUI2 gui2;
  int figurenPos[] = new int[16];
  private int aktSpieler;
  private int aktFigur;
  private int spieleranzahl;
  private int gewuerfelteZahl;
  private int ersterSpieler;
  private int aktPosFigur;
  private int altePosFigur;
  private int anzahlFigurenImFeld;
  private int anzahlFigurenImZiel;
  private int neueZielPosition;
  private int alteZielPosition;
  private boolean istImZiel;

  // Konstruktor
  public Steuerung(int initSpielerAnzahl, int initErsterSpieler, String initSpielerNamen[]) {
    spieleranzahl = initSpielerAnzahl;// Spieleranzahl wird uebergeben
    ersterSpieler = initErsterSpieler;// der ersteSpieler wird uebergeben
    System.out.println("Spieleranzahl: " + spieleranzahl);
    System.out.println("Erster Spieler: " + ersterSpieler);
    spielerNamen = initSpielerNamen;// Spielernamen erden uebergeben
    aktSpieler = ersterSpieler; // aktueller spieler wird auf den ersten Spieler gestzt da dieser anfaengt
    feld = new Feld();// Das Feld wird als Objekt erzeugt um darin die Positionenn zu berechnen
    player = new Player[spieleranzahl]; // Spieler werden erzeugt mit der notwendigen Spieleranzahl
    for (int i = 0; i < spieleranzahl; i++) {
      player[i] = new Player(i + 1);
    }
    gui2 = new GUI2(getSpieleranzahl(), getErsterSpieler(), this); // das Spielfeld erscheint auf der zweiten GUI
  }

  // Spieler waehlt eine Figur
  public void figurWahl(int figur) { // uebergibt gewuenshcte Figur
    setAktFigur(figur);
  }

  // Eine ausgewaehlte Figur wird bewegt
  public void laufen(int figur) {
    // vorheriges wird geholt:
    if(aktSpieler == 0){
        altePosFigur = player[aktSpieler].figur[aktFigur].getPosition();
    }else{
        altePosFigur = player[aktSpieler - 1].figur[aktFigur - 1].getPosition();
    }
     // die alte Position
    alteZielPosition = player[aktSpieler - 1].figur[aktFigur - 1].getPositionImZiel(); // die alte Position im Ziel
    // fehlervermeidung:
    if (altePosFigur == 0) {// array hat ansonsten index von -1
      altePosFigur = player[aktSpieler - 1].getStartPosition();
    }
    // fehlervermeidung:
    if (alteZielPosition == 0) {// array hat ansonsten index von -1
      switch (aktSpieler) {
        case 1:
          alteZielPosition = player[aktSpieler - 1].figur[aktFigur - 1].getPositionImZiel() + 1;
          break;
        case 2:
          alteZielPosition = player[aktSpieler - 1].figur[aktFigur - 1].getPositionImZiel() + 5;
          break;
        case 3:
          alteZielPosition = player[aktSpieler - 1].figur[aktFigur - 1].getPositionImZiel() + 9;
          break;
        case 4:
          alteZielPosition = player[aktSpieler - 1].figur[aktFigur - 1].getPositionImZiel() + 13;
          break;

      }
    }
    // Operation die Figuren bewegt wird ausgefuehrt:
    feld.bewegen(player[aktSpieler - 1], aktSpieler, player[aktSpieler - 1].figur[aktFigur - 1], figur,
        gewuerfelteZahl);

    // Alle Positionen werden in einem Array gespeichert
    System.out.println("");
    System.out.print("Positionen aller Spieler: ");
    for (int i = 0; i < 4; i++) {// Positionen 0-3, Spieler 1
      setFigurenPos(1, i, player[0].figur[i].getPosition());
    }
    for (int i = 0; i < 4; i++) {// Positionen 4-7, Spieler 2
      setFigurenPos(2, i, player[1].figur[i].getPosition());
    }
    if (spieleranzahl > 2) {
      for (int i = 0; i < 4; i++) {// Positionen 8-11, Spieler 3
        setFigurenPos(3, i, player[2].figur[i].getPosition());
      }
    }
    if (spieleranzahl > 3) {
      for (int i = 0; i < 4; i++) {// Positionen 12-15, Spieler 4
        setFigurenPos(4, i, player[3].figur[i].getPosition());
      }
    }

    // Ueberpruefung ob Felder gleich sind
    boolean kannBewegen = true; // variable die enstcheidet ob sich eine Figur bewegen kann
    for (int i = 0; i < figurenPos.length; i++) {
      if (i == (aktFigur - 1) + (aktSpieler - 1) * 4) {
        break;
      } else {
        if (figurenPos[(aktFigur - 1) + (aktSpieler - 1) * 4] != 0 && figurenPos[i] != 0) { // gleicht ab ob positionen
                                                                                            // nicht 0 sind
          if (figurenPos[(aktFigur - 1) + (aktSpieler - 1) * 4] == figurenPos[i]) {// wenn von den restlichen Positionen
                                                                                   // welche gleich sind
            System.out.println("\nFigurenPos " + (((aktFigur - 1) + (aktSpieler - 1) * 4) + 1) + " und FigurenPos "
                + (i + 1) + " sind gleich");// Ausgabe
            switch (aktSpieler) {// Switch case legt hier fest ob Figur aus dem Spielfeld rasufliegt oder die
                                 // Figur sich nicht bvewegen kann
              case 1:
                if (i < 4) {
                  kannBewegen = false;
                } else if (i >= 4 && i <= 7) {
                  player[1].figur[i - 4].setPosition(0);
                  player[1].figur[i - 4].setIstImFeld(false);
                } else if (i >= 8 && i <= 11) {
                  player[2].figur[i - 8].setPosition(0);
                  player[2].figur[i - 8].setIstImFeld(false);
                } else if (i >= 12 && i <= 15) {
                  player[3].figur[i - 12].setPosition(0);
                  player[3].figur[i - 12].setIstImFeld(false);
                }
                break;
              case 2:
                if (i < 4) {
                  player[0].figur[i].setPosition(0);
                  player[0].figur[i].setIstImFeld(false);
                } else if (i >= 4 && i <= 7) {
                  kannBewegen = false;
                } else if (i >= 8 && i <= 11) {
                  player[2].figur[i - 8].setPosition(0);
                  player[2].figur[i - 8].setIstImFeld(false);
                } else if (i >= 12 && i <= 15) {
                  player[3].figur[i - 12].setPosition(0);
                  player[3].figur[i - 12].setIstImFeld(false);
                }
                break;
              case 3:
                if (i < 4) {
                  player[0].figur[i].setPosition(0);
                  player[0].figur[i].setIstImFeld(false);
                } else if (i >= 4 && i <= 7) {
                  player[1].figur[i - 4].setPosition(0);
                  player[1].figur[i - 4].setIstImFeld(false);
                } else if (i >= 8 && i <= 11) {
                  kannBewegen = false;
                } else if (i >= 12 && i <= 15) {
                  player[3].figur[i - 12].setPosition(0);
                  player[3].figur[i - 12].setIstImFeld(false);
                }
                break;
              case 4:
                if (i < 4) {
                  player[0].figur[i].setPosition(0);
                  player[0].figur[i].setIstImFeld(false);
                } else if (i >= 4 && i <= 7) {
                  player[1].figur[i - 4].setPosition(0);
                  player[1].figur[i - 4].setIstImFeld(false);
                } else if (i >= 8 && i <= 11) {
                  player[2].figur[i - 8].setPosition(0);
                  player[2].figur[i - 8].setIstImFeld(false);
                } else if (i >= 12 && i <= 15) {
                  kannBewegen = false;
                }
                break;
            }
          }
        }
      }
    }

    // holt sich die anz der Figuren im Feld und ob Figur im Ziel ist
    istImZiel = player[aktSpieler - 1].figur[aktFigur - 1].getIstImZiel();
    anzahlFigurenImZiel = player[aktSpieler - 1].getAnzFigurenImZiel();

    if (kannBewegen == false) {// bleibt stehen wenn sich Figur nicht bewegen kann
      player[aktSpieler - 1].figur[aktFigur - 1].setPosition(altePosFigur);
      if (istImZiel == true) {// bleibt im Ziel stehen wenn sich Figur nicht bewegen kann
        player[aktSpieler - 1].figur[aktFigur - 1].setPositionImZiel(altePosFigur - 40);
      }
    }
    boolean gehtInsZiel = feld.getGehtInsZiel();
    if (gehtInsZiel == true && kannBewegen == false) {// ueberprueft ob Figur ins Ziel geht und nicht laufen kann
      anzahlFigurenImZiel = anzahlFigurenImZiel - 1;// setzt Figurenanzahl zurueck
      player[aktSpieler - 1].setAnzFigurenImZiel(anzahlFigurenImZiel);
      player[aktSpieler - 1].figur[aktFigur - 1].setPositionImZiel(0);
      player[aktSpieler - 1].figur[aktFigur - 1].setIstImZiel(false);
    }

    // neu berechnete Positionen werden gespeichert
    anzahlFigurenImFeld = player[aktSpieler - 1].getAnzFigurenImFeld();
    aktPosFigur = player[aktSpieler - 1].figur[aktFigur - 1].getPosition();
    neueZielPosition = player[aktSpieler - 1].figur[aktFigur - 1].getPositionImZiel();
    // neue Array positionen
    for (int i = 0; i < 4; i++) {// Positionen 0-3, Spieler 1
      setFigurenPos(1, i, player[0].figur[i].getPosition());
    }
    for (int i = 0; i < 4; i++) {// Positionen 4-7, Spieler 2
      setFigurenPos(2, i, player[1].figur[i].getPosition());
    }
    if (spieleranzahl > 2) {
      for (int i = 0; i < 4; i++) {// Positionen 8-11, Spieler 3
        setFigurenPos(3, i, player[2].figur[i].getPosition());
      }
    }
    if (spieleranzahl > 3) {
      for (int i = 0; i < 4; i++) {// Positionen 12-15, Spieler 4
        setFigurenPos(4, i, player[3].figur[i].getPosition());
      }
    }
    // Array wird ausgegeben
    for (int i = 0; i < figurenPos.length; i++) {
      System.out.print(figurenPos[i] + " ");
    }
    System.out.println("\nKann Bewegen:" + kannBewegen);

    // Test Ausgabe:
    System.out.println("\nSpieler: " + aktSpieler + ", Figur: " + aktFigur + ", Position: " + aktPosFigur
        + ", Figuren Im Feld: " + anzahlFigurenImFeld + ", Figuren Im Ziel: " + anzahlFigurenImZiel
        + ", Position im Ziel: " + neueZielPosition);

    if (anzahlFigurenImZiel == 4) {// leitet das Ende ein
      String gewinner = getSpielerName();
      spielEnde(gewinner);
    }
    figurenPos();

  }

  // Spielerwechsel
  public void wechselSpieler() {
    int NeuerAktSpieler = aktSpieler + 1; // Spieler wird erhoeht
    if (NeuerAktSpieler > spieleranzahl) { // wenn Spileranzahl zu groß ist beginnt sie wieder bei 1
      NeuerAktSpieler = 1;
    }
    System.out.println(aktSpieler);
    setAktSpieler(NeuerAktSpieler); // setzt aktSpieler
    System.out.println("Aktueller Spieler: Spieler" + aktSpieler);
  }

  // Spieler wuerfelt
  public void wuerfeln() {
    gewuerfelteZahl = Wuerfel.wuerfeln();
    setGewuerfelteZahl(gewuerfelteZahl);
    System.out.println("Gewürfelte Zahl: ");
  }

  // Operation leitet das Speil zum End-Bildschirm
  public void spielEnde(String initGewinner) {
    gui2.spielEnde();
    new GUI3(initGewinner);
  }

  // get und set Operationen:

  public void setFigurenPos(int spieler, int figur, int neuePosition) {// Positionen fuer das Array
    int speicherPosition = 0;
    switch (spieler) {
      case 1:
        speicherPosition = figur;
        break;
      case 2:
        speicherPosition = 4 + figur;
        break;
      case 3:
        speicherPosition = 8 + figur;
        break;
      case 4:
        speicherPosition = 12 + figur;
        break;
    }
    figurenPos[speicherPosition] = neuePosition;
  }

  public int[] getAllefigurenPos() {//holt sich das Array
    return figurenPos;
  }

  public String getSpielerName() {// holt isch den Spielernamen
    return spielerNamen[aktSpieler - 1];
  }

  public int getGewuerfelteZahl() {//holt isch die gewuerfelte Zahl
    return gewuerfelteZahl;
  }

  public void setGewuerfelteZahl(int initGewuerfelteZahl) {// legt gewuerfelte Zahl fest
    gewuerfelteZahl = initGewuerfelteZahl;
  }

  public int getAktSpieler() { //holt isch aktuellen Spieler
    return aktSpieler;
  }

  public void setAktSpieler(int initAktSpieler) { //legt aktuellen Spieler fest
    aktSpieler = initAktSpieler;
  }

  public int getAktFigur() { //holt aktuelle Figur
    return aktFigur;
  }

  public void setAktFigur(int initAktFigur) { //legt aktuelle Figur fest
    aktFigur = initAktFigur;
  }

  public int getSpieleranzahl() { //holt Spieleranzahl
    return spieleranzahl;
  }

  public int getErsterSpieler() { //holt den Spieler, der beginnt
    return ersterSpieler;
  }

  public int getPosFigur() { // holt sich aktuelle die Positionen der aktuellen Figur
    return aktPosFigur;
  }

  public int getAltePosFigur() { // holt alte Position der aktuellen Figur
    return altePosFigur;
  }

  public int getAnzahlFigurenImFeld() { //holt sich aktuelle Anzhal der Figuren im Feld des aktuellen Spielers
    return anzahlFigurenImFeld;
  }

  public boolean getIstImZiel() {//holt sich den Zustand ob aktuelle Figur im Ziel ist
    return istImZiel;
  }

  public int getNeuePosImZiel() {// holt sich die Position im Ziel der aktuellen Figur
    return neueZielPosition;
  }

  public int getAltePosImZiel() {//holt alte Position im Ziel der aktuellen Figur
    return alteZielPosition;
  }

  public int getFigurenPos() { // holt sich die Position der Figuren
    return figurenPos[(aktFigur - 1) + (aktSpieler - 1) * 4 ];
  }

  public void figurenPos(){
    System.out.println(getAllefigurenPos());
        
  }
}
