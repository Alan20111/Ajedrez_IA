package ajedrez;

import ajedrez.piezas.*;
import java.util.ArrayList;
import java.util.List;

public class JugadorIA implements Jugador {

    private final int profundidadBusqueda;

    // --- Tablas Posicionales ---
    private static final int[] PEON_SCORE = {
            900, 900, 900, 900, 900, 900, 900, 900, // Fila de Promoción
            50,  50,  50,  50,  50,  50,  50,  50,
            10,  10,  20,  30,  30,  20,  10,  10,
            5,   5,  10,  25,  25,  10,   5,   5,
            0,   0,   0,  20,  20,   0,   0,   0,
            5,  -5, -10,   0,   0, -10,  -5,   5,
            5,  10,  10, -20, -20,  10,  10,   5,
            0,   0,   0,   0,   0,   0,   0,   0
    };
    private static final int[] CABALLO_SCORE = {
            -50,-40,-30,-30,-30,-30,-40,-50,
            -40,-20,  0,  0,  0,  0,-20,-40,
            -30,  0, 10, 15, 15, 10,  0,-30,
            -30,  5, 15, 20, 20, 15,  5,-30,
            -30,  0, 15, 20, 20, 15,  0,-30,
            -30,  5, 10, 15, 15, 10,  5,-30,
            -40,-20,  0,  5,  5,  0,-20,-40,
            -50,-40,-30,-30,-30,-30,-40,-50
    };
    private static final int[] ALFIL_SCORE = {
            -20,-10,-10,-10,-10,-10,-10,-20,
            -10,  0,  0,  0,  0,  0,  0,-10,
            -10,  0,  5, 10, 10,  5,  0,-10,
            -10,  5,  5, 10, 10,  5,  5,-10,
            -10,  0, 10, 10, 10, 10,  0,-10,
            -10, 10, 10, 10, 10, 10, 10,-10,
            -10,  5,  0,  0,  0,  0,  5,-10,
            -20,-10,-10,-10,-10,-10,-10,-20
    };
    private static final int[] TORRE_SCORE = {
            0,  0,  0,  0,  0,  0,  0,  0,
            5, 10, 10, 10, 10, 10, 10,  5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            0,  0,  0,  5,  5,  0,  0,  0
    };
    private static final int[] REINA_SCORE = {
            -20,-10,-10, -5, -5,-10,-10,-20,
            -10,  0,  0,  0,  0,  0,  0,-10,
            -10,  0,  5,  5,  5,  5,  0,-10,
            -5,  0,  5,  5,  5,  5,  0, -5,
            0,  0,  5,  5,  5,  5,  0, -5,
            -10,  5,  5,  5,  5,  5,  0,-10,
            -10,  0,  5,  0,  0,  0,  0,-10,
            -20,-10,-10, -5, -5,-10,-10,-20
    };
    private static final int[] REY_SCORE = {
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -20,-30,-30,-40,-40,-30,-30,-20,
            -10,-20,-20,-20,-20,-20,-20,-10,
            20, 20,  0,  0,  0,  0, 20, 20,
            20, 30, 10,  0,  0, 10, 30, 20
    };

    public JugadorIA(int profundidad) {
        this.profundidadBusqueda = Math.max(1, profundidad);
    }

    @Override
    public Movimiento obtenerMovimiento(Tablero tablero, List<Movimiento> movimientosValidos) {
        System.out.println("La IA ("+ this.profundidadBusqueda +" plys) está pensando su movimiento...");
        long startTime = System.currentTimeMillis();

        Movimiento mejorMovimiento = null;
        if (movimientosValidos.isEmpty()) { return null; }

        Color colorIA = tablero.getPiezaEn(movimientosValidos.get(0).getInicio()).getColor();

        if (colorIA == Color.BLANCO) {
            int mejorPuntuacion = Integer.MIN_VALUE;
            for (Movimiento mov : movimientosValidos) {
                Pieza piezaCapturada = tablero.moverPieza(mov);
                int puntuacion = minimax(tablero, profundidadBusqueda - 1, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                tablero.deshacerMovimiento(mov, piezaCapturada);
                if (puntuacion > mejorPuntuacion) {
                    mejorPuntuacion = puntuacion;
                    mejorMovimiento = mov;
                }
            }
        } else {
            int mejorPuntuacion = Integer.MAX_VALUE;
            for (Movimiento mov : movimientosValidos) {
                Pieza piezaCapturada = tablero.moverPieza(mov);
                int puntuacion = minimax(tablero, profundidadBusqueda - 1, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
                tablero.deshacerMovimiento(mov, piezaCapturada);
                if (puntuacion < mejorPuntuacion) {
                    mejorPuntuacion = puntuacion;
                    mejorMovimiento = mov;
                }
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("La IA ha decidido su movimiento en " + (endTime - startTime) + " ms.");
        return mejorMovimiento;
    }

    private int minimax(Tablero tablero, int profundidad, boolean esTurnoMax, int alfa, int beta) {
        List<Movimiento> movimientos = generarMovimientosPara(tablero, esTurnoMax ? Color.BLANCO : Color.NEGRO);
        if (profundidad == 0 || movimientos.isEmpty()) {
            return evaluarTablero(tablero);
        }

        if (esTurnoMax) {
            int mejorPuntuacion = Integer.MIN_VALUE;
            for (Movimiento mov : movimientos) {
                Pieza capturada = tablero.moverPieza(mov);
                int puntuacion = minimax(tablero, profundidad - 1, false, alfa, beta);
                tablero.deshacerMovimiento(mov, capturada);
                mejorPuntuacion = Math.max(mejorPuntuacion, puntuacion);
                alfa = Math.max(alfa, mejorPuntuacion);
                if (beta <= alfa) { break; }
            }
            return mejorPuntuacion;
        } else {
            int mejorPuntuacion = Integer.MAX_VALUE;
            for (Movimiento mov : movimientos) {
                Pieza capturada = tablero.moverPieza(mov);
                int puntuacion = minimax(tablero, profundidad - 1, true, alfa, beta);
                tablero.deshacerMovimiento(mov, capturada);
                mejorPuntuacion = Math.min(mejorPuntuacion, puntuacion);
                beta = Math.min(beta, mejorPuntuacion);
                if (beta <= alfa) { break; }
            }
            return mejorPuntuacion;
        }
    }

    private List<Movimiento> generarMovimientosPara(Tablero tablero, Color color) {
        List<Movimiento> movimientosValidos = new ArrayList<>();
        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                Pieza pieza = tablero.getPiezaEn(f, c);
                if (pieza != null && pieza.getColor() == color) {
                    movimientosValidos.addAll(pieza.calcularMovimientosLegales(tablero));
                }
            }
        }
        return movimientosValidos;
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
        int valorMaterial = 0;
        int valorPosicional = 0;
        int fila = pieza.getPosicion().getFila();
        int col = pieza.getPosicion().getColumna();
        int indice = fila * 8 + col;

        if (pieza instanceof Reina)  { valorMaterial = 900; valorPosicional = REINA_SCORE[indice]; }
        if (pieza instanceof Torre)  { valorMaterial = 500; valorPosicional = TORRE_SCORE[indice]; }
        if (pieza instanceof Alfil)  { valorMaterial = 330; valorPosicional = ALFIL_SCORE[indice]; }
        if (pieza instanceof Caballo){ valorMaterial = 320; valorPosicional = CABALLO_SCORE[indice]; }
        if (pieza instanceof Peon)   { valorMaterial = 100; valorPosicional = PEON_SCORE[indice]; }
        if (pieza instanceof Rey)    { valorMaterial = 20000; valorPosicional = REY_SCORE[indice]; }

        if (pieza.getColor() == Color.BLANCO) {
            return valorMaterial + valorPosicional;
        } else {
            int indiceInvertido = 63 - indice;
            if (pieza instanceof Reina)  valorPosicional = REINA_SCORE[indiceInvertido];
            if (pieza instanceof Torre)  valorPosicional = TORRE_SCORE[indiceInvertido];
            if (pieza instanceof Alfil)  valorPosicional = ALFIL_SCORE[indiceInvertido];
            if (pieza instanceof Caballo)valorPosicional = CABALLO_SCORE[indiceInvertido];
            if (pieza instanceof Peon)   valorPosicional = PEON_SCORE[indiceInvertido];
            if (pieza instanceof Rey)    valorPosicional = REY_SCORE[indiceInvertido];

            return -(valorMaterial + valorPosicional);
        }
    }
}