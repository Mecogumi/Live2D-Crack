package Controlador;

import Vista.Principal;

/**
 *
 * @author Gumi
 */

public class Main {
    public static ControladorPrincipal controlador;
    public static void main(String[] args) {
        controlador = new ControladorPrincipal(new Principal());
        controlador.iniciar();
    }
}
