package ajedrez.piezas;

import java.util.ArrayList;
import java.util.List;
import ajedrez.*;

public class Rey extends Pieza {
    private boolean seHaMovido = false;

    public Rey(Color color, Posicion posicion) {
        super(color, posicion);
    }

    @Override
    public String getSimbolo() {
        return (getColor() == Color.BLANCO) ? "♔" : "♚";
    }

    @Override
    public void setPosicion(Posicion nuevaPosicion) {
        super.setPosicion(nuevaPosicion);
        this.seHaMovido = true; // Importante para el enroque
    }

    @Override
    public List<Movimiento> calcularMovimientosLegales(Tablero tablero) {
        List<Movimiento> movimientos = new ArrayList<>();

        // Direcciones: las 8 direcciones, pero solo un paso
        int[][] offsets = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}, // Ortogonales
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}  // Diagonales
        };

        for (int[] offset : offsets) {
            Posicion nuevaPosicion = new Posicion(posicion.getFila() + offset[0], posicion.getColumna() + offset[1]);

            if (tablero.esCasillaValida(nuevaPosicion)) {
                Pieza piezaEnDestino = tablero.getPiezaEn(nuevaPosicion);
                // Válido si la casilla está vacía o tiene un enemigo
                if (piezaEnDestino == null || piezaEnDestino.getColor() != this.color) {
                    movimientos.add(new Movimiento(this.posicion, nuevaPosicion));
                }
            }
        }

        // TODO: Implementar la lógica del Enroque (Castling)
        // TODO: Implementar la lógica para no moverse a una casilla en jaque

        return movimientos;
    }
}