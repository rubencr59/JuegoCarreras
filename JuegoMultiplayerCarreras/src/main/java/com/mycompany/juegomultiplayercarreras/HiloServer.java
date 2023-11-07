package com.mycompany.juegomultiplayercarreras;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class HiloServer extends Thread {
    private Socket socket;
    private Jugador jugadorConectado;
    private ArrayList<HiloServer> jugadoresConectados; // Lista de hilos de jugadores

    public HiloServer(Jugador jugadorNuevo, ArrayList<HiloServer> jugadoresConectados) {
        this.jugadorConectado = jugadorNuevo;
        this.socket = jugadorNuevo.socketJugador;
        this.jugadoresConectados = jugadoresConectados;
    }
    
    public void setJugadoresConectados(ArrayList<HiloServer> jugadoresConectados){
        this.jugadoresConectados = jugadoresConectados;
    }
    
    public Jugador getJugadorConectado(){
        return jugadorConectado;
    }

    @Override
    public void run() {
        try {
            DataInputStream entrada = new DataInputStream(socket.getInputStream());
            //Envia el id del jugador al cliente.

            while (true) {
                String movimientoJugador = entrada.readUTF();
                System.out.println(this.jugadorConectado.getId() +"");
                if(this.jugadorConectado.getId()== 1){
                    movimientoJugador = movimientoJugador + " 1";
                }else if(this.jugadorConectado.getId()== 2){
                    movimientoJugador = movimientoJugador + " 2";
                }
              
                System.out.println("Movimiento recibido: " + movimientoJugador);
                enviarMovimientoATodos(movimientoJugador);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void enviarMovimientoATodos( String movimiento) {
        try {
            for (HiloServer jugador : this.jugadoresConectados) {
                DataOutputStream salida = new DataOutputStream(jugador.socket.getOutputStream());

                if (jugador != this) {
                    // Envía el movimiento a otros jugadores
                    salida.writeUTF(movimiento);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

