package Controlador;

import Vista.FileWindow;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Gumi
 */

public class ControladorRuta implements ActionListener  {
    private FileWindow ruta;
    private String dir;
    
    
    public ControladorRuta(FileWindow ruta){
        this.ruta=ruta;
        
        this.ruta.getjFileChooser().addActionListener(this);
        
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();

        // Obtener el tamaño del JFrame
        Dimension tamañoJFrame = this.ruta.getSize();

        // Calcular las coordenadas para centrar el JFrame
        int x = (pantalla.width - tamañoJFrame.width) / 2;
        int y = (pantalla.height - tamañoJFrame.height) / 2;

        // Establecer la ubicación del JFrame en el centro de la pantalla
        this.ruta.setLocation(x, y);
    }

    public void iniciar (){
        this.ruta.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==this.ruta.getjFileChooser()){
            String temp=ruta.getjFileChooser().getSelectedFile().getAbsolutePath();
            dir = temp.replace("\\", "\\\\");
            Controlador.Main.controlador.actualizarruta();
            this.ruta.dispose();
        }
    }
    
    public String getdir(){
        
        return dir;
        
    }
    
}
