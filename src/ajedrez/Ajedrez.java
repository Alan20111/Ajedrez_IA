package ajedrez;

import java.util.Scanner;

/**
 * Clase principal que contiene el punto de entrada (main) y el menú del juego.
 */
public class Ajedrez {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Ajedrez de Consola ---");
            System.out.println("1. Jugador vs. Jugador");
            System.out.println("2. Jugador vs. IA");
            System.out.println("3. Salir");
            System.out.print("Elige una opción: ");

            String opcion = scanner.nextLine();
            Partida partida = null;

            switch (opcion) {
                case "1":
                    System.out.println("\nIniciando partida: Jugador vs. Jugador...");
                    partida = new Partida(new JugadorHumano(), new JugadorHumano());
                    partida.iniciar();
                    break;
                case "2":
                    int profundidad;
                    while (true) {
                        System.out.println("\n--- Dificultad de la IA ---");
                        System.out.println("1. Fácil (Piensa 2 plys)");
                        System.out.println("2. Medio (Piensa 4 plys)");
                        System.out.println("3. Difícil (Piensa 6 plys)");
                        System.out.print("Elige una opción: ");

                        String dif = scanner.nextLine();
                        if (dif.equals("1")) {
                            profundidad = 2;
                            break;
                        } else if (dif.equals("2")) {
                            profundidad = 4;
                            break;
                        } else if (dif.equals("3")) {
                            profundidad = 6;
                            break;
                        } else {
                            System.out.println("Opción no válida. Inténtalo de nuevo.");
                        }
                    }

                    System.out.println("\nIniciando partida: Jugador vs. IA (Profundidad " + profundidad + " plys)...");
                    partida = new Partida(new JugadorHumano(), new JugadorIA(profundidad));
                    partida.iniciar();
                    break;
                case "3":
                    System.out.println("Gracias por jugar. ¡Hasta pronto!");
                    return;
                default:
                    System.out.println("Opción no válida. Por favor, elige 1, 2 o 3.");
                    break;
            }
        }
    }
}