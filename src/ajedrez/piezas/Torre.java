package ajedrez.piezas;

import java.util.ArrayList;
import java.util.List;
import ajedrez.*;

public class Torre extends Pieza {
    private boolean seHaMovido = false;

    public Torre(Color color, Posicion posicion) {
        super(color, posicion);
    }

    @Override
    public String getSimbolo() {
        return (getColor() == Color.BLANCO) ? "♖" : "♜";
    }

    @Override
    public void setPosicion(Posicion nuevaPosicion) {
        super.setPosicion(nuevaPosicion);
        this.seHaMovido = true; // Importante para el enroque
    }

    @Override
    public List<Movimiento> calcularMovimientosLegales(Tablero tablero) {
        List<Movimiento> movimientos = new ArrayList<>();

        // Direcciones: arriba, abajo, izquierda, derecha
        int[][] direcciones = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        // Lógica para piezas que se deslizan (Torre, Alfil, Reina)
        for (int[] direccion : direcciones) {
            Posicion siguientePos = new Posicion(posicion.getFila() + direccion[0], posicion.getColumna() + direccion[1]);

            while (tablero.esCasillaValida(siguientePos)) {
                Pieza piezaEnDestino = tablero.getPiezaEn(siguientePos);

                if (piezaEnDestino == null) {
                    // Si la casilla está vacía, es un movimiento válido
                    movimientos.add(new Movimiento(this.posicion, siguientePos));
                } else {
                    // Si hay una pieza enemiga, es un movimiento válido (captura) pero no podemos seguir
                    if (piezaEnDestino.getColor() != this.color) {
                        movimientos.add(new Movimiento(this.posicion, siguientePos));
                    }
                    // Si hay cualquier pieza (amiga o enemiga), bloquea el camino
                    break;
                }
                siguientePos = new Posicion(siguientePos.getFila() + direccion[0], siguientePos.getColumna() + direccion[1]);
            }
        }
        return movimientos;
    }
}