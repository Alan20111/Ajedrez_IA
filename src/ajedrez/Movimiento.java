package ajedrez;

/**
 * Contenedor de datos inmutable que representa un movimiento desde una Posicion de inicio a una de fin.
 */
public class Movimiento {
    private final Posicion inicio;
    private final Posicion fin;

    public Movimiento(Posicion inicio, Posicion fin) {
        this.inicio = inicio;
        this.fin = fin;
    }

    public Posicion getInicio() {
        return inicio;
    }

    public Posicion getFin() {
        return fin;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Movimiento otro = (Movimiento) obj;
        return inicio.getFila() == otro.inicio.getFila() &&
                inicio.getColumna() == otro.inicio.getColumna() &&
                fin.getFila() == otro.fin.getFila() &&
                fin.getColumna() == otro.fin.getColumna();
    }

    @Override
    public int hashCode() {
        return 31 * (31 * inicio.getFila() + inicio.getColumna()) + (31 * fin.getFila() + fin.getColumna());
    }
}