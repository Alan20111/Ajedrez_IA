package ajedrez;

/**
 * Contenedor de datos inmutable para las coordenadas (fila, columna) del tablero.
 * VERSIÓN CORREGIDA con los métodos equals() y hashCode() para una correcta comparación.
 */
public class Posicion {
    private final int fila;
    private final int columna;

    public Posicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    // --- MÉTODOS AÑADIDOS PARA CORREGIR LA DETECCIÓN DE JAQUE ---

    @Override
    public boolean equals(Object obj) {
        // Un objeto es igual a sí mismo
        if (this == obj) return true;
        // Un objeto no puede ser igual a null o a un objeto de otra clase
        if (obj == null || getClass() != obj.getClass()) return false;
        // Comparamos los valores de fila y columna
        Posicion otra = (Posicion) obj;
        return fila == otra.fila && columna == otra.columna;
    }

    @Override
    public int hashCode() {
        // Genera un número único basado en la fila y la columna
        return 31 * fila + columna;
    }
}