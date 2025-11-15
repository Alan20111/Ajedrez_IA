package ajedrez;

import java.util.List;

public interface Jugador {
    Movimiento obtenerMovimiento(Tablero tablero, List<Movimiento> movimientosValidos);
}