package ajedrez;

/**
 * Clase principal que contiene el punto de entrada (main) para iniciar la aplicación.
 */
public class Ajedrez {
    public static void main(String[] args) {
        System.out.println("--- Ajedrez de Consola ---");
        Partida partida = new Partida();
        partida.iniciar();
    }
}