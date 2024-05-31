package Modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author Gumi
 */

public class ReplaceClassValue {

    public static void main(String[] args) {
        
    }
    
    public void remplazar(String ruta, String shaOriginal, String shaReoplace){
        String classFilePath = ruta; // Reemplaza con la ruta al archivo .class
        String originalHex = new StringToHex().stringToHex(shaOriginal);
        String replacementHex = new StringToHex().stringToHex(shaReoplace);
        
        //String originalHex = "35 34 37 62 65 62 36 32 36 65 66 33 37 37 32 36 64 38 33 63 61 32 34 36 39 38 63 33 33 35 36 34 63 35 31 39 61 38 37 65 33 66 32 35 65 62 32 30 64 63 62 65 39 32 32 37 38 30 34 35 64 34 32 38";
        //String replacementHex = "61 30 65 66 30 62 32 64 66 64 36 37 39 31 37 38 37 62 66 39 63 31 63 30 37 62 63 34 63 30 33 30 37 38 63 66 63 34 61 39 34 36 61 64 63 66 61 36 35 32 64 37 30 38 66 36 63 39 39 62 64 36 30 61";

        try {
            // Leer el archivo .class en bytes
            byte[] classFileBytes = readClassFile(classFilePath);

            // Convertir el hex original y el reemplazo a arrays de bytes
            byte[] originalBytes = hexToBytes(originalHex);
            byte[] replacementBytes = hexToBytes(replacementHex);

            // Buscar y reemplazar los bytes
            replaceBytes(classFileBytes, originalBytes, replacementBytes);

            // Escribir los bytes modificados al archivo .class
            writeClassFile(classFilePath, classFileBytes);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Error al leer o escribir el archivo .class: " + e.getMessage());
        }
    }

    // Lee el archivo .class en bytes
    private static byte[] readClassFile(String classFilePath) throws IOException {
        File classFile = new File(classFilePath);
        FileInputStream fis = new FileInputStream(classFile);
        byte[] classFileBytes = new byte[(int) classFile.length()];
        fis.read(classFileBytes);
        fis.close();
        return classFileBytes;
    }

    // Escribe los bytes modificados al archivo .class
    private static void writeClassFile(String classFilePath, byte[] classFileBytes) throws IOException {
        FileOutputStream fos = new FileOutputStream(classFilePath);
        fos.write(classFileBytes);
        fos.close();
    }

    // Convierte un string hexadecimal a un array de bytes
    private static byte[] hexToBytes(String hexString) {
        String[] hexValues = hexString.split(" ");
        byte[] bytes = new byte[hexValues.length];
        for (int i = 0; i < hexValues.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hexValues[i], 16);
        }
        return bytes;
    }

    // Busca y reemplaza los bytes en el archivo .class
    private static void replaceBytes(byte[] classFileBytes, byte[] originalBytes, byte[] replacementBytes) {
        int index = findBytes(classFileBytes, originalBytes);
        if (index != -1) {
            System.arraycopy(replacementBytes, 0, classFileBytes, index, originalBytes.length);
        } else {
            System.err.println("Los valores hexadecimales originales no se encontraron en el archivo.");
        }
    }

    // Busca los bytes originales en el archivo .class
    private static int findBytes(byte[] classFileBytes, byte[] originalBytes) {
        for (int i = 0; i <= classFileBytes.length - originalBytes.length; i++) {
            if (Arrays.equals(Arrays.copyOfRange(classFileBytes, i, i + originalBytes.length), originalBytes)) {
                return i;
            }
        }
        return -1;
    }
}