package ajedrez;

import java.util.Scanner;

/**
 * Implementación de un Jugador controlado por un humano a través de la consola.
 */
public class JugadorHumano implements Jugador {
    private Scanner scanner;

    public JugadorHumano() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public Movimiento obtenerMovimiento(Tablero tablero) {
        while (true) {
            System.out.print("Ingrese su movimiento (ej: e2e4): ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.length() != 4) {
                System.out.println("Error: Formato inválido. Deben ser 4 caracteres. Inténtalo de nuevo.");
                continue;
            }

            try {
                char colInicioChar = input.charAt(0);
                char filaInicioChar = input.charAt(1);
                char colFinChar = input.charAt(2);
                char filaFinChar = input.charAt(3);

                int colInicio = colInicioChar - 'a';
                int filaInicio = 8 - (filaInicioChar - '0');
                int colFin = colFinChar - 'a';
                int filaFin = 8 - (filaFinChar - '0');

                Posicion inicio = new Posicion(filaInicio, colInicio);
                Posicion fin = new Posicion(filaFin, colFin);

                return new Movimiento(inicio, fin);

            } catch (Exception e) {
                System.out.println("Error: Coordenadas inválidas. Usa el formato 'a1h8'. Inténtalo de nuevo.");
            }
        }
    }
}