
package Modelo;

/**
 *
 * @author Gumi
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import javax.swing.JOptionPane;

public class CopyFileOverwrite {

    public static void main(String[] args) {

        // Directorio de origen y destino
        
    }
    public void remplazar (String origen, String destino){
        

        // Comprueba si el archivo de destino existe
        File archivoDestino = new File(destino);
        if (archivoDestino.exists()) {
        } else {
        }

        try (FileInputStream inputStream = new FileInputStream(origen);
             FileOutputStream outputStream = new FileOutputStream(destino)) {

            FileChannel inputChannel = inputStream.getChannel();
            FileChannel outputChannel = outputStream.getChannel();

            // Transfiere los datos del archivo de origen al archivo de destino
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());


        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al copiar el archivo: " + e.getMessage());
            
        }
    }
}