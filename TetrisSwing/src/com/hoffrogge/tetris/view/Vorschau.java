package com.hoffrogge.tetris.view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import com.hoffrogge.tetris.model.TetrisKonstanten;
import com.hoffrogge.tetris.model.tetromino.TetrominoFactory;
import com.hoffrogge.tetris.model.tetromino.TetrominoSpielstein;
import com.hoffrogge.tetris.model.tetromino.TetrominoTyp;

@SuppressWarnings("serial")
public class Vorschau extends Canvas {

    private static final Color FUELL_FARBE = new Color(200, 240, 255);

    private TetrominoTyp naechsterSpielsteinTyp;
    private TetrominoFactory tetrominoFactory;

    private TetrominoSpielstein naechsterSpielstein;

    // TODO die Vorschau muss die Factory gar nicht kennen, es sollte v�llig
    // reichen, wenn sie den Spielstein kennt, oder besser noch, einfach nur ein
    // zeichenbares Objekt bekommt (den Spielstein)
    public Vorschau(TetrominoFactory tetrominoFactory) {

	this.tetrominoFactory = tetrominoFactory;

	setBackground(TetrisKonstanten.HINTERGRUND);
	setForeground(TetrisKonstanten.VORDERGRUND);
	setBounds(TetrisKonstanten.VORSCHAU_POS_X, TetrisKonstanten.VORSCHAU_POS_Y, TetrisKonstanten.VORSCHAU_BREITE,
		TetrisKonstanten.VORSCHAU_HOEHE);
    }

    public void aktualisieren(TetrominoTyp tetrominoTyp) {
	this.naechsterSpielsteinTyp = tetrominoTyp;
	zeichnen();
    }

    private void zeichnen() {

	Graphics g = null;

	try {

	    g = getBufferStrategy().getDrawGraphics();

	    zeichneVorschauFeld(g);

	    if (naechsterSpielsteinTyp == null)
		return;

	    int xKoordinate = 0;
	    int yKoordinate = 0;

	    switch (naechsterSpielsteinTyp) {

	    case BLOCK:
		xKoordinate = (int) (TetrisKonstanten.BLOCK_BREITE * 1.5);
		yKoordinate = TetrisKonstanten.BLOCK_BREITE * 2;
		break;

	    case L:
	    case UMGEDREHTES_Z:
		xKoordinate = (int) (TetrisKonstanten.BLOCK_BREITE * 1.5);
		yKoordinate = (int) (TetrisKonstanten.BLOCK_BREITE * 1.5);
		break;

	    case UMGEDREHTES_L:
		xKoordinate = (int) (TetrisKonstanten.BLOCK_BREITE * 2.5);
		yKoordinate = TetrisKonstanten.BLOCK_BREITE;
		break;

	    case LANGER:
		xKoordinate = TetrisKonstanten.BLOCK_BREITE * 2;
		yKoordinate = TetrisKonstanten.BLOCK_BREITE;
		break;

	    case Z:
		xKoordinate = (int) (TetrisKonstanten.BLOCK_BREITE * 2.5);
		yKoordinate = (int) (TetrisKonstanten.BLOCK_BREITE * 1.5);
		break;

	    case T:
		xKoordinate = TetrisKonstanten.BLOCK_BREITE * 2;
		yKoordinate = TetrisKonstanten.BLOCK_BREITE * 2;
		break;

	    default:
		throw new IllegalStateException("TetrominoTyp " + naechsterSpielsteinTyp + " ist nicht bekannt!");
	    }

	    naechsterSpielstein = tetrominoFactory.erstelleTetromino(naechsterSpielsteinTyp, xKoordinate, yKoordinate);

	    for (TetrominoSpielstein teilblock : naechsterSpielstein.getTeilBloecke())
		teilblock.setFuellFarbe(FUELL_FARBE);

	} finally {
	    if (g != null)
		g.dispose();
	}

	getBufferStrategy().show();
    }

    private void zeichneVorschauFeld(Graphics g) {

	/* Hintergrund des Feldes */
	g.setColor(TetrisKonstanten.VORDERGRUND);
	g.fillRect(0, 0, TetrisKonstanten.VORSCHAU_BREITE, TetrisKonstanten.VORSCHAU_HOEHE);

	// TODO: Vorschau zeichnet Bl�cke zu oft, evtl. als Listener beim
	// Spiel registrieren und nur bei Aenderungen am naechsten
	// Spielstein zeichnen

	if (naechsterSpielstein != null)
	    naechsterSpielstein.zeichnen(g);
    }
}