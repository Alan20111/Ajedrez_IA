package ajedrez;

import ajedrez.piezas.*;
import java.util.List;

public class Tablero {
    private final Pieza[][] casillas;

    public Tablero() { this.casillas = new Pieza[8][8]; }

    public Pieza getPiezaEn(Posicion pos) {
        if (!esCasillaValida(pos)) return null;
        return casillas[pos.getFila()][pos.getColumna()];
    }

    public boolean esCasillaValida(Posicion pos) {
        int fila = pos.getFila();
        int col = pos.getColumna();
        return fila >= 0 && fila < 8 && col >= 0 && col < 8;
    }

    // --- MÉTODO MODIFICADO: AHORA DEVUELVE LA PIEZA CAPTURADA ---
    public Pieza moverPieza(Movimiento movimiento) {
        Posicion inicio = movimiento.getInicio();
        Posicion fin = movimiento.getFin();
        Pieza piezaAMover = getPiezaEn(inicio);

        // Guardamos la pieza que está en la casilla de destino (si la hay)
        Pieza piezaCapturada = getPiezaEn(fin);

        casillas[fin.getFila()][fin.getColumna()] = piezaAMover;
        casillas[inicio.getFila()][inicio.getColumna()] = null;

        if (piezaAMover != null) {
            piezaAMover.setPosicion(fin);
        }

        // Devolvemos la pieza que fue capturada (puede ser null)
        return piezaCapturada;
    }

    // --- MÉTODO MODIFICADO: AHORA MUESTRA LAS PIEZAS CAPTURADAS ---
    public void imprimirTablero(Color jugadorActual, List<Pieza> capturadasBlancas, List<Pieza> capturadasNegras) {
        // Imprime piezas capturadas por las Negras (piezas Blancas)
        System.out.print("\nCapturadas por Negras: ");
        for (Pieza p : capturadasBlancas) { System.out.print(p.getSimbolo() + " "); }

        System.out.print("\n   a b c d e f g h\n  -----------------\n");
        if (jugadorActual == Color.BLANCO) {
            for (int fila = 0; fila < 8; fila++) {
                System.out.print((8 - fila) + "|");
                for (int col = 0; col < 8; col++) { imprimirCasilla(fila, col); }
                System.out.println(" |" + (8 - fila));
            }
        } else {
            for (int fila = 7; fila >= 0; fila--) {
                System.out.print((8 - fila) + "|");
                for (int col = 0; col < 8; col++) { imprimirCasilla(fila, col); }
                System.out.println(" |" + (8 - fila));
            }
        }
        System.out.println("  -----------------\n   a b c d e f g h");

        // Imprime piezas capturadas por las Blancas (piezas Negras)
        System.out.print("Capturadas por Blancas: ");
        for (Pieza p : capturadasNegras) { System.out.print(p.getSimbolo() + " "); }
        System.out.println();
    }

    // (El resto de la clase: iniciarTablero, imprimirCasilla, getPiezaEn(int, int) no cambia)
    public Pieza getPiezaEn(int fila, int col) { return casillas[fila][col]; }
    private void imprimirCasilla(int fila, int col) {
        Pieza pieza = casillas[fila][col];
        if (pieza == null) { System.out.print("· "); }
        else { System.out.print(pieza.getSimbolo() + " "); }
    }
    public void iniciarTablero() {
        casillas[0][0]=new Torre(Color.NEGRO,new Posicion(0,0)); casillas[0][1]=new Caballo(Color.NEGRO,new Posicion(0,1)); casillas[0][2]=new Alfil(Color.NEGRO,new Posicion(0,2)); casillas[0][3]=new Reina(Color.NEGRO,new Posicion(0,3)); casillas[0][4]=new Rey(Color.NEGRO,new Posicion(0,4)); casillas[0][5]=new Alfil(Color.NEGRO,new Posicion(0,5)); casillas[0][6]=new Caballo(Color.NEGRO,new Posicion(0,6)); casillas[0][7]=new Torre(Color.NEGRO,new Posicion(0,7));
        for(int i=0;i<8;i++) { casillas[1][i]=new Peon(Color.NEGRO,new Posicion(1,i)); }
        for(int i=0;i<8;i++) { casillas[6][i]=new Peon(Color.BLANCO,new Posicion(6,i)); }
        casillas[7][0]=new Torre(Color.BLANCO,new Posicion(7,0)); casillas[7][1]=new Caballo(Color.BLANCO,new Posicion(7,1)); casillas[7][2]=new Alfil(Color.BLANCO,new Posicion(7,2)); casillas[7][3]=new Reina(Color.BLANCO,new Posicion(7,3)); casillas[7][4]=new Rey(Color.BLANCO,new Posicion(7,4)); casillas[7][5]=new Alfil(Color.BLANCO,new Posicion(7,5)); casillas[7][6]=new Caballo(Color.BLANCO,new Posicion(7,6)); casillas[7][7]=new Torre(Color.BLANCO,new Posicion(7,7));
    }
}