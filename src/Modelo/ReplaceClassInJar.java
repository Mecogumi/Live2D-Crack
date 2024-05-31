package Modelo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import javax.swing.JOptionPane;

/**
 *
 * @author Gumi
 */

public class ReplaceClassInJar {

    public static void main(String[] args) {
        
    }
    
    public void remplazar (String rutajar,String rutaG){
        String jarFilePath = rutajar+"\\\\Live2D_Cubism.jar";
        String targetClassPath = "com/live2d/cubism/g.class";
        String replacementFilePath = rutaG; // Ruta del archivo g.class modificado

        try {
            // Crear un archivo JAR temporal
            String tempJarFilePath = jarFilePath + ".tmp";
            repackJarFile(jarFilePath, tempJarFilePath, targetClassPath, replacementFilePath);

            // Reemplazar el JAR original con el temporal
            File originalJar = new File(jarFilePath);
            File tempJar = new File(tempJarFilePath);

            if (originalJar.delete() && tempJar.renameTo(originalJar)) {
            } else {
                System.err.println("Error al reemplazar el archivo JAR original.");
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el archivo en el JAR: " + e.getMessage());
        }
    }

    private static void repackJarFile(String originalJarPath, String newJarPath,
                                       String targetClassPath, String replacementFilePath) 
                                       throws IOException {

        try (JarFile jarFile = new JarFile(originalJarPath);
             JarOutputStream jos = new JarOutputStream(new FileOutputStream(newJarPath))) {

            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();

                // Ignorar la carpeta META-INF
                if (entry.getName().startsWith("META-INF/")) {
                    continue;
                }

                // Escribir la entrada al nuevo JAR, reemplazando el archivo si es necesario
                if (entry.getName().equals(targetClassPath)) {
                    addEntryFromFileSystem(jos, replacementFilePath, targetClassPath);
                } else {
                    addEntryFromJar(jarFile, entry, jos);
                }
            }
        }
    }

    // Agrega una entrada al nuevo JAR desde el sistema de archivos
    private static void addEntryFromFileSystem(JarOutputStream jos, String filePath, String entryName) 
                                                throws IOException {

        try (InputStream is = new BufferedInputStream(new FileInputStream(filePath))) {
            ZipEntry ze = new ZipEntry(entryName);
            jos.putNextEntry(ze);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                jos.write(buffer, 0, length);
            }
            jos.closeEntry();
        }
    }

    // Agrega una entrada al nuevo JAR desde el JAR original
    private static void addEntryFromJar(JarFile jarFile, JarEntry entry, JarOutputStream jos) 
                                        throws IOException {

        try (InputStream is = jarFile.getInputStream(entry)) {
            ZipEntry ze = new ZipEntry(entry.getName());
            jos.putNextEntry(ze);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                jos.write(buffer, 0, length);
            }
            jos.closeEntry();
        }
    }
}