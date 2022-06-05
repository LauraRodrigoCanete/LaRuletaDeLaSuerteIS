package Net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONObject;

import Net.Server.SpecialMsg;
import model.Model;
import model.ModelStatus;


public class Client extends Thread {
	private BufferedReader in;
	private PrintWriter out;
	private String connectionIp;
	private Socket serverConnection;
	private int port;
	private String _name;
	private Model model;
	
	public Client(String ip, int port, String name, Model model) throws IOException {
		this._name = name;
		this.connectionIp = ip;
		this.port = port;
		this.model = model;
    	
		this.startClient();
	}

    private void startClient() {//Método para iniciar el cliente
        try {
        	//Inicializamos la conexión (socket con el server)
        	this.serverConnection = new Socket (this.connectionIp, this.port);
        	
        	//Inicializamos los flujos de entrada y salida con el server
        	in = new BufferedReader(new InputStreamReader(serverConnection.getInputStream()));
        	out = new PrintWriter(serverConnection.getOutputStream(), true); 	
        	
        	//Le mandamos al Server nuestro nombre
        	out.println(this._name);
        	
        	//Iniciamos el hilo del cliente
        	this.start();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
	public void run() {
		String inputLine;
		SpecialMsg sMsg;
		try {
			while ((inputLine = in.readLine()) != null) {
				sMsg = SpecialMsg.valueOf(in.readLine());
				if(sMsg == null || SpecialMsg.EXIT_GRANTED.equals(sMsg)) break;
				else if (SpecialMsg.SHUT.equals(sMsg)) model.serverShutGame();
				else {
					this.model.deserialize(inputLine); //Actualizar según el echo
				if(SpecialMsg.START.equals(sMsg)) model.netStart();
					
				JSONObject msgJO = new JSONObject(inputLine);
				String status = msgJO.getString("status");
					if((ModelStatus.JUEGO_ACABADO).equals(ModelStatus.valueOf(status))) {
						model.endGame();
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				serverConnection.close();
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
    
	public synchronized void sendMsg(String game_serial, SpecialMsg sMsg) {
		this.out.println(game_serial);
		this.out.println(sMsg);
	}
    
    public String get_name() {
    	return _name;
    }
}