package ajedrez.piezas;

import java.util.ArrayList;
import java.util.List;
import ajedrez.*;

public class Rey extends Pieza {
    private boolean seHaMovido = false;

    public Rey(Color color, Posicion posicion) {
        super(color, posicion);
    }

    public void restaurarEstadoMovimiento() { this.seHaMovido = false; }

    @Override
    public String getSimbolo() { return (getColor() == Color.BLANCO) ? "♔" : "♚"; }

    @Override
    public void setPosicion(Posicion nuevaPosicion) {
        super.setPosicion(nuevaPosicion);
        this.seHaMovido = true;
    }

    @Override
    public List<Movimiento> calcularMovimientosLegales(Tablero tablero) {
        List<Movimiento> movimientos = new ArrayList<>();
        int[][] offsets = {{-1,0},{1,0},{0,-1},{0,1},{-1,-1},{-1,1},{1,-1},{1,1}};

        for (int[] offset : offsets) {
            Posicion nuevaPosicion = new Posicion(posicion.getFila() + offset[0], posicion.getColumna() + offset[1]);
            if (tablero.esCasillaValida(nuevaPosicion)) {
                Pieza piezaEnDestino = tablero.getPiezaEn(nuevaPosicion);
                if (piezaEnDestino == null || piezaEnDestino.getColor() != this.color) {
                    movimientos.add(new Movimiento(this.posicion, nuevaPosicion));
                }
            }
        }

        if (!seHaMovido) {
            // Enroque corto (lado del rey)
            Pieza torreDerecha = tablero.getPiezaEn(posicion.getFila(), 7);
            if (torreDerecha instanceof Torre && !((Torre) torreDerecha).seHaMovido()) {
                if (tablero.getPiezaEn(posicion.getFila(), 5) == null && tablero.getPiezaEn(posicion.getFila(), 6) == null) {
                    movimientos.add(new Movimiento(this.posicion, new Posicion(posicion.getFila(), 6)));
                }
            }
            // Enroque largo (lado de la reina)
            Pieza torreIzquierda = tablero.getPiezaEn(posicion.getFila(), 0);
            if (torreIzquierda instanceof Torre && !((Torre) torreIzquierda).seHaMovido()) {
                if (tablero.getPiezaEn(posicion.getFila(), 1) == null && tablero.getPiezaEn(posicion.getFila(), 2) == null && tablero.getPiezaEn(posicion.getFila(), 3) == null) {
                    movimientos.add(new Movimiento(this.posicion, new Posicion(posicion.getFila(), 2)));
                }
            }
        }
        return movimientos;
    }
}