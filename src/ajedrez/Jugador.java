package ajedrez;

/**
 * Interfaz que define el comportamiento que debe tener cualquier tipo de jugador (humano, IA, etc.).
 */
public interface Jugador {
    Movimiento obtenerMovimiento(Tablero tablero);
}