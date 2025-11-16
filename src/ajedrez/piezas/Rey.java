package ajedrez.piezas;

import java.util.ArrayList;
import java.util.List;
import ajedrez.*;

public class Rey extends Pieza {
    private boolean seHaMovido = false; // <<< AÑADE ESTA LÍNEA

    public Rey(Color color, Posicion posicion) {
        super(color, posicion);
    }

    @Override
    public String getSimbolo() {
        return (getColor() == Color.BLANCO) ? "♔" : "♚";
    }

    // <<< AÑADE ESTE MÉTODO (getter para el estado de movimiento)
    public boolean seHaMovido() {
        return seHaMovido;
    }
    // <<< FIN DE AÑADIR MÉTODO

    public void restaurarEstadoMovimiento() {
        this.seHaMovido = false;
    }

    @Override
    public void setPosicion(Posicion nuevaPosicion) {
        super.setPosicion(nuevaPosicion);
        this.seHaMovido = true; // El rey se ha movido una vez que su posición cambia
    }

    @Override
    public List<Movimiento> calcularMovimientosLegales(Tablero tablero) {
        List<Movimiento> movimientos = new ArrayList<>();
        int filaActual = posicion.getFila();
        int colActual = posicion.getColumna();

        int[][] direcciones = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}, // Horizontales y verticales
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}  // Diagonales
        };

        for (int[] dir : direcciones) {
            Posicion siguientePos = new Posicion(filaActual + dir[0], colActual + dir[1]);
            if (tablero.esCasillaValida(siguientePos)) {
                Pieza piezaEnDestino = tablero.getPiezaEn(siguientePos);
                if (piezaEnDestino == null || piezaEnDestino.getColor() != this.color) {
                    movimientos.add(new Movimiento(this.posicion, siguientePos));
                }
            }
        }

        // --- Lógica del Enroque ---
        if (!seHaMovido) { // Si el rey no se ha movido
            int filaRey = this.posicion.getFila();

            // Enroque corto (lado del rey)
            // Verificar casilla g1/g8 (columna 6) y f1/f8 (columna 5)
            // y que la torre no se haya movido
            Pieza torreDerecha = tablero.getPiezaEn(filaRey, 7);
            if (torreDerecha instanceof Torre && !((Torre) torreDerecha).seHaMovido()) {
                if (tablero.getPiezaEn(filaRey, 5) == null && tablero.getPiezaEn(filaRey, 6) == null) {
                    movimientos.add(new Movimiento(this.posicion, new Posicion(filaRey, 6))); // Mueve el rey a g1/g8
                }
            }

            // Enroque largo (lado de la reina)
            // Verificar casilla c1/c8 (columna 2), d1/d8 (columna 3) y b1/b8 (columna 1)
            // y que la torre no se haya movido
            Pieza torreIzquierda = tablero.getPiezaEn(filaRey, 0);
            if (torreIzquierda instanceof Torre && !((Torre) torreIzquierda).seHaMovido()) {
                if (tablero.getPiezaEn(filaRey, 1) == null && tablero.getPiezaEn(filaRey, 2) == null && tablero.getPiezaEn(filaRey, 3) == null) {
                    movimientos.add(new Movimiento(this.posicion, new Posicion(filaRey, 2))); // Mueve el rey a c1/c8
                }
            }
        }
        return movimientos;
    }
}