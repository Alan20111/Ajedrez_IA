package ajedrez.piezas;

import java.util.ArrayList;
import java.util.List;
import ajedrez.*;

public class Peon extends Pieza {
    private boolean seHaMovido = false;

    public Peon(Color color, Posicion posicion) {
        super(color, posicion);
    }

    public void restaurarEstadoMovimiento() {
        int filaOriginal = (this.color == Color.BLANCO) ? 6 : 1;
        if (this.posicion.getFila() == filaOriginal) {
            this.seHaMovido = false;
        }
    }

    @Override
    public String getSimbolo() { return (getColor() == Color.BLANCO) ? "♙" : "♟"; }

    @Override
    public void setPosicion(Posicion nuevaPosicion) {
        super.setPosicion(nuevaPosicion);
        this.seHaMovido = true;
    }

    public List<Movimiento> calcularMovimientosLegales(Tablero tablero, Movimiento ultimoMovimientoOponente) {
        List<Movimiento> movimientos = new ArrayList<>();
        int filaActual = this.posicion.getFila();
        int colActual = this.posicion.getColumna();
        int direccion = (this.color == Color.BLANCO) ? -1 : 1;

        Posicion unPaso = new Posicion(filaActual + direccion, colActual);
        if (tablero.esCasillaValida(unPaso) && tablero.getPiezaEn(unPaso) == null) {
            movimientos.add(new Movimiento(this.posicion, unPaso));
            if (!this.seHaMovido) { // Esta condición ahora funcionará correctamente
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

        if (ultimoMovimientoOponente != null) {
            Pieza piezaMovida = tablero.getPiezaEn(ultimoMovimientoOponente.getFin());
            if (piezaMovida instanceof Peon &&
                    Math.abs(ultimoMovimientoOponente.getFin().getFila() - ultimoMovimientoOponente.getInicio().getFila()) == 2 &&
                    ultimoMovimientoOponente.getFin().getFila() == filaActual &&
                    Math.abs(ultimoMovimientoOponente.getFin().getColumna() - colActual) == 1)
            {
                Posicion posCaptura = new Posicion(filaActual + direccion, ultimoMovimientoOponente.getFin().getColumna());
                movimientos.add(new Movimiento(this.posicion, posCaptura));
            }
        }
        return movimientos;
    }

    @Override
    public List<Movimiento> calcularMovimientosLegales(Tablero tablero) {
        return calcularMovimientosLegales(tablero, null);
    }
}