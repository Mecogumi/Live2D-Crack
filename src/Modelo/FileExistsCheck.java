package Modelo;

/**
 *
 * @author Gumi
 */
import java.io.File;
import javax.swing.JOptionPane;

public class FileExistsCheck {

    public static void main(String[] args) {
        
    }
    
    public boolean comprobar (String ruta){
        String rutaPrincipal = ruta;
        String nombreArchivo = "CubismEditor5.exe";

        String rutaCompleta = rutaPrincipal + "\\" + nombreArchivo; // Construye la ruta completa

        File archivo = new File(rutaCompleta); // Crea un objeto File

        if (archivo.exists()) { // Verifica si el archivo existe
            return true;
        } 
        JOptionPane.showMessageDialog(null,"Error ruta incorrecta");
        return false;
    }
}