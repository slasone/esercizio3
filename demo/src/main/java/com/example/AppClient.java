package com.example;
import java.io.*;
import java.net.*;
/**
 * Hello world!
 *
 */
public class AppClient 
{

String nomeServer = "localHost";
    int portaServer = 6789;
    Socket mioSocket;
    BufferedReader tastiera;
    String stringaUtente;
    String stringaRicevutaDalServer;
    DataOutputStream outVersoServer;
    BufferedReader inDalServer;

    public Socket connetti(){
        System.out.println("2... CLIENT partito in esecuzione...");
        try {
            tastiera = new BufferedReader(new InputStreamReader(System.in));

            mioSocket = new Socket(nomeServer, portaServer);

            outVersoServer = new DataOutputStream(mioSocket.getOutputStream());
            inDalServer = new BufferedReader(new InputStreamReader(mioSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.out.println("Host sconosciuto! \n");
        }
            catch(Exception e){
                System.out.println(e.getMessage());
                System.out.println("Errore durante la connessione! \n");
                System.exit(1);
            }
            return mioSocket;
    }



    public void comunica(){
        try{
            for(;;){
                System.out.println("4... Inserisci un numero (0-999) da trasmettere al server: " + '\n');
                
                stringaUtente = tastiera.readLine();

                System.out.println("5... invio il numero al server e attendo ...");
                outVersoServer.writeBytes(stringaUtente + '\n');

                stringaRicevutaDalServer = inDalServer.readLine();
                System.out.println("7 ... risposta dal server " + '\n' + stringaRicevutaDalServer);
                

                if(stringaRicevutaDalServer.equals("Hai indovinato")){
                    System.out.println("8 CLIENT: termina elaborazione e chiude connessione");
                    mioSocket.close();
                    break;
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Errore durante la comunicazione col server !");
            System.exit(1);
        }
    }

    public static void main( String[] args ){
        AppClient client = new AppClient();
        client.connetti();
        client.comunica();
    }
}
