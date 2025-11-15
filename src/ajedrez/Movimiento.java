package ajedrez;

import ajedrez.piezas.Pieza;

public class Movimiento {
    private final Posicion inicio;
    private final Posicion fin;

    public Movimiento(Posicion inicio, Posicion fin) {
        this.inicio = inicio;
        this.fin = fin;
    }

    public Posicion getInicio() { return inicio; }
    public Posicion getFin() { return fin; }

    public Pieza getPieza(Tablero tablero) {
        return tablero.getPiezaEn(this.inicio);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Movimiento otro = (Movimiento) obj;
        return inicio.equals(otro.inicio) && fin.equals(otro.fin);
    }

    @Override
    public int hashCode() {
        return 31 * inicio.hashCode() + fin.hashCode();
    }

    @Override
    public String toString() {
        return this.inicio.toString() + this.fin.toString();
    }
}