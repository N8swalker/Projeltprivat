// von Thomas und Tim mit unterstuetzung von Tobias
package Gui;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import Spiel.Steuerung;

public class GUI2 extends JFrame implements ActionListener {
    // Gibt die Pfade der Bilder zurück
    ImageGetter imgs = new ImageGetter();
    // generiert buttons
    JButton buttonforWuerfeln;
    JButton buttonforFigur1;
    JButton buttonforFigur2;
    JButton buttonforFigur3;
    JButton buttonforFigur4;
    JButton buttonforLaufen;
    JButton buttonforFertig;

    // generiert ein Feld für Name und Nummer des Spielers
    JTextField spielerInfo;
    int spieler = 1;
    int gewuerfelteZahl;
    String spielerNamen[] = new String[4];

    // Schrift wird eingestellt //von Tobi
    Font font1 = new Font("Arial", Font.BOLD, 24);
    Font font2 = new Font("Arial", Font.BOLD, 12);

    // Positionen der Felder werden festgelegt
    int[][] posFelder = {
            { 810, 810, 810, 810, 810, 880, 950, 1020, 1090, 1090, 1090, 1020, 950, 880, 810, 810, 810, 810, 810,
                    740, 670, 670, 670, 670, 670, 600, 530, 460, 390, 390, 390, 460, 530, 600, 670, 670, 670, 670,
                    670, 740 },
            { 70, 140, 210, 280, 350, 350, 350, 350, 350, 420, 490, 490, 490, 490, 490, 560, 630, 700, 770, 770,
                    770, 700, 630, 560, 490, 490, 490, 490, 490, 420, 350, 350, 350, 350, 350, 280, 210, 140, 70,
                    70 } };

    int[][] posDefaultFelder = { { 7, 8, 7, 8, 7, 8, 7, 8, 28, 27, 28, 27, 28, 27, 28, 27 },
            { 0, 0, 1, 1, 17, 17, 18, 18, 21, 21, 20, 20, 0, 0, 1, 1 } };

    int[][] posEndFelder = { { 39, 39, 39, 39, 7, 6, 5, 4, 19, 19, 19, 19, 27, 26, 25, 24 },
            { 1, 2, 3, 4, 9, 9, 9, 9, 17, 16, 15, 14, 9, 9, 9, 9 } };

    Spiel.Steuerung guiSteuerung;

    ActionEvent e;

    public GUI2(int initSpieleranzahl, int initErsterSpieler, Steuerung steuerung) {
        guiSteuerung = steuerung;
        spieler = initErsterSpieler;
        // Spielernanzahl und Erstespieler werden
        // weitergegeben

        // erstellt das Panel wo alles drauf ist
        setTitle("Spielbrett");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.gray.darker());
        repaint();

        // erstellt die Buttons und setzt deren positionen
        buttonforWuerfeln = new JButton();
        try {
            buttonforWuerfeln.setIcon(new ImageIcon(imgs.getWuerfel2()));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        buttonforWuerfeln.setBounds(1650, 70, 100, 100);
        add(buttonforWuerfeln);

        spielerInfo = new JTextField("Spieler : " + guiSteuerung.getSpielerName());
        spielerInfo.setEditable(false);
        spielerInfo.setBounds(1450, 70, 175, 100);
        spielerInfo.setFont(font1);
        spielerInfo.setForeground(Color.black);
        add(spielerInfo);

        buttonforFigur1 = new JButton("Figur 1");
        buttonforFigur1.setBounds(1450, 330, 300, 60);
        buttonforFigur1.setFont(font1);
        buttonforFigur1.setForeground(Color.black);
        add(buttonforFigur1);

        buttonforFigur2 = new JButton("Figur 2");
        buttonforFigur2.setBounds(1450, 400, 300, 60);
        buttonforFigur2.setFont(font1);
        buttonforFigur2.setForeground(Color.black);
        add(buttonforFigur2);

        buttonforFigur3 = new JButton("Figur 3");
        buttonforFigur3.setBounds(1450, 470, 300, 60);
        buttonforFigur3.setFont(font1);
        buttonforFigur3.setForeground(Color.black);
        add(buttonforFigur3);

        buttonforFigur4 = new JButton("Figur 4");
        buttonforFigur4.setBounds(1450, 540, 300, 60);
        buttonforFigur4.setFont(font1);
        buttonforFigur4.setForeground(Color.black);
        add(buttonforFigur4);

        buttonforLaufen = new JButton("Laufen");
        buttonforLaufen.setBounds(1450, 760, 145, 100);
        buttonforLaufen.setFont(font1);
        buttonforLaufen.setForeground(Color.black);
        add(buttonforLaufen);

        buttonforFertig = new JButton("Fertig");
        buttonforFertig.setBounds(1605, 760, 145, 100);
        buttonforFertig.setFont(font1);
        buttonforFertig.setForeground(Color.black);
        add(buttonforFertig);
        repaint();

        // hier wird die Aktionen festgelegt für die Buttons
        buttonforWuerfeln.addActionListener(this);
        buttonforFigur1.addActionListener(this);
        buttonforFigur2.addActionListener(this);
        buttonforFigur3.addActionListener(this);
        buttonforFigur4.addActionListener(this);
        buttonforLaufen.addActionListener(this);
        buttonforFertig.addActionListener(this);
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        int[] allefigurenPos = new int[16];
        allefigurenPos = guiSteuerung.getAllefigurenPos();
        int figurenPos = 57;
        figurenPos = guiSteuerung.getFigurenPos();

        for (int i = 0; i <= 39; i++) {
            
            g2.setColor(Color.BLACK);
            g2.drawOval(posFelder[0][i], posFelder[1][i], 60, 60);
            g2.setColor(Color.BLACK);
            g2.fillOval(posFelder[0][i], posFelder[1][i], 60, 60);
            g2.setColor(Color.WHITE);
            g2.fillOval(posFelder[0][0] + 5, posFelder[1][0] + 5, 50, 50);
            g2.fillOval(posFelder[0][10] + 5, posFelder[1][10] + 5, 50, 50);
            g2.fillOval(posFelder[0][20] + 5, posFelder[1][20] + 5, 50, 50);
            g2.fillOval(posFelder[0][30] + 5, posFelder[1][30] + 5, 50, 50);
            if (!guiSteuerung.getIstImZiel()) {
                switch (spieler) {
                    case 1:
                        g.setColor(Color.BLUE);
                        break;
                    case 2:
                        g.setColor(Color.YELLOW);
                        break;
                    case 3:
                        g.setColor(Color.GREEN);
                        break;
                    case 4:
                        g.setColor(Color.RED);
                        break;
                    default:
                        g.setColor(Color.WHITE);
                }
                g2.fillOval(posFelder[0][figurenPos] + 5, posFelder[1][figurenPos] + 5, 50, 50);                
            }              

            for(int j = 0; j < 16; j++){
                int spielerImFeld = spieler;
                switch (spielerImFeld) {
                    case 0:
                        g.setColor(Color.BLUE);
                        break;
                    case 1:
                        g.setColor(Color.BLUE);
                        break;
                    case 2:
                        g.setColor(Color.BLUE);
                        break;
                    case 3:
                        g.setColor(Color.BLUE);
                        break;
                    default:
                        g.setColor(Color.WHITE);
                        break;
                }
                if (i == allefigurenPos[j] && allefigurenPos[j] < 40) {
                    g2.fillOval(posFelder[0][i] + 5, posFelder[1][i] + 5, 50, 50);
                }
                if(spielerImFeld ==4){
                    spielerImFeld = 0;
                }else{
                    spielerImFeld++;
                }
            }
            if(i < 16 && allefigurenPos[i] >= 40) {
                g2.fillOval(posFelder[0][posDefaultFelder[0][i]] + 5, posFelder[1][posDefaultFelder[1][i]] + 5, 50, 50);
            }
                
            
            
        }
        int j = 0;
        for (int i = 0; i < 16; i++) {
            Graphics2D g3 = (Graphics2D) g;
            g3.setColor(Color.BLACK);
            g3.fillOval(posFelder[0][posDefaultFelder[0][guiSteuerung.getAltePosImZiel()]], posFelder[1][posDefaultFelder[1][guiSteuerung.getAltePosImZiel()]], 60, 60);
            switch (j) {
                case 0:
                    g3.setColor(Color.BLUE);
                    if (i == 3) {
                        j = 1;
                    }
                    break;
                case 1:
                    g3.setColor(Color.YELLOW);
                    if (i == 7) {
                        j = 2;
                    }
                    break;
                case 2:
                    g3.setColor(Color.GREEN);
                    if (i == 11) {
                        j = 3;
                    }
                    break;
                case 3:
                    g3.setColor(Color.RED);
                    break;
            }
            g3.fillOval(posFelder[0][posEndFelder[0][i]], posFelder[1][posEndFelder[1][i]], 60, 60);
            g3.fillOval(posFelder[0][posDefaultFelder[0][i]], posFelder[1][posDefaultFelder[1][i]], 60, 60);
            g3.setColor(Color.BLACK);
            g3.fillOval(posFelder[0][posEndFelder[0][i]] + 5, posFelder[1][posEndFelder[1][i]] + 5, 50, 50);
            g3.drawOval(posFelder[0][posEndFelder[0][i]], posFelder[1][posEndFelder[1][i]], 60, 60);
            g3.drawOval(posFelder[0][posDefaultFelder[0][i]], posFelder[1][posDefaultFelder[1][i]], 60, 60);
            if (guiSteuerung.getIstImZiel()) {
                switch (spieler) {
                    case 1:
                        g.setColor(Color.BLUE);
                        break;
                    case 2:
                        g.setColor(Color.YELLOW);
                        break;
                    case 3:
                        g.setColor(Color.GREEN);
                        break;
                    case 4:
                        g.setColor(Color.RED);
                        break;
                    default:
                        g.setColor(Color.WHITE);
                }
                g.fillOval(posFelder[0][posEndFelder[0][figurenPos - 40] - 1] + 5, posFelder[1][posEndFelder[1][figurenPos -39] - 2] + 5,50, 50);
                
            }
        }
    }

    public void wuerfelAnimation(int gewuerfelteZahl) {
        System.out.println(gewuerfelteZahl);
        switch (gewuerfelteZahl) {
            case 1:
                try {
                    buttonforWuerfeln.setIcon(new ImageIcon(imgs.getWuerfel1()));
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                break;
            case 2:
                try {
                    buttonforWuerfeln.setIcon(new ImageIcon(imgs.getWuerfel2()));
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                break;
            case 3:
                try {
                    buttonforWuerfeln.setIcon(new ImageIcon(imgs.getWuerfel3()));
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                break;
            case 4:
                try {
                    buttonforWuerfeln.setIcon(new ImageIcon(imgs.getWuerfel4()));
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                break;
            case 5:
                try {
                    buttonforWuerfeln.setIcon(new ImageIcon(imgs.getWuerfel5()));
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                break;
            case 6:
                try {
                    buttonforWuerfeln.setIcon(new ImageIcon(imgs.getWuerfel6()));
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                break;
        }
    }

    // macht die bewegungen der Figuren auf dem Spielfeld sichtbar
    int altePosition = 0;
    int neuePosition = 0;
    int aktFigur = 0;

    public void figurBewegung() {
        altePosition = guiSteuerung.getAltePosFigur();
        neuePosition = guiSteuerung.getPosFigur();
        aktFigur = guiSteuerung.getAktFigur();
        // paintBewegung();
    }


    public void spielEnde() {
        dispose();
    }

    @Override
    // Operationen zum Ausführen der Funktionen der Buttons
    public void actionPerformed(ActionEvent e) {
        this.e = e;
        if (e.getSource() == buttonforWuerfeln) {
            guiSteuerung.wuerfeln();
            int gewuerfelteZahl = guiSteuerung.getGewuerfelteZahl();
            wuerfelAnimation(gewuerfelteZahl);
        }

        if (e.getSource() == buttonforFigur1) {
            guiSteuerung.figurWahl(1);
        }

        if (e.getSource() == buttonforFigur2) {
            guiSteuerung.figurWahl(2);
        }

        if (e.getSource() == buttonforFigur3) {
            guiSteuerung.figurWahl(3);
        }

        if (e.getSource() == buttonforFigur4) {
            guiSteuerung.figurWahl(4);
        }

        if (e.getSource() == buttonforLaufen) {
            guiSteuerung.laufen(aktFigur);
            repaint();

        }

        if (e.getSource() == buttonforFertig) {
            guiSteuerung.wechselSpieler();
            spieler = guiSteuerung.getAktSpieler();
            spielerInfo.setText("Spieler : " + guiSteuerung.getSpielerName());
            // Hintergrundfarbe Anzeigen und Buttons:
            switch (spieler) {
                case 1:
                    spielerInfo.setBackground(Color.BLUE.darker());
                    buttonforFigur1.setBackground(Color.BLUE.darker());
                    buttonforFigur2.setBackground(Color.BLUE.darker());
                    buttonforFigur3.setBackground(Color.BLUE.darker());
                    buttonforFigur4.setBackground(Color.BLUE.darker());
                    buttonforLaufen.setBackground(Color.BLUE.darker());
                    buttonforFertig.setBackground(Color.BLUE.darker());
                    break;
                case 2:
                    spielerInfo.setBackground(Color.yellow.darker());
                    buttonforFigur1.setBackground(Color.yellow.darker());
                    buttonforFigur2.setBackground(Color.yellow.darker());
                    buttonforFigur3.setBackground(Color.yellow.darker());
                    buttonforFigur4.setBackground(Color.yellow.darker());
                    buttonforLaufen.setBackground(Color.yellow.darker());
                    buttonforFertig.setBackground(Color.yellow.darker());
                    break;
                case 3:
                    spielerInfo.setBackground(Color.green.darker());
                    buttonforFigur1.setBackground(Color.green.darker());
                    buttonforFigur2.setBackground(Color.green.darker());
                    buttonforFigur3.setBackground(Color.green.darker());
                    buttonforFigur4.setBackground(Color.green.darker());
                    buttonforLaufen.setBackground(Color.green.darker());
                    buttonforFertig.setBackground(Color.green.darker());
                    break;
                case 4:
                    spielerInfo.setBackground(Color.red.darker());
                    buttonforFigur1.setBackground(Color.red.darker());
                    buttonforFigur2.setBackground(Color.red.darker());
                    buttonforFigur3.setBackground(Color.red.darker());
                    buttonforFigur4.setBackground(Color.red.darker());
                    buttonforLaufen.setBackground(Color.red.darker());
                    buttonforFertig.setBackground(Color.red.darker());
                    break;
            }

        }

    }
}
