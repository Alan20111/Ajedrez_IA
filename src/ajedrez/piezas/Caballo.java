package ajedrez.piezas;

import java.util.ArrayList;
import java.util.List;
import ajedrez.*;

public class Caballo extends Pieza {
    public Caballo(Color color, Posicion posicion) {
        super(color, posicion);
    }

    @Override
    public String getSimbolo() {
        return (getColor() == Color.BLANCO) ? "♘" : "♞";
    }

    @Override
    public List<Movimiento> calcularMovimientosLegales(Tablero tablero) {
        List<Movimiento> movimientos = new ArrayList<>();
        int filaActual = this.posicion.getFila();
        int colActual = this.posicion.getColumna();

        // Estos son los 8 posibles movimientos en "L" de un caballo
        int[][] offsets = {
                {-2, -1}, {-2, 1}, {2, -1}, {2, 1},
                {-1, -2}, {-1, 2}, {1, -2}, {1, 2}
        };

        for (int[] offset : offsets) {
            Posicion nuevaPosicion = new Posicion(filaActual + offset[0], colActual + offset[1]);

            if (tablero.esCasillaValida(nuevaPosicion)) {
                Pieza piezaEnDestino = tablero.getPiezaEn(nuevaPosicion);
                // El movimiento es válido si la casilla está vacía o contiene una pieza enemiga
                if (piezaEnDestino == null || piezaEnDestino.getColor() != this.color) {
                    movimientos.add(new Movimiento(this.posicion, nuevaPosicion));
                }
            }
        }
        return movimientos;
    }
}