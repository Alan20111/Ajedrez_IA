package ajedrez.piezas;

import java.util.ArrayList;
import java.util.List;
import ajedrez.*;

public class Alfil extends Pieza {
    public Alfil(Color color, Posicion posicion) {
        super(color, posicion);
    }

    @Override
    public String getSimbolo() {
        return (getColor() == Color.BLANCO) ? "♗" : "♝";
    }

    @Override
    public List<Movimiento> calcularMovimientosLegales(Tablero tablero) {
        List<Movimiento> movimientos = new ArrayList<>();

        // Direcciones: las cuatro diagonales
        int[][] direcciones = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

        // Lógica para piezas que se deslizan
        for (int[] direccion : direcciones) {
            Posicion siguientePos = new Posicion(posicion.getFila() + direccion[0], posicion.getColumna() + direccion[1]);

            while (tablero.esCasillaValida(siguientePos)) {
                Pieza piezaEnDestino = tablero.getPiezaEn(siguientePos);

                if (piezaEnDestino == null) {
                    movimientos.add(new Movimiento(this.posicion, siguientePos));
                } else {
                    if (piezaEnDestino.getColor() != this.color) {
                        movimientos.add(new Movimiento(this.posicion, siguientePos));
                    }
                    break;
                }
                siguientePos = new Posicion(siguientePos.getFila() + direccion[0], siguientePos.getColumna() + direccion[1]);
            }
        }
        return movimientos;
    }
}