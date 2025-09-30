package ajedrez.piezas;

import java.util.List;
import ajedrez.*;

/**
 * Clase base abstracta para todas las piezas de ajedrez.
 */
public abstract class Pieza {
    protected Color color;
    protected Posicion posicion;

    public Pieza(Color color, Posicion posicion) {
        this.color = color;
        this.posicion = posicion;
    }

    public Color getColor() { return color; }
    public Posicion getPosicion() { return posicion; }
    public void setPosicion(Posicion nuevaPosicion) { this.posicion = nuevaPosicion; }

    public abstract String getSimbolo();
    public abstract List<Movimiento> calcularMovimientosLegales(Tablero tablero);
}