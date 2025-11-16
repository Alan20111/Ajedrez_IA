package ajedrez;

import ajedrez.piezas.*;
import java.util.List;

public class Tablero {
    private Pieza[][] casillas;
    private Posicion reyBlancoPos;
    private Posicion reyNegroPos;

    public Tablero() {
        casillas = new Pieza[8][8];
    }

    public void iniciarTablero() {
        // Piezas Negras
        casillas[0][0] = new Torre(Color.NEGRO, new Posicion(0, 0));
        casillas[0][1] = new Caballo(Color.NEGRO, new Posicion(0, 1));
        casillas[0][2] = new Alfil(Color.NEGRO, new Posicion(0, 2));
        casillas[0][3] = new Reina(Color.NEGRO, new Posicion(0, 3));
        casillas[0][4] = new Rey(Color.NEGRO, new Posicion(0, 4));
        casillas[0][5] = new Alfil(Color.NEGRO, new Posicion(0, 5));
        casillas[0][6] = new Caballo(Color.NEGRO, new Posicion(0, 6));
        casillas[0][7] = new Torre(Color.NEGRO, new Posicion(0, 7));
        for (int i = 0; i < 8; i++) {
            casillas[1][i] = new Peon(Color.NEGRO, new Posicion(1, i));
        }

        // Piezas Blancas
        casillas[7][0] = new Torre(Color.BLANCO, new Posicion(7, 0));
        casillas[7][1] = new Caballo(Color.BLANCO, new Posicion(7, 1));
        casillas[7][2] = new Alfil(Color.BLANCO, new Posicion(7, 2));
        casillas[7][3] = new Reina(Color.BLANCO, new Posicion(7, 3));
        casillas[7][4] = new Rey(Color.BLANCO, new Posicion(7, 4));
        casillas[7][5] = new Alfil(Color.BLANCO, new Posicion(7, 5));
        casillas[7][6] = new Caballo(Color.BLANCO, new Posicion(7, 6));
        casillas[7][7] = new Torre(Color.BLANCO, new Posicion(7, 7));
        for (int i = 0; i < 8; i++) {
            casillas[6][i] = new Peon(Color.BLANCO, new Posicion(6, i));
        }

        this.reyBlancoPos = new Posicion(7, 4);
        this.reyNegroPos = new Posicion(0, 4);
    }

    // --- FUNCIÓN DE IMPRESIÓN (VERSIÓN 6: CELDAS DE 4 ESPACIOS) ---
    public void imprimirTablero(Color colorVista, List<Pieza> capturadasBlancas, List<Pieza> capturadasNegras) {
        System.out.println("\nCapturadas por Negras: ");
        for (Pieza p : capturadasBlancas) {
            System.out.print(p.getSimbolo() + " ");
        }
        System.out.println();

        // CAMBIO: Encabezado de 4 espacios
        System.out.println("    a   b   c   d   e   f   g   h");
        // CAMBIO: Línea divisoria (4 prefijo + 8 * 4 = 32 guiones)
        System.out.println("    --------------------------------");

        if (colorVista == Color.BLANCO) {
            for (int f = 0; f < 8; f++) {
                // CAMBIO: Prefijo de 4 espacios ("8 | ")
                System.out.print(String.format("%d |  ", (8 - f)));

                for (int c = 0; c < 8; c++) {
                    Pieza pieza = casillas[f][c];

                    if (pieza != null) {
                        // CAMBIO: Pieza (2) + 2 espacios = 4 anchos
                        System.out.print(pieza.getSimbolo() + "  ");
                    } else {
                        // CAMBIO: "+" (1) + 3 espacios = 4 anchos
                        System.out.print("+   ");
                    }
                }
                // CAMBIO: Sufijo de 4 espacios (" | 8")
                System.out.println(String.format(" | %d", (8 - f)));
            }
        } else {
            // Vista para Negras
            for (int f = 7; f >= 0; f--) {
                System.out.print(String.format("%d |  ", (8 - f))); // 4 anchos

                for (int c = 0; c < 8; c++) {
                    Pieza pieza = casillas[f][c];

                    if (pieza != null) {
                        System.out.print(pieza.getSimbolo() + "  "); // 4 anchos
                    } else {
                        System.out.print("+   "); // 4 anchos
                    }
                }
                System.out.println(String.format(" | %d", (8 - f))); // 4 anchos
            }
        }

        System.out.println("    --------------------------------");
        System.out.println("    a   b   c   d   e   f   g   h");
        // ... (resto de la función) ...
    }



    public boolean esCasillaValida(Posicion pos) {
        int fila = pos.getFila();
        int col = pos.getColumna();
        return fila >= 0 && fila < 8 && col >= 0 && col < 8;
    }

    public Pieza getPiezaEn(Posicion pos) {
        if (!esCasillaValida(pos)) {
            return null;
        }
        return casillas[pos.getFila()][pos.getColumna()];
    }

    public Pieza getPiezaEn(int fila, int columna) {
        if (fila < 0 || fila >= 8 || columna < 0 || columna >= 8) {
            return null;
        }
        return casillas[fila][columna];
    }

    public void reemplazarPieza(Posicion pos, Pieza pieza) {
        if (!esCasillaValida(pos)) {
            return;
        }
        casillas[pos.getFila()][pos.getColumna()] = pieza;
        if (pieza != null) {
            pieza.setPosicion(pos);
            if (pieza instanceof Rey) {
                if (pieza.getColor() == Color.BLANCO) {
                    reyBlancoPos = pos;
                } else {
                    reyNegroPos = pos;
                }
            }
        }
    }

    public Pieza moverPieza(Movimiento mov) {
        Pieza piezaOrigen = getPiezaEn(mov.getInicio());
        Pieza piezaDestino = getPiezaEn(mov.getFin());

        reemplazarPieza(mov.getFin(), piezaOrigen);
        reemplazarPieza(mov.getInicio(), null);

        if (piezaOrigen != null) {
            // Este setHaMovido() es una simplificación
        }

        return piezaDestino;
    }

    public void deshacerMovimiento(Movimiento mov, Pieza piezaCapturada) {
        Pieza piezaMovida = getPiezaEn(mov.getFin());

        reemplazarPieza(mov.getInicio(), piezaMovida);
        reemplazarPieza(mov.getFin(), piezaCapturada);
    }

    public Posicion getReyBlancoPos() {
        return reyBlancoPos;
    }

    public Posicion getReyNegroPos() {
        return reyNegroPos;
    }
}