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

            // --- PEON: Restaurar estado de "primer movimiento" si se deshace un avance doble ---
            // La lógica para Peon se maneja con restaurarEstadoMovimiento()
            if (piezaMovida instanceof Peon) {
                // Si el peón se movió de la fila inicial y ahora está volviendo a ella
                int filaOriginalPeon = (piezaMovida.getColor() == Color.BLANCO) ? 6 : 1;
                if (inicio.getFila() == filaOriginalPeon) {
                    ((Peon) piezaMovida).restaurarEstadoMovimiento();
                }
            }


            // --- REY: Restaurar estado de "primer movimiento" para enroque ---
            if (piezaMovida instanceof Rey) {
                // Fue un enroque si el Rey se movió 2 columnas horizontalmente
                int colDiff = fin.getColumna() - inicio.getColumna();
                if (Math.abs(colDiff) == 2) {
                    ((Rey) piezaMovida).restaurarEstadoMovimiento(); // Deshacer el 'seHaMovido' del rey

                    // Deshacer el movimiento de la torre que participó en el enroque
                    int fila = inicio.getFila();
                    if (colDiff > 0) { // Fue enroque corto (Rey se movió a g1/g8)
                        // Mover la torre de f1/f8 de vuelta a h1/h8
                        Pieza torre = getPiezaEn(new Posicion(fila, 5)); // Torre estaba en f1/f8
                        if (torre != null && torre instanceof Torre) {
                            casillas[fila][7] = torre; // Mover a h1/h8
                            torre.setPosicion(new Posicion(fila, 7));
                            casillas[fila][5] = null; // Vaciar f1/f8
                            ((Torre) torre).restaurarEstadoMovimiento(); // Restaurar estado de la torre
                        }
                    } else { // Fue enroque largo (Rey se movió a c1/c8)
                        // Mover la torre de d1/d8 de vuelta a a1/a8
                        Pieza torre = getPiezaEn(new Posicion(fila, 3)); // Torre estaba en d1/d8
                        if (torre != null && torre instanceof Torre) {
                            casillas[fila][0] = torre; // Mover a a1/a8
                            torre.setPosicion(new Posicion(fila, 0));
                            casillas[fila][3] = null; // Vaciar d1/d8
                            ((Torre) torre).restaurarEstadoMovimiento(); // Restaurar estado de la torre
                        }
                    }
                } else {
                    // Si el rey estaba en su posición original antes del movimiento
                    // y el rey se había movido (no era un enroque)
                    int filaOriginal = (piezaMovida.getColor() == Color.BLANCO) ? 7 : 0;
                    if (inicio.getFila() == filaOriginal && inicio.getColumna() == 4 && ((Rey) piezaMovida).seHaMovido()) { // <--- CORRECCIÓN AQUÍ (era seHaMovido())
                        ((Rey) piezaMovida).restaurarEstadoMovimiento();
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

            // --- TORRE: Restaurar estado de "primer movimiento" si aplica ---
            if (piezaMovida instanceof Torre) {
                // Si la torre estaba en su posición original antes del movimiento
                // y la torre se había movido (no parte de un enroque)
                int filaOriginal = (piezaMovida.getColor() == Color.BLANCO) ? 7 : 0;
                if (inicio.getFila() == filaOriginal && (inicio.getColumna() == 0 || inicio.getColumna() == 7) && ((Torre) piezaMovida).seHaMovido()) { // <--- CORRECCIÓN AQUÍ (era seHaMovido())
                    ((Torre) piezaMovida).restaurarEstadoMovimiento();
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

        // --- MANEJO DE PIEZA CAPTURADA ---
        // La pieza capturada se restaura en la posición final.
        // CASO ESPECIAL: Captura al Paso
        if (piezaCapturada instanceof Peon) {
            // Determinar la fila donde el peón fue "capturado al paso"
            int filaPeonCapturadoReal = (piezaMovida.getColor() == Color.BLANCO) ? fin.getFila() + 1 : fin.getFila() - 1;
            Posicion posPeonCapturadoReal = new Posicion(filaPeonCapturadoReal, fin.getColumna());

            // Si el movimiento original fue una captura al paso (destino final estaba vacío, pero se capturó un peón)
            if (getPiezaEn(fin) == null && Math.abs(movimiento.getInicio().getColumna() - movimiento.getFin().getColumna()) == 1 &&
                    movimiento.getFin().getFila() == (piezaMovida.getColor() == Color.BLANCO ? 2 : 5) ) { // Fila de la captura al paso

                // Colocar el peón capturado en su posición original ANTES de la captura al paso
                casillas[posPeonCapturadoReal.getFila()][posPeonCapturadoReal.getColumna()] = piezaCapturada;
                piezaCapturada.setPosicion(posPeonCapturadoReal);

                // Y la casilla de destino donde aterrizó el peón que capturó, debe quedar vacía
                casillas[fin.getFila()][fin.getColumna()] = null;

            } else {
                // Caso normal: restaurar la pieza capturada en la casilla de destino del movimiento
                casillas[fin.getFila()][fin.getColumna()] = piezaCapturada;
                if (piezaCapturada != null) {
                    piezaCapturada.setPosicion(fin);
                }
            }
        } else {
            // Para otras piezas capturadas (no peones en captura al paso)
            casillas[fin.getFila()][fin.getColumna()] = piezaCapturada;
            if (piezaCapturada != null) {
                piezaCapturada.setPosicion(fin);
            }
        }
    }


    public Tablero() {
        this.casillas = new Pieza[8][8];
    }
    public void reemplazarPieza(Posicion pos, Pieza nuevaPieza) {
        if(esCasillaValida(pos)){casillas[pos.getFila()][pos.getColumna()]=nuevaPieza;
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

    // --- CÓDIGO MODIFICADO: Ahora imprime cuadros blancos o negros ---
    private void imprimirCasilla(int f, int c) {
        Pieza p = casillas[f][c];
        // Determina si la casilla es "blanca" o "negra" en un tablero de ajedrez
        // (f + c) % 2 == 0 para casillas blancas, (f + c) % 2 != 0 para casillas negras
        boolean esCasillaClara = (f + c) % 2 == 0;

        if (p == null) {
            // Imprime un cuadrado blanco o negro vacío
            System.out.print(esCasillaClara ? "□ " : "■ "); // Utiliza Unicode para los cuadrados
        } else {
            // Imprime la pieza sobre el fondo que corresponda
            // Podrías intentar algo como:
            // System.out.print(esCasillaClara ? "\u001b[47m" + p.getSimbolo() + " " + "\u001b[0m" : "\u001b[40m" + p.getSimbolo() + " " + "\u001b[0m");
            // Pero esto requiere manejar los colores del terminal, lo cual es más complejo.
            // Por ahora, solo imprime la pieza sobre un espacio.
            System.out.print(p.getSimbolo() + " ");
        }
    }
    // --- FIN CÓDIGO MODIFICADO ---

    public void iniciarTablero(){casillas[0][0]=new Torre(Color.NEGRO,new Posicion(0,0));casillas[0][1]=new Caballo(Color.NEGRO,new Posicion(0,1));casillas[0][2]=new Alfil(Color.NEGRO,new Posicion(0,2));casillas[0][3]=new Reina(Color.NEGRO,new Posicion(0,3));casillas[0][4]=new Rey(Color.NEGRO,new Posicion(0,4));casillas[0][5]=new Alfil(Color.NEGRO,new Posicion(0,5));casillas[0][6]=new Caballo(Color.NEGRO,new Posicion(0,6));casillas[0][7]=new Torre(Color.NEGRO,new Posicion(0,7));for(int i=0;i<8;i++){casillas[1][i]=new Peon(Color.NEGRO,new Posicion(1,i));}for(int i=0;i<8;i++){casillas[6][i]=new Peon(Color.BLANCO,new Posicion(6,i));}casillas[7][0]=new Torre(Color.BLANCO,new Posicion(7,0));casillas[7][1]=new Caballo(Color.BLANCO,new Posicion(7,1));casillas[7][2]=new Alfil(Color.BLANCO,new Posicion(7,2));casillas[7][3]=new Reina(Color.BLANCO,new Posicion(7,3));casillas[7][4]=new Rey(Color.BLANCO,new Posicion(7,4));casillas[7][5]=new Alfil(Color.BLANCO,new Posicion(7,5));casillas[7][6]=new Caballo(Color.BLANCO,new Posicion(7,6));casillas[7][7]=new Torre(Color.BLANCO,new Posicion(7,7));}
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