
import java.rmi.*;

public class FileServer {
    public static void main(String argv[]) {
        if (argv.length != 1) {
            System.out.println("Usage: java FileServer <nombreArchivo>");
            System.exit(0);
        }
        try {
            String nombreArchivo = argv[0];
            FileInterface fi = new IOArchivo(nombreArchivo);
            Naming.rebind("//127.0.0.1/FileServer", fi);
            System.out.println("Server ready...");
        } catch (Exception e) {
            System.out.println("FileServer: " + e.getMessage());
            e.printStackTrace();
        }
    }
}