package ajedrez;

import ajedrez.piezas.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class JugadorIA implements Jugador {

    @Override
    public Movimiento obtenerMovimiento(Tablero tablero, List<Movimiento> movimientosValidos) {
        System.out.println("La IA está pensando su movimiento...");
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        Movimiento mejorMovimiento = null;
        if (movimientosValidos.isEmpty()) {
            return null;
        }

        // Obtiene el color de la IA del primer movimiento válido
        Color colorIA = tablero.getPiezaEn(movimientosValidos.get(0).getInicio()).getColor();
        int mejorPuntuacion = (colorIA == Color.BLANCO) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Movimiento mov : movimientosValidos) {
            // Simula el movimiento
            Pieza piezaCapturada = tablero.moverPieza(mov);
            int puntuacionActual = evaluarTablero(tablero);
            // Deshace el movimiento
            tablero.deshacerMovimiento(mov, piezaCapturada);

            if (colorIA == Color.BLANCO) {
                if (puntuacionActual > mejorPuntuacion) {
                    mejorPuntuacion = puntuacionActual;
                    mejorMovimiento = mov;
                }
            } else { // colorIA es NEGRO
                if (puntuacionActual < mejorPuntuacion) {
                    mejorPuntuacion = puntuacionActual;
                    mejorMovimiento = mov;
                }
            }
        }

        System.out.println("La IA ha decidido su movimiento.");
        return mejorMovimiento;
    }

    private int evaluarTablero(Tablero tablero) {
        int puntuacionTotal = 0;
        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                Pieza p = tablero.getPiezaEn(f, c);
                if (p != null) {
                    puntuacionTotal += getValorPieza(p);
                }
            }
        }
        return puntuacionTotal;
    }

    private int getValorPieza(Pieza pieza) {
        int valor = 0;
        if (pieza instanceof Reina) valor = 9;
        if (pieza instanceof Torre) valor = 5;
        if (pieza instanceof Alfil) valor = 3;
        if (pieza instanceof Caballo) valor = 3;
        if (pieza instanceof Peon) valor = 1;

        return (pieza.getColor() == Color.BLANCO) ? valor : -valor;
    }
}