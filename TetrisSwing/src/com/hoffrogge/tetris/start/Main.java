package com.hoffrogge.tetris.start;

import com.hoffrogge.tetris.logik.Spiel;
import com.hoffrogge.tetris.model.TetrisKonstanten;
import com.hoffrogge.tetris.model.tetromino.StandardTetrominoFactory;
import com.hoffrogge.tetris.model.tetromino.TetrominoFactory;
import com.hoffrogge.tetris.view.Spielfeld;
import com.hoffrogge.tetris.view.Spielfenster;
import com.hoffrogge.tetris.view.TetrisKeyListener;
import com.hoffrogge.tetris.view.Vorschau;

public class Main {

    public static void main(String[] args) {

        /*
         * Diese Factory ist daf�r zust�ndig, Spielsteine zu generieren. Mit
         * deiner eigenen Factory kannst du hier deine eigenen Spielsteine
         * einbauen.
         */
        TetrominoFactory tetrominoFactory = new StandardTetrominoFactory();

        /*
         * Dies ist das Spielfeld. Es zeichnet das Spielfeld und weiss, wo
         * welche Spielsteine sind. Das Spielfeld kann Tetrisspielsteine nicht
         * beeinflussen, es weiss nur, wo sie sind.
         */
        Spielfeld spielfeld = new Spielfeld(tetrominoFactory);
        spielfeld.setBackground(TetrisKonstanten.HINTERGRUND.konvertiereZuColor());
        spielfeld.setBounds(TetrisKonstanten.SPIELFELD_POS_X, TetrisKonstanten.SPIELFELD_POS_Y, TetrisKonstanten.SPIELFELD_BREITE,
                TetrisKonstanten.SPIELFELD_HOEHE);

        /*
         * Die Vorschau zeigt den jeweils n�chsten Spielstein an. Mehr kann sie
         * nicht tun.
         */
        Vorschau vorschau = new Vorschau(tetrominoFactory);
        vorschau.setBackground(TetrisKonstanten.HINTERGRUND.konvertiereZuColor());
        vorschau.setForeground(TetrisKonstanten.VORDERGRUND.konvertiereZuColor());
        vorschau.setBounds(TetrisKonstanten.VORSCHAU_POS_X, TetrisKonstanten.VORSCHAU_POS_Y, TetrisKonstanten.VORSCHAU_BREITE, TetrisKonstanten.VORSCHAU_HOEHE);

        /*
         * Das Spielfenster ist daf�r zust�ndig, das Spiel anzuzeigen, die
         * Tetrisspielsteine, die Vorschau, Highscore. Das Spielfenster selbst
         * kann Spielsteine nicht beeinflussen, es stellt sie nur dar.
         */
        Spielfenster spielfenster = new Spielfenster(spielfeld, vorschau);

        /*
         * Das Spiel enth�lt alles an Logik, die es braucht, z. B. das Drehen
         * von Spielsteinen oder die Berechnung von Punkten. Das Spiel kann
         * nichts darstellen, das ist Aufgabe des Spielfensters. Das Spiel kann
         * nur dem Spielfenster Informationen geben, die das Spielfenster dann
         * darstellt.
         */
        Spiel spiel = new Spiel(spielfenster, tetrominoFactory);

        /*
         * Dieser Listener wird fuer die Steuerung des Spiels benoetigt (links,
         * rechts, runter, drehen, Pause). Er erkennt Tastatureingaben und
         * reicht diese an das Spiel weiter.
         */
        TetrisKeyListener tetrisKeyListener = new TetrisKeyListener(spiel);

        /*
         * Der KeyListener muss an einer Komponente h�ngen, die angezeigt wird,
         * in diesem Fall eignet sich das Spielfenster daf�r.
         */
        spielfenster.addKeyListener(tetrisKeyListener);

        spiel.starteSpiel();
    }
}