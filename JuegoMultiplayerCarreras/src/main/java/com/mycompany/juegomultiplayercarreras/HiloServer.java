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

    @Override
    public void run() {
        try {
            DataInputStream entrada = new DataInputStream(socket.getInputStream());
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
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

                for (HiloServer jugador : jugadoresConectados) {
                    if (jugador != this) {
                        // Envía el movimiento a otros jugadores
                        jugador.enviarMovimiento(salida,movimientoJugador);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void enviarMovimiento(DataOutputStream salida, String movimiento) {
        try {
            salida.writeUTF(movimiento);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

