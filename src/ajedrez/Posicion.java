package ajedrez;

public class Posicion {
    private final int fila;
    private final int columna;

    public Posicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public int getFila() { return fila; }
    public int getColumna() { return columna; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Posicion otra = (Posicion) obj;
        return fila == otra.fila && columna == otra.columna;
    }

    @Override
    public int hashCode() {
        return 31 * fila + columna;
    }

    /**
     * Convierte las coordenadas internas (ej. fila=6, col=4)
     * a la notación de ajedrez estándar (ej. "e2").
     */
    @Override
    public String toString() {
        // (char)('a' + this.columna) convierte 0->'a', 1->'b', etc.
        // (8 - this.fila) convierte 0->'8', 1->'7', etc.
        return "" + (char)('a' + this.columna) + (8 - this.fila);
    }
}