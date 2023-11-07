package com.mycompany.juegomultiplayercarreras;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.Executors;
import javax.swing.JLabel;


public class Cliente {

 
    public static void main(String[] args){
        Partida partida = new Partida();
        partida.setVisible(true);
        String serverAddress = "localhost";
        int serverPort = 1200;

        JLabel coche1 = partida.getCoche1Label();
        JLabel coche2 = partida.getCoche2Label();


        try {
            //Se establece conexión con el servidor a través de la ip y el puerto.
            Socket socket = new Socket(serverAddress, serverPort);
            //Entrada de datos.
            DataInputStream entrada = new DataInputStream(socket.getInputStream());
            //Salida de datos.
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
            

            partida.addKeyListener(new KeyListener() {

                @Override
                public void keyTyped(KeyEvent e) {
                    // No necesitas implementar este método
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    // Cuando se presiona una tecla
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        // El jugador presionó la tecla de flecha izquierda
                        try {
                            if (!socket.isClosed()){
                                salida.writeUTF("LEFT");  
                            }
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                        try {
                            if (!socket.isClosed()){
                                salida.writeUTF("RIGHT");  // Envía un mensaje al servidor
                            }
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    // También puedes agregar lógica para otras teclas, como la flecha derecha.
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    // No necesitas implementar este método
                }
            });

        try {
            while (true) {
                String mensaje = entrada.readUTF(); // Lee el mensaje del servidor
                System.out.println("Mensaje recibido del servidor: " + mensaje); // Imprime el mensaje
            }
        } catch (IOException e) {
            // Maneja errores de lectura o cierre de la conexión
            e.printStackTrace();
        }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
