//import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileInterface extends Remote {
    //Metodos de IOArchivo
    int contarLineas(String nombrearchivo) throws RemoteException;  //Contar Lineas
    int cuentavocales(String nombrearchivo) throws RemoteException; //Contar vocales
    //void escribe(ByteArrayOutputStream os) throws IOException; 
    void escribe(String nombreArchivoDestino) throws IOException;
    void escribe(OutputStream os) throws IOException;           //Escribir contenido del archivo a OS
    void imprimir() throws RemoteException;                         //Imprimir contenido del archivo en pantalla del servidor
    void respaldar(String nombrearchivo) throws RemoteException;    //Respaldar archivo 
    void copiar(String nombrearchivodestino) throws RemoteException; //Copiar contenido del archivo original en otro archivo destino
    void renombrar(String nombrearchivo) throws RemoteException;    //Renombrar el archivo original
    void eliminar(String nombrearchivo) throws RemoteException;     //Eliminar el archivo original
}