package client;

import java.io.*;
import java.net.*;

public class Echo {
  private static EchoObjectStub ss;

  public static void main(String[] args) {

    if (args.length<2) {
        System.out.println("Usage: Echo <host> <port#>");
        System.exit(1);
    }
    ss = new EchoObjectStub();
    //EJERCICIO: crear una instancia del stub 
    ss.setHostAndPort(args[0],Integer.parseInt(args[1]));

    BufferedReader stdIn = new BufferedReader( new InputStreamReader(System.in));
    PrintWriter stdOut = new PrintWriter(System.out);
    String input,output;
    try {
    	//EJERCICIO: el bucle infinito: 
    	//EJERCICIO: Leer de teclado 
    	for (int i=0;i<10000; i++){
    		int j;
    		byte[] buffer = new byte[80];
    		//E/S con InputStream y OutputStream
    		System.out.println("Teclee una cadena");
    		j = System.in.read(buffer);
    		System.out.print("La cadena: ");
    		System.out.write(buffer,0,j);

    		//Convertimos cadenade bytes a cadena de caracteres (2 bytes)
    		String tira = new String(buffer,0,j);
    		//EJERCICIO: Invocar el stub 
    		
    		String tira2= ss.echo(tira);
    		//EJERCICIO: Imprimir por pantalla 
    	
    		System.out.println("Devolución echo: " + tira2);
    	}

    } catch (UnknownHostException e) {
      System.err.println("Don't know about host: "+ args[0]);
    } catch (IOException e) {
      System.err.println("I/O failed for connection to host: "+args[0]);
    }
  }
}