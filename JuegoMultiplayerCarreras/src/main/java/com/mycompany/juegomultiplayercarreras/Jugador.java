package com.mycompany.juegomultiplayercarreras;

import java.net.Socket;

public class Jugador {

    private static int ID_GENERATOR = 0;
    int id;
    Socket socketJugador;
    
    public Jugador(Socket socket){
        ID_GENERATOR++;
        this.id = ID_GENERATOR;
        this.socketJugador = socket;
    }
    
    public int getId() {
        return id;
    }
    
    public Socket getSocket(){
        return socketJugador;
    }
}
