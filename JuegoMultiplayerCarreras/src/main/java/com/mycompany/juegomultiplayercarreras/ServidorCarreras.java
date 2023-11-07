package com.mycompany.juegomultiplayercarreras;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServidorCarreras{
    public static void main(String[] args) throws IOException {
        int puerto = 1200;
        boolean jugadorYaConectado = false;
        ArrayList<HiloServer> jugadores = new ArrayList<HiloServer>();
        try {
            ServerSocket serverSocket;
            serverSocket = new ServerSocket(puerto);
            System.out.println("Servidor iniciado");

            
            while (true){
                Socket jugadorConectado = serverSocket.accept();
                for(HiloServer cadaJugador: jugadores){
                    if(cadaJugador.getJugadorConectado().getSocket().equals(jugadorConectado)){
                        jugadorYaConectado = true;
                        cadaJugador.start();
                    }
                }
                if(!jugadorYaConectado ){
                    Jugador nuevoJugador = new Jugador(jugadorConectado);
                System.out.println("Jugador conectado");
                
                HiloServer hiloJugador = new HiloServer(nuevoJugador, jugadores);
                jugadores.add(hiloJugador);
                hiloJugador.setJugadoresConectados(jugadores);
                hiloJugador.start();
                }
                

            }
        }catch (IOException e){
            throw new RuntimeException(e);

        }
    }
}
