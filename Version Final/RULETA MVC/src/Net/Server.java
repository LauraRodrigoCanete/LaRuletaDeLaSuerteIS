package Net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import model.Model;

/* PROTOCOLO:
 * Las comunicaciones entre cliente y servidor se harán por medio de dos mensajes (game_serial y special).
 * Primero se envía game_serial y acto seguido, special. (La única vez que no se envían los dos mensajes a la
 * vez es cuando el cliente se registra en el servidor y envía su nombre).
 * 
 * Special cuando lo recibe el servidor:
 *    "EXIT": el cliente quiere salir del juego.
 *    "SHUT": para que el servidor no diga nada más a los clientes porque se va a cerrar
 *    
 * Special cuando lo recibe el cliente:
 *    "EXIT_GRANTED": el servidor va a desconectar al cliente.
 *    "START": Se ha iniciado el juego
 *    "SHUT": Se ha cerrado el servidor, hay que cerrar las conexiones (recíprocamente)
 * */

public class Server {
	private int numPlayers;
	private List<String> names;
	
	private List<EchoClientHandler> handlers;

	private ServerSocket ss;
	private Model model;
	
	    
	public Server(int numJugadores, int puerto, Model model) throws IOException{
    	this.numPlayers = numJugadores;
    	this.names = new ArrayList<String>();
    	this.ss = new ServerSocket(puerto);
    	this.model = model;
    	this.handlers = new ArrayList<EchoClientHandler>();
    	this.startServer();
    }
	
	//TIPO ENUMERADO AUXILIAR
	public enum SpecialMsg { START, EXIT, SHUT, EXIT_GRANTED, NONE};
	
	//CLASE ANÓNIMA AUXILIAR
	public class EchoClientHandler extends Thread{
		private Socket clientSocket;
		private PrintWriter out;
		private BufferedReader in;
		private Server server;
		
		public EchoClientHandler(Socket socket, Server server) throws IOException{
			clientSocket = socket;
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			this.server = server;
		}
		
		public void run() {
			try {
				String inputLine;
				SpecialMsg sMsg;
				addName(in.readLine());
				
				while((inputLine = in.readLine()) != null) {
					sMsg = SpecialMsg.valueOf(in.readLine());
					if(sMsg == null) break;
					
					if(!(SpecialMsg.SHUT.equals(sMsg))) {
						if(SpecialMsg.EXIT.equals(sMsg)) {
							handlers.remove(this);
						}
						server.echo(inputLine, SpecialMsg.NONE);
					}
					
					if(SpecialMsg.EXIT.equals(sMsg) || SpecialMsg.SHUT.equals(sMsg)) {
						send("", SpecialMsg.EXIT_GRANTED);
						break;
					}
				}
				in.close();
				out.close();
				clientSocket.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	
		public synchronized void send(String game_serial, SpecialMsg sMsg) {
			out.println(game_serial);
			out.println(sMsg);
		}
	}
	
	  
    public void startServer() throws IOException {
    	int i = 0;
       	while(i < numPlayers) {
       		try {
       			Socket socket = ss.accept();
       			EchoClientHandler handler = new EchoClientHandler(socket, this);
       			handlers.add(handler);
       			handler.start();
       			++i;
       		}
       		catch (Exception e){
       			e.printStackTrace();
       		}
       	}
       	this.model.newGame(this.numPlayers, true, names);
       	
       	this.echo(this.model.serialize(), SpecialMsg.START);
    }
    
    private synchronized void addName(String name) {
    	names.add(name);
    }
    
    private synchronized void echo(String game_serial, SpecialMsg sMsg) {
    	for (int i = 0; i < handlers.size(); i++) {
    		this.handlers.get(i).send(game_serial, sMsg);
    	}
    }
    
	public void shut() {
		this.echo("", SpecialMsg.SHUT);
		try {
			Thread.sleep(1000);
			this.ss.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
}