package ajedrez;

import ajedrez.piezas.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Partida {
    private Tablero tablero;
    private Jugador jugadorBlanco;
    private Jugador jugadorNegro;
    private Jugador jugadorActual;
    private List<Pieza> piezasCapturadasBlancas;
    private List<Pieza> piezasCapturadasNegras;
    private Movimiento ultimoMovimiento;

    public Partida(Jugador jugadorBlanco, Jugador jugadorNegro) {
        this.tablero = new Tablero();
        this.tablero.iniciarTablero();
        this.jugadorBlanco = jugadorBlanco;
        this.jugadorNegro = jugadorNegro;
        this.jugadorActual = this.jugadorBlanco;
        this.piezasCapturadasBlancas = new ArrayList<>();
        this.piezasCapturadasNegras = new ArrayList<>();
        this.ultimoMovimiento = null;
    }

    public void iniciar() {
        while (true) {
            Color colorJugadorActual = (jugadorActual == jugadorBlanco) ? Color.BLANCO : Color.NEGRO;

            tablero.imprimirTablero(colorJugadorActual, piezasCapturadasBlancas, piezasCapturadasNegras);

            boolean estaEnJaque = reyEstaEnJaque(colorJugadorActual);

            if (estaEnJaque) {
                System.out.println("\n¡El rey " + colorJugadorActual + " está en JAQUE!");
            }

            System.out.println("\nTurno de las " + (colorJugadorActual == Color.BLANCO ? "Blancas." : "Negras."));

            List<Movimiento> movimientosValidos = generarMovimientosValidos(colorJugadorActual, estaEnJaque);
            if (movimientosValidos.isEmpty()) {
                if (estaEnJaque) {
                    System.out.println("¡JAQUE MATE! Ganan las " + (colorJugadorActual == Color.BLANCO ? "Negras." : "Blancas."));
                } else {
                    System.out.println("¡TABLAS! El jugador no tiene movimientos legales (Ahogado).");
                }
                break;
            }

            Movimiento movimiento = jugadorActual.obtenerMovimiento(tablero, movimientosValidos);

            if (jugadorActual instanceof JugadorHumano && movimiento == JugadorHumano.MOVIMIENTO_SALIR) {
                System.out.println("\nPartida terminada por el jugador.");
                return;
            }

            if (jugadorActual instanceof JugadorIA && movimiento != null) {
                Pieza piezaMovidaIA = tablero.getPiezaEn(movimiento.getInicio());
                System.out.println("La IA ha movido " + piezaMovidaIA.getSimbolo() + " de " + movimiento.toString());
            }

            if (movimiento != null && movimientosValidos.contains(movimiento)) {
                Pieza piezaCapturada = procesarMovimiento(movimiento);
                if (piezaCapturada != null) {
                    System.out.println("¡Captura! La pieza " + piezaCapturada.getSimbolo() + " ha sido eliminada.");
                    if (piezaCapturada.getColor() == Color.BLANCO) {
                        piezasCapturadasBlancas.add(piezaCapturada);
                    } else {
                        piezasCapturadasNegras.add(piezaCapturada);
                    }
                }

                manejarPromocion(movimiento);
                ultimoMovimiento = movimiento;
                jugadorActual = (jugadorActual == jugadorBlanco) ? jugadorNegro : jugadorBlanco;
            } else if (movimiento != null) {
                System.out.println("Error: Movimiento ilegal o formato incorrecto. Inténtalo de nuevo.");
            }
        }
    }

    // --- El resto de la clase Partida (métodos privados) no cambia ---
    private Pieza procesarMovimiento(Movimiento mov) {
        Pieza pieza = tablero.getPiezaEn(mov.getInicio());
        Pieza piezaCapturada = tablero.getPiezaEn(mov.getFin());

        if (pieza instanceof Rey) {
            int colDiff = mov.getFin().getColumna() - mov.getInicio().getColumna();
            if (Math.abs(colDiff) == 2) {
                int fila = mov.getInicio().getFila();
                if (colDiff > 0) { tablero.moverPieza(new Movimiento(new Posicion(fila, 7), new Posicion(fila, 5))); }
                else { tablero.moverPieza(new Movimiento(new Posicion(fila, 0), new Posicion(fila, 3))); }
            }
        }

        if (pieza instanceof Peon) {
            boolean esCapturaDiagonal = mov.getInicio().getColumna() != mov.getFin().getColumna();
            if (esCapturaDiagonal && piezaCapturada == null) {
                int filaPeonCapturado = mov.getInicio().getFila();
                int colPeonCapturado = mov.getFin().getColumna();
                piezaCapturada = tablero.getPiezaEn(filaPeonCapturado, colPeonCapturado);
                tablero.reemplazarPieza(new Posicion(filaPeonCapturado, colPeonCapturado), null);
            }
        }

        tablero.moverPieza(mov);
        return piezaCapturada;
    }

    private void manejarPromocion(Movimiento mov) {
        Pieza piezaMovida = tablero.getPiezaEn(mov.getFin());
        if (piezaMovida instanceof Peon) {
            Color color = piezaMovida.getColor();
            int filaFin = mov.getFin().getFila();
            if ((color == Color.BLANCO && filaFin == 0) || (color == Color.NEGRO && filaFin == 7)) {
                System.out.println("¡Promoción de Peón! Elige una pieza (Reyna, Torre, Alfil, Caballo): ");
                Scanner scanner = new Scanner(System.in);
                Pieza nuevaPieza = null;
                while (nuevaPieza == null) {
                    String eleccionInput = scanner.next().trim().toLowerCase();
                    String eleccion = eleccionInput.substring(0, 1).toUpperCase() + eleccionInput.substring(1);
                    switch (eleccion) {
                        case "Reyna": nuevaPieza = new Reina(color, mov.getFin()); break;
                        case "Torre": nuevaPieza = new Torre(color, mov.getFin()); break;
                        case "Alfil": nuevaPieza = new Alfil(color, mov.getFin()); break;
                        case "Caballo": nuevaPieza = new Caballo(color, mov.getFin()); break;
                        default: System.out.println("Elección inválida. Escribe el nombre completo (Reyna, Torre, Alfil, Caballo):");
                    }
                }
                tablero.reemplazarPieza(mov.getFin(), nuevaPieza);
            }
        }
    }

    private List<Movimiento> generarMovimientosValidos(Color color, boolean estaEnJaque) {
        List<Movimiento> movimientosValidos = new ArrayList<>();
        for (Pieza pieza : obtenerTodasLasPiezas(color)) {
            List<Movimiento> movimientosPseudoLegales = (pieza instanceof Peon)
                    ? ((Peon) pieza).calcularMovimientosLegales(tablero, ultimoMovimiento)
                    : pieza.calcularMovimientosLegales(tablero);

            for (Movimiento mov : movimientosPseudoLegales) {
                boolean movimientoValido = true;

                if (pieza instanceof Rey) {
                    int colDiff = mov.getFin().getColumna() - mov.getInicio().getColumna();
                    if (Math.abs(colDiff) == 2) {
                        if (estaEnJaque) {
                            movimientoValido = false;
                        } else {
                            Posicion posPaso;
                            if (colDiff > 0) { posPaso = new Posicion(mov.getInicio().getFila(), mov.getInicio().getColumna() + 1); }
                            else { posPaso = new Posicion(mov.getInicio().getFila(), mov.getInicio().getColumna() - 1); }
                            if (esCasillaAtacada(posPaso, (color == Color.BLANCO) ? Color.NEGRO : Color.BLANCO)) {
                                movimientoValido = false;
                            }
                        }
                    }
                }

                if (!movimientoValido) { continue; }

                Pieza piezaCapturada = tablero.moverPieza(mov);
                if (reyEstaEnJaque(color)) {
                    movimientoValido = false;
                }
                tablero.deshacerMovimiento(mov, piezaCapturada);

                if (movimientoValido) {
                    movimientosValidos.add(mov);
                }
            }
        }
        return movimientosValidos;
    }

    public boolean reyEstaEnJaque(Color colorRey){ Posicion p=encontrarRey(colorRey); return p!=null&&esCasillaAtacada(p,(colorRey==Color.BLANCO)?Color.NEGRO:Color.BLANCO); }
    private Posicion encontrarRey(Color color){ for(int f=0;f<8;f++){for(int c=0;c<8;c++){Pieza p=tablero.getPiezaEn(f,c); if(p instanceof Rey&&p.getColor()==color){return p.getPosicion();}}} return null; }
    public boolean esCasillaAtacada(Posicion pos, Color colorAtacante) {
        for(Pieza p : obtenerTodasLasPiezas(colorAtacante)) {
            List<Movimiento> movimientos = (p instanceof Peon)
                    ? ((Peon) p).calcularMovimientosLegales(tablero, ultimoMovimiento)
                    : p.calcularMovimientosLegales(tablero);
            for(Movimiento m : movimientos) {
                if(m.getFin().equals(pos)) return true;
            }
        }
        return false;
    }
    private List<Pieza> obtenerTodasLasPiezas(Color color){
        List<Pieza> piezas = new ArrayList<>();
        for (int f = 0; f < 8; f++) {
            for (int c = 0; c < 8; c++) {
                Pieza p = tablero.getPiezaEn(f, c);
                if (p != null && p.getColor() == color) {
                    piezas.add(p);
                }
            }
        }
        return piezas;
    }
}