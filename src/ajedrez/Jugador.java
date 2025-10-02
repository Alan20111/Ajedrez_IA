package ajedrez;

import java.util.List;

public interface Jugador {
    // Le pasamos la lista de movimientos válidos para que el jugador (especialmente la IA) elija uno.
    Movimiento obtenerMovimiento(Tablero tablero, List<Movimiento> movimientosValidos);
}