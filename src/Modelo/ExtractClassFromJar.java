package Modelo;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javax.swing.JOptionPane;

/**
 *
 * @author Gumi
 */

public class ExtractClassFromJar {

    public static void main(String[] args) {
        
    }
    
    public void extract(String dirlib){
        String jarFilePath = dirlib+"\\\\Live2D_Cubism.jar";
        String targetClassPath = "com/live2d/cubism/g.class";
        String outputFilePath = "g.class"; // Nombre del archivo de salida

        try (JarFile jarFile = new JarFile(jarFilePath)) {
            Enumeration<JarEntry> entries = jarFile.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().equals(targetClassPath)) {
                    extractFile(jarFile, entry, outputFilePath);
                    break;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al extraer el archivo: " + e.getMessage());
            
        }
    }

    private static void extractFile(JarFile jarFile, JarEntry entry, String outputFilePath) throws IOException {
        try (InputStream inputStream = jarFile.getInputStream(entry);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
             FileOutputStream outputStream = new FileOutputStream(outputFilePath)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }
}