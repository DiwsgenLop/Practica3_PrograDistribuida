
//import java.io.*;
import java.rmi.*;

public class FileClient {
    public static void main(String argv[]) {
        if (argv.length < 3) {
            System.out.println("Usage: java FileClient <IP> <operation> <parameter>");
            System.exit(0);
        }
        String ip = argv[0];
        String operation = argv[1];
        String parameter = argv[2];
        try {
            String name = "//" + ip + "/FileServer";
            FileInterface fi = (FileInterface) Naming.lookup(name);

            switch (operation) {
                case "contarLineas":
                    int lineCount = fi.contarLineas(parameter);
                    System.out.println("Número de líneas: " + lineCount);
                    break;
                case "cuentavocales":
                    int vowelCount = fi.cuentavocales(parameter);
                    System.out.println("Número de vocales: " + vowelCount);
                    break;
                case "escribe":
                    //El problema es que OutputStream y InputStream no son serializables y no se pueden transmitir directamente a través de RMI
                    //Solucion beta:
                    
                    fi.escribe(parameter); //Solo pasamos el nombre del archivo donde escribira y la clase por sobrecarga de metodos se enecargara con OutputStream
                    
                    /*
                    FileOutputStream os = new FileOutputStream(parameter);
                    fi.escribe(os);
                    os.close();
                    */
                    System.out.println("Contenido del archivo escrito en "+parameter);
                    break;
                case "imprimir":
                    fi.imprimir();
                    break;
                case "respaldar":
                    fi.respaldar(parameter);
                    System.out.println("Archivo respaldado.");
                    break;
                case "copiar":
                    fi.copiar(parameter);
                    System.out.println("Archivo copiado a " + parameter);
                    break;
                case "renombrar":
                    fi.renombrar(parameter);
                    System.out.println("Archivo renombrado a " + parameter);
                    break;
                case "eliminar":
                    fi.eliminar(parameter);
                    System.out.println("Archivo eliminado.");
                    break;
                default:
                    System.out.println("Operación no válida.");
            }
        } catch (Exception e) {
            System.err.println("FileServer exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}