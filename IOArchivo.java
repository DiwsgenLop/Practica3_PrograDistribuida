
//Librerias necesarias para rmi 
import java.io.*;
/*Excepciones para NotSerializableException
import java.io.IOException;
import java.io.FileOutputStream;
*/
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

//Clase IOArchivo que implementa la interfaz FileInterface
public class IOArchivo extends UnicastRemoteObject implements FileInterface {
    private String nombreArchivo;

    // Constructor con parametro para representar el nombre del archivo en el que
    // operar
    public IOArchivo(String nombreArchivo) throws RemoteException {
        this.nombreArchivo = nombreArchivo;
    }

    // Metodo para contarlineas del archivo dado el nombre del archivo String
    public int contarLineas(String nombreArchivo) throws RemoteException {
        int contador = 0;
        try {
            FileReader fr = new FileReader(nombreArchivo);
            BufferedReader br = new BufferedReader(fr);
            String linea = br.readLine();
            while (linea != null) {
                contador++;
                linea = br.readLine();
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            System.out.println("Error al leer el archivo");
        }
        return contador;
    }

    // Metodo para contarlas vocales del archivo dado el nombre del archivo String
    public int cuentavocales(String nombreArchivo) throws RemoteException {
        int contador = 0;
        try {
            FileReader fr = new FileReader(nombreArchivo);
            BufferedReader br = new BufferedReader(fr);
            String linea = br.readLine();
            while (linea != null) {
                for (int i = 0; i < linea.length(); i++) {
                    if (linea.charAt(i) == 'a' || linea.charAt(i) == 'e' || linea.charAt(i) == 'i'
                            || linea.charAt(i) == 'o' || linea.charAt(i) == 'u') {
                        contador++;
                    }
                }
                linea = br.readLine();
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            System.out.println("Error al leer el archivo");
        }
        return contador;
    }

    // Metodo que escriba el contedio del archivo a OS
    // public void escribe(OutputStream os) throws IOException
    /*
     * public void escribe(OutputStream os) throws IOException{
     * try (InputStream is = new FileInputStream(nombreArchivo)) {
     * byte[] buffer = new byte[1024];
     * int bytesRead;
     * while ((bytesRead = is.read(buffer)) != -1) {
     * //java.io.NOTSerializableException
     * os.write(buffer, 0, bytesRead);
     * }
     * } catch (IOException e) {
     * System.err.println("Error al escribir el archivo en el OutputStream: " +
     * e.getMessage());
     * throw new RemoteException("Error al escribir el archivo en el OutputStream: "
     * + e.getMessage());
     * }
     * }
     */
    // Segunda forma para escribe
    public void escribe(String nombreArchivoDestino) throws IOException {
        try (InputStream is = new FileInputStream(nombreArchivo);
                OutputStream os = new FileOutputStream(nombreArchivoDestino)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo en el OutputStream: " + e.getMessage());
            throw new RemoteException("Error al escribir el archivo en el OutputStream: " + e.getMessage());
        }
    }

    // Metodo para imprimir el contenido del archivo en pantalla
    public void imprimir() throws RemoteException {
        try {
            FileReader fr = new FileReader(nombreArchivo);
            BufferedReader br = new BufferedReader(fr);
            String linea = br.readLine();
            while (linea != null) {
                System.out.println(linea);
                linea = br.readLine();
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            System.out.println("Error al leer el archivo");
        }
    }

    // Metodo para respaldar el archivo
    public void respaldar(String nombreArchivoDestino) throws RemoteException {
        try (InputStream is = new FileInputStream(nombreArchivo);
                OutputStream os = new FileOutputStream(nombreArchivoDestino)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            System.err.println("Error al respaldar el archivo: " + e.getMessage());
            throw new RemoteException("Error al respaldar el archivo: " + e.getMessage());
        }
    }

    // Metodo para copiar el contenido del archivo fuente a un archivo destino
    // 'nombrearchivodestino'
    public void copiar(String nombreArchivoDestino) throws RemoteException {
        try (InputStream is = new FileInputStream(nombreArchivo);
                OutputStream os = new FileOutputStream(nombreArchivoDestino, true)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            // Si el archivo destino ya tiene contenido, se añade, y se diferencia con un
            // salto de linea
            if (new File(nombreArchivoDestino).length() > 0) {
                os.write('\n');
            }
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            System.err.println("Error al copiar el archivo: " + e.getMessage());
            throw new RemoteException("Error al copiar el archivo: " + e.getMessage());
        }
    }

    // Metodo para renombrar el archivo
    public void renombrar(String nuevoNombre) throws RemoteException {
        // renombrarlo
        File archivo = new File(nombreArchivo);
        File archivo2 = new File(nuevoNombre);//
        if (archivo.renameTo(archivo2)) {
            System.out.println("Archivo renombrado a:" + nuevoNombre);//
            // Actualizamos el nombre para que tenga referencia
            this.nombreArchivo = nuevoNombre;
        } else {
            System.out.println("Error al renombrar el archivo");
        }
    }

    // Metodo para eliminar el archivo
    public void eliminar(String nombreArchivo) throws RemoteException {
        File archivo = new File(nombreArchivo);
        if (archivo.delete()) {
            System.out.println("Archivo eliminado");
        } else {
            System.out.println("Error al eliminar el archivo");
        }
    }
}