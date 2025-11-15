package ajedrez;

import java.util.List;
import java.util.Scanner;

public class JugadorHumano implements Jugador {

    private Scanner scanner;
    public static final Movimiento MOVIMIENTO_SALIR = new Movimiento(new Posicion(-1, -1), new Posicion(-1, -1));

    public JugadorHumano() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public Movimiento obtenerMovimiento(Tablero tablero, List<Movimiento> movimientosValidos) {
        System.out.print("Ingrese su movimiento (ej: e2e4 o 'salir' para terminar): ");
        String input = scanner.nextLine().toLowerCase();

        if (input.equals("salir")) {
            return MOVIMIENTO_SALIR; // Devuelve el movimiento especial para salir
        }

        if (input.length() != 4) {
            System.out.println("Error: Formato inválido. Deben ser 4 caracteres (ej: e2e4). Inténtalo de nuevo.");
            return null; // Devuelve null si el formato es INVÁLIDO, para que el jugador reintente
        }

        try {
            Posicion inicio = parsearPosicion(input.substring(0, 2));
            Posicion fin = parsearPosicion(input.substring(2, 4));

            Movimiento intentoMovimiento = new Movimiento(inicio, fin);

            if (!movimientosValidos.contains(intentoMovimiento)) {
                System.out.println("Error: Movimiento ilegal. La pieza no puede moverse así o deja al rey en jaque. Inténtalo de nuevo.");
                return null; // Devuelve null si el movimiento es ILEGAL
            }

            return intentoMovimiento; // Devuelve el movimiento válido

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage() + " Inténtalo de nuevo.");
            return null; // Devuelve null si hay un error de parseo
        }
    }

    private Posicion parsearPosicion(String s) {
        if (s.length() != 2) {
            throw new IllegalArgumentException("La posición debe tener 2 caracteres.");
        }
        char colChar = s.charAt(0);
        char filaChar = s.charAt(1);

        if (colChar < 'a' || colChar > 'h') {
            throw new IllegalArgumentException("Columna inválida: " + colChar + ". Debe ser de 'a' a 'h'.");
        }
        if (filaChar < '1' || filaChar > '8') {
            throw new IllegalArgumentException("Fila inválida: " + filaChar + ". Debe ser de '1' a '8'.");
        }

        int columna = colChar - 'a';
        int fila = 8 - (filaChar - '0');

        return new Posicion(fila, columna);
    }
}