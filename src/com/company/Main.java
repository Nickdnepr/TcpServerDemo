package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String argv[]) throws Exception {
        String clientSentence;
        String capitalizedSentence;
        ServerSocket serverSocket = new ServerSocket(6789);
        ArrayList<ClientHandler> clients = new ArrayList<>();

        MessageNotifier notifier = new MessageNotifier() {
            @Override
            public void notify(String msg) {
                for (ClientHandler handler : clients) {
                    handler.notifyClient(msg);
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("Waiting for connection");
                    ClientHandler handler = null;
                    try {
                        handler = new ClientHandler(serverSocket.accept(), notifier);
                        clients.add(handler);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("New connection");
                    Thread t = new Thread(handler);
                    t.start();
                }
            }
        }).start();

        Scanner sc = new Scanner(System.in);

        while (true) {
            String msg = sc.nextLine();
            System.out.println("From server: " + msg);
            notifier.notify("From server: " + msg);
        }

//        System.out.println("Waiting for connection");
//        Socket clientSocket = serverSocket.accept();
//        System.out.println("Connected");
//        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
//        PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
//        String data;
//        while ((data = in.readLine()) != null) {
//            System.out.println("Received: " + data);
//            capitalizedSentence = data.toUpperCase();
//            out.println(capitalizedSentence);
//            out.flush();
//        }
    }
}
