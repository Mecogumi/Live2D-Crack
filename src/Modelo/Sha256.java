package Modelo;

import java.io.FileInputStream;
import java.security.MessageDigest;
import javax.swing.JOptionPane;

/**
 *
 * @author Gumi
 */

public class Sha256 {

    public String checkSum(String fileDir) {
        try (FileInputStream fis = new FileInputStream(fileDir)) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
            byte[] hash = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al convertir hexadecimales: "+ e.getMessage());
            return null;
        }
    }

}