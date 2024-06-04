package Controlador;

import Modelo.CopyFileOverwrite;
import Modelo.ExtractClassFromJar;
import Modelo.FileExistsCheck;
import Modelo.ReplaceClassInJar;
import Modelo.ReplaceClassValue;
import Modelo.Sha256;
import Vista.FileWindow;
import Vista.Principal;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Gumi
 */

public class ControladorPrincipal implements ActionListener {
    private Principal principal;
    private ControladorRuta ruta = new ControladorRuta(new FileWindow());
    private String dir;
    private String rutaLibrerias;
    private FileExistsCheck comprobar = new FileExistsCheck();
    
    public ControladorPrincipal(Principal principal){
        this.principal=principal;
        dir=System.getProperty("user.dir");
        actualizarruta(dir);
        this.principal.getCambiarRuta().addActionListener(this);
        this.principal.getCancelar().addActionListener(this);
        this.principal.getIniciar().addActionListener(this);
        
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();

        // Obtener el tamaño del JFrame
        Dimension tamañoJFrame = this.principal.getSize();

        // Calcular las coordenadas para centrar el JFrame
        int x = (pantalla.width - tamañoJFrame.width) / 2;
        int y = (pantalla.height - tamañoJFrame.height) / 2;

        // Establecer la ubicación del JFrame en el centro de la pantalla
        this.principal.setLocation(x, y);
    }
    
    public void iniciar (){
        this.principal.setVisible(true);
        this.principal.getInfo().setText("Ready");
    }
    
    public void actualizarruta(String dir){
        this.dir=dir;
        this.principal.getRuta().setText(this.dir);
        this.principal.getInfo().setText(this.principal.getInfo().getText()+"\n"+"New rute changed");
    }
    
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource()==this.principal.getCancelar()){
            
            this.principal.getInfo().setText(this.principal.getInfo().getText()+"\n"+"Closing");
            
        }
        if (e.getSource()==this.principal.getCambiarRuta()){
            ruta.iniciar();
        }
        if (e.getSource()==this.principal.getIniciar()){
           this.principal.getInfo().setText(this.principal.getInfo().getText()+"\n"+"Loading, please dont close");
            
           if (comprobar.comprobar(dir)){
               this.principal.getInfo().setText(this.principal.getInfo().getText()+"\n"+"Checking directories");
               String rutaGclass =System.getProperty("user.dir");
               rutaGclass=rutaGclass.replace("\\", "\\\\");
               rutaGclass=rutaGclass+"\\\\g.class";
               
               String rutaRLMcrackeado= System.getProperty("user.dir");
               rutaRLMcrackeado=rutaRLMcrackeado.replace("\\", "\\\\");
               rutaRLMcrackeado=rutaRLMcrackeado+"\\\\rlm1501.jar";
               
               rutaLibrerias=dir+"\\\\app\\\\lib";
               String rutaRLMoriginal=rutaLibrerias+"\\\\rlm1501.jar";
               
               this.principal.getInfo().setText(this.principal.getInfo().getText()+"\n"+"Extracting g,cass from JAR");
               ExtractClassFromJar extraer = new ExtractClassFromJar();
               extraer.extract(rutaLibrerias);
               
               this.principal.getInfo().setText(this.principal.getInfo().getText()+"\n"+"Changing hexadecimal values");
               Sha256 cheksum = new Sha256();
               String originalSha = cheksum.checkSum(rutaRLMoriginal);
               String crackedSha = cheksum.checkSum(rutaRLMcrackeado);
               ReplaceClassValue remplazarSHA = new ReplaceClassValue();
               remplazarSHA.remplazar(rutaGclass,originalSha,crackedSha);
               
               this.principal.getInfo().setText(this.principal.getInfo().getText()+"\n"+"Importing g.class to JAR");
               ReplaceClassInJar remplazarjar = new ReplaceClassInJar();
               remplazarjar.remplazar(rutaLibrerias, rutaGclass);
               
               this.principal.getInfo().setText(this.principal.getInfo().getText()+"\n"+"Copying rlm1501.jar");
               CopyFileOverwrite remplazarRLM = new CopyFileOverwrite();
               remplazarRLM.remplazar(rutaRLMcrackeado, rutaRLMoriginal);
               
               this.principal.getInfo().setText(this.principal.getInfo().getText()+"\n"+"Done");
               
           }
           
           
        }
    }
}
