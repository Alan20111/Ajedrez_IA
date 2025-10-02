package ajedrez;

import java.util.Scanner;

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
                    System.out.println("\nIniciando partida: Jugador vs. IA...");
                    partida = new Partida(new JugadorHumano(), new JugadorIA());
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