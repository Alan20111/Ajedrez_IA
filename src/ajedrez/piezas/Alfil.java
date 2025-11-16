package ajedrez.piezas;

import ajedrez.Color;
import ajedrez.Movimiento;
import ajedrez.Posicion;
import ajedrez.Tablero;
import java.util.ArrayList;
import java.util.List;

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

        for (int[] direccion : direcciones) {
            Posicion siguientePos = new Posicion(posicion.getFila() + direccion[0], posicion.getColumna() + direccion[1]);

            // --- CORRECCIÓN AQUÍ: Usamos getPiezaEn para verificar validez y contenido ---
            // La condición del while debe verificar que la siguientePos sea válida
            // (es decir, que getPiezaEn no devuelva null por estar fuera del tablero)
            // Y que la casilla no esté ocupada por una pieza del mismo color.
            while (siguientePos.getFila() >= 0 && siguientePos.getFila() < 8 &&
                    siguientePos.getColumna() >= 0 && siguientePos.getColumna() < 8) {

                Pieza piezaEnDestino = tablero.getPiezaEn(siguientePos);

                if (piezaEnDestino == null) {
                    // Si la casilla está vacía, podemos movernos
                    movimientos.add(new Movimiento(this.posicion, siguientePos));
                } else {
                    // Si la casilla está ocupada
                    if (piezaEnDestino.getColor() != this.color) {
                        // Si es una pieza del oponente, podemos capturar y luego paramos
                        movimientos.add(new Movimiento(this.posicion, siguientePos));
                    }
                    // En ambos casos (captura o bloqueado por pieza propia), no podemos seguir en esta dirección
                    break;
                }

                // Mover a la siguiente posición en la misma dirección
                siguientePos = new Posicion(siguientePos.getFila() + direccion[0], siguientePos.getColumna() + direccion[1]);
            }
        }

        return movimientos;
    }
}