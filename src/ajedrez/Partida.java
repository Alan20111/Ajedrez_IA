package ajedrez;

import ajedrez.piezas.Pieza;
import java.util.ArrayList;
import java.util.List;

public class Partida {
    private Tablero tablero;
    private Jugador jugadorBlanco;
    private Jugador jugadorNegro;
    private Jugador jugadorActual;

    // --- NUEVAS LISTAS PARA GUARDAR LAS PIEZAS CAPTURADAS ---
    private List<Pieza> piezasCapturadasBlancas; // Piezas blancas capturadas por las negras
    private List<Pieza> piezasCapturadasNegras;  // Piezas negras capturadas por las blancas

    public Partida() {
        tablero = new Tablero();
        tablero.iniciarTablero();
        jugadorBlanco = new JugadorHumano();
        jugadorNegro = new JugadorHumano();
        jugadorActual = jugadorBlanco;

        // Inicializamos las listas
        piezasCapturadasBlancas = new ArrayList<>();
        piezasCapturadasNegras = new ArrayList<>();
    }

    public void iniciar() {
        while (true) {
            Color colorJugadorActual = (jugadorActual == jugadorBlanco) ? Color.BLANCO : Color.NEGRO;

            // --- LLAMADA MODIFICADA: AHORA PASAMOS LAS LISTAS DE CAPTURADOS ---
            tablero.imprimirTablero(colorJugadorActual, piezasCapturadasBlancas, piezasCapturadasNegras);

            if (colorJugadorActual == Color.BLANCO) {
                System.out.println("\nTurno de las Blancas.");
            } else {
                System.out.println("\nTurno de las Negras.");
            }

            Movimiento movimiento = jugadorActual.obtenerMovimiento(tablero);

            if (movimiento != null) {
                Pieza piezaAMover = tablero.getPiezaEn(movimiento.getInicio());

                if (piezaAMover == null || piezaAMover.getColor() != colorJugadorActual) {
                    System.out.println("Error: Movimiento inválido.");
                    continue;
                }

                List<Movimiento> movimientosLegales = piezaAMover.calcularMovimientosLegales(tablero);
                if (!movimientosLegales.contains(movimiento)) {
                    System.out.println("Error: Movimiento ilegal para esa pieza.");
                    continue;
                }

                // --- LÓGICA DE CAPTURA MODIFICADA ---
                Pieza piezaCapturada = tablero.moverPieza(movimiento);

                if (piezaCapturada != null) {
                    // Imprimimos el mensaje de captura
                    System.out.println("¡Captura! La pieza " + piezaCapturada.getSimbolo() + " ha sido eliminada.");
                    // Añadimos la pieza a la lista correcta
                    if (piezaCapturada.getColor() == Color.BLANCO) {
                        piezasCapturadasBlancas.add(piezaCapturada);
                    } else {
                        piezasCapturadasNegras.add(piezaCapturada);
                    }
                }
            }

            jugadorActual = (jugadorActual == jugadorBlanco) ? jugadorNegro : jugadorBlanco;
        }
    }
}