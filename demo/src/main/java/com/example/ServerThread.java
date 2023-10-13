package com.example;
import java.net.*;
import java.io.*;

public class ServerThread extends Thread {
    ServerSocket server = null;
    Socket client = null;
    String stringaRicevuta = null;
    String stringaModificata = null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;

    public ServerThread(Socket socket) {
        this.client = socket;
    }

    public void run(){
        try {
            comunica();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public void comunica() throws Exception{
        inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outVersoClient = new DataOutputStream(client.getOutputStream());

        int numGenerato = (int) (Math.random() * 1000);
        System.out.println("Numero generato: " + numGenerato);

        for(;;){
            stringaRicevuta = inDalClient.readLine();
            int numRicevuto = 0;

            try {
                numRicevuto = Integer.parseInt(stringaRicevuta);
            } catch (Exception e) {
                outVersoClient.writeBytes("Non hai inserito un numero!" + '\n');
            }

            if(numRicevuto == numGenerato){
                outVersoClient.writeBytes("Hai indoviato!" + '\n');
                break;
            }else if(numGenerato > numRicevuto){
                outVersoClient.writeBytes("Il numero da indovinare e' piu' grande" + '\n');
                System.out.println("6 Echo sul server: " + "num piu grande");
            }else {
                outVersoClient.writeBytes("Il numero da indovinare e' piu' piccolo" + '\n');
                System.out.println("6 Echo sul server: " + "num piu piccolo");
            }
        }
        outVersoClient.close();
        inDalClient.close();
        System.out.println("9 Chiusura socket" + client);
        client.close();
    }


    public static void main(String[] args) {
        MultiServer server = new MultiServer();
        server.startServer();
    }
}
