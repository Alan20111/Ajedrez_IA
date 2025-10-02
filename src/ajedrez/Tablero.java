package ajedrez;

import ajedrez.piezas.*;
import java.util.List;

public class Tablero {
    private final Pieza[][] casillas;

    // --- MÉTODO MODIFICADO PARA CORREGIR EL ENROQUE ---
    public void deshacerMovimiento(Movimiento movimiento, Pieza piezaCapturada) {
        Posicion inicio = movimiento.getInicio();
        Posicion fin = movimiento.getFin();
        Pieza piezaMovida = getPiezaEn(fin);

        casillas[inicio.getFila()][inicio.getColumna()] = piezaMovida;
        if (piezaMovida != null) {
            piezaMovida.setPosicion(inicio);

            // Lógica para restaurar el estado "seHaMovido"
            if (piezaMovida instanceof Peon) {
                ((Peon) piezaMovida).restaurarEstadoMovimiento();
            }
            if (piezaMovida instanceof Rey) {
                int filaOriginal = (piezaMovida.getColor() == Color.BLANCO) ? 7 : 0;
                if (inicio.getFila() == filaOriginal && inicio.getColumna() == 4) {
                    ((Rey) piezaMovida).restaurarEstadoMovimiento();
                }
            }
            if (piezaMovida instanceof Torre) {
                int filaOriginal = (piezaMovida.getColor() == Color.BLANCO) ? 7 : 0;
                if (inicio.getFila() == filaOriginal && (inicio.getColumna() == 0 || inicio.getColumna() == 7)) {
                    ((Torre) piezaMovida).restaurarEstadoMovimiento();
                }
            }
        }

        casillas[fin.getFila()][fin.getColumna()] = piezaCapturada;
    }

    // --- EL RESTO DE LA CLASE NO CAMBIA ---
    public Tablero() { this.casillas = new Pieza[8][8]; }
    public void reemplazarPieza(Posicion pos, Pieza nuevaPieza) { if(esCasillaValida(pos)){casillas[pos.getFila()][pos.getColumna()]=nuevaPieza;}}
    public Pieza getPiezaEn(Posicion pos) { if (!esCasillaValida(pos)) return null; return casillas[pos.getFila()][pos.getColumna()]; }
    public Pieza getPiezaEn(int fila, int col) { return casillas[fila][col]; }
    public boolean esCasillaValida(Posicion pos) { int f=pos.getFila(),c=pos.getColumna(); return f>=0&&f<8&&c>=0&&c<8; }
    public Pieza moverPieza(Movimiento movimiento) {
        Posicion inicio = movimiento.getInicio(); Posicion fin = movimiento.getFin();
        Pieza piezaAMover = getPiezaEn(inicio); Pieza piezaCapturada = getPiezaEn(fin);
        casillas[fin.getFila()][fin.getColumna()] = piezaAMover;
        casillas[inicio.getFila()][inicio.getColumna()] = null;
        if (piezaAMover != null) { piezaAMover.setPosicion(fin); }
        return piezaCapturada;
    }
    public void imprimirTablero(Color j, List<Pieza> cb, List<Pieza> cn) {
        System.out.print("\nCapturadas por Negras: "); for(Pieza p:cb){System.out.print(p.getSimbolo()+" ");}
        System.out.print("\n   a b c d e f g h\n  -----------------\n");
        if(j==Color.BLANCO){for(int f=0;f<8;f++){System.out.print((8-f)+"|");for(int c=0;c<8;c++){imprimirCasilla(f,c);}System.out.println(" |"+(8-f));}}
        else{for(int f=7;f>=0;f--){System.out.print((8-f)+"|");for(int c=0;c<8;c++){imprimirCasilla(f,c);}System.out.println(" |"+(8-f));}}
        System.out.println("  -----------------\n   a b c d e f g h");
        System.out.print("Capturadas por Blancas: "); for(Pieza p:cn){System.out.print(p.getSimbolo()+" ");} System.out.println();
    }
    private void imprimirCasilla(int f,int c){Pieza p=casillas[f][c];if(p==null){System.out.print("· ");}else{System.out.print(p.getSimbolo()+" ");}}
    public void iniciarTablero(){casillas[0][0]=new Torre(Color.NEGRO,new Posicion(0,0));casillas[0][1]=new Caballo(Color.NEGRO,new Posicion(0,1));casillas[0][2]=new Alfil(Color.NEGRO,new Posicion(0,2));casillas[0][3]=new Reina(Color.NEGRO,new Posicion(0,3));casillas[0][4]=new Rey(Color.NEGRO,new Posicion(0,4));casillas[0][5]=new Alfil(Color.NEGRO,new Posicion(0,5));casillas[0][6]=new Caballo(Color.NEGRO,new Posicion(0,6));casillas[0][7]=new Torre(Color.NEGRO,new Posicion(0,7));for(int i=0;i<8;i++){casillas[1][i]=new Peon(Color.NEGRO,new Posicion(1,i));}for(int i=0;i<8;i++){casillas[6][i]=new Peon(Color.BLANCO,new Posicion(6,i));}casillas[7][0]=new Torre(Color.BLANCO,new Posicion(7,0));casillas[7][1]=new Caballo(Color.BLANCO,new Posicion(7,1));casillas[7][2]=new Alfil(Color.BLANCO,new Posicion(7,2));casillas[7][3]=new Reina(Color.BLANCO,new Posicion(7,3));casillas[7][4]=new Rey(Color.BLANCO,new Posicion(7,4));casillas[7][5]=new Alfil(Color.BLANCO,new Posicion(7,5));casillas[7][6]=new Caballo(Color.BLANCO,new Posicion(7,6));casillas[7][7]=new Torre(Color.BLANCO,new Posicion(7,7));}
}