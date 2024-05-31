
package Modelo;

/**
 *
 * @author Gumi
 */

public class StringToHex {

    

    public String stringToHex(String texto) {
        StringBuilder hexString = new StringBuilder();

        for (char c : texto.toCharArray()) {
            hexString.append(String.format("%02X ", (int) c));
        }

        // Eliminar el espacio final si existe
        if (hexString.length() > 0) {
            hexString.deleteCharAt(hexString.length() - 1);
        }

        return hexString.toString();
    }
}