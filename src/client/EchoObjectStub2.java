package client;

import java.net.*;
import java.io.*;

import rmi.EchoInt;

import java.util.Timer;
import java.util.TimerTask;

public class EchoObjectStub2 implements EchoInt {

  private Socket echoSocket = null;
  private PrintWriter os = null;
  private BufferedReader is = null;
  private String host = "localhost";
  private int port=7;
  private String output = "Error";
  private boolean connected  = false;
  Timeout tout=null;
  


  public void setHostAndPort(String host, int port) {
    this.host= host; this.port =port;
    tout = new Timeout(10,this);
  }

  public String echo(String input) throws java.rmi.RemoteException {
    connect();
    if (echoSocket != null && os != null && is != null) {
    try {
         os.println(input);
         os.flush();
         output= is.readLine();
      } catch (IOException e) {
        System.err.println("I/O failed in reading/writing socket");
      }
    }
    programDisconnection();
    return output;
  }

  private synchronized void connect() throws java.rmi.RemoteException {
	//EJERCICIO: lo mismo que en EchoObjectStub 
	  if(!connected){
		  try {
			echoSocket= new Socket(host,port);
			System.out.print(" nueva conexion\n"); 
			is = new BufferedReader( new InputStreamReader(echoSocket.getInputStream()));
	        os = new PrintWriter(echoSocket.getOutputStream());
	      
			connected=true;
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
	  }else {
		  tout.cancel();
		  System.out.print("reusando conexion\n");
	  }
  }
  

  private synchronized void disconnect(){
	//EJERCICIO: lo mismo que en EchoObjectStub 
	  try {
			echoSocket.close();
			os.close();
	        is.close();
			connected=false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
  }

  private synchronized void programDisconnection(){
    tout.start();
  }

  class Timeout {
     Timer timer;
     EchoObjectStub2 stub;
     int seconds;

     public Timeout (int seconds, EchoObjectStub2 stub) {
       this.seconds = seconds;
       this.stub = stub;
     }

     public void start() {
    	 
    	 timer = new Timer();
    	 timer.schedule(new TimeoutTask(), seconds*1000);
    	//EJERCICIO 
     }

     public void cancel() {
    	//EJERCICIO 
    	 timer.cancel();
     }

     class TimeoutTask extends TimerTask {
    	 
		@Override
		public void run() {
			timer.cancel();
			disconnect();
		}
    	
     }

   }
}




