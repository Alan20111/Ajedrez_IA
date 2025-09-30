package ajedrez.piezas;

import java.util.ArrayList;
import java.util.List;
import ajedrez.*;

public class Peon extends Pieza {
    private boolean seHaMovido = false;

    public Peon(Color color, Posicion posicion) {
        super(color, posicion);
    }

    @Override
    public String getSimbolo() { return (getColor() == Color.BLANCO) ? "♙" : "♟"; }

    @Override
    public void setPosicion(Posicion nuevaPosicion) {
        super.setPosicion(nuevaPosicion);
        this.seHaMovido = true;
    }

    @Override
    public List<Movimiento> calcularMovimientosLegales(Tablero tablero) {
        List<Movimiento> movimientos = new ArrayList<>();
        int filaActual = this.posicion.getFila();
        int colActual = this.posicion.getColumna();
        int direccion = (this.color == Color.BLANCO) ? -1 : 1;

        Posicion unPaso = new Posicion(filaActual + direccion, colActual);
        if (tablero.esCasillaValida(unPaso) && tablero.getPiezaEn(unPaso) == null) {
            movimientos.add(new Movimiento(this.posicion, unPaso));
            if (!this.seHaMovido) {
                Posicion dosPasos = new Posicion(filaActual + 2 * direccion, colActual);
                if (tablero.esCasillaValida(dosPasos) && tablero.getPiezaEn(dosPasos) == null) {
                    movimientos.add(new Movimiento(this.posicion, dosPasos));
                }
            }
        }

        int[] colsDiagonales = { colActual - 1, colActual + 1 };
        for (int col : colsDiagonales) {
            Posicion diagonal = new Posicion(filaActual + direccion, col);
            if (tablero.esCasillaValida(diagonal)) {
                Pieza piezaEnDiagonal = tablero.getPiezaEn(diagonal);
                if (piezaEnDiagonal != null && piezaEnDiagonal.getColor() != this.color) {
                    movimientos.add(new Movimiento(this.posicion, diagonal));
                }
            }
        }
        return movimientos;
    }
}