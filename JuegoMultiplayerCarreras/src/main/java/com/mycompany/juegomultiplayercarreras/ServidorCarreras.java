package com.mycompany.juegomultiplayercarreras;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServidorCarreras{
    public static void main(String[] args) throws IOException {
        int puerto = 1200;
        
        ArrayList<HiloServer> jugadores = new ArrayList<HiloServer>();
        try {
            ServerSocket serverSocket;
            serverSocket = new ServerSocket(puerto);
            System.out.println("Servidor iniciado");

            
            while (true){
                Socket jugadorConectado = serverSocket.accept();
                Jugador nuevoJugador = new Jugador(jugadorConectado);
                System.out.println("Jugador conectado");
                
                HiloServer hiloJugador = new HiloServer(nuevoJugador, jugadores);
                jugadores.add(hiloJugador);

                hiloJugador.start();

            }
        }catch (IOException e){
            throw new RuntimeException(e);

        }
    }
}
