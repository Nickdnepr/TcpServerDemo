package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket client;
    private BufferedReader out = null;
    private PrintWriter in = null;
    private String name;
    private MessageNotifier notifier;

    public ClientHandler(Socket client, MessageNotifier notifier) {
        this.client = client;
        this.notifier = notifier;
        try {
            out = new BufferedReader(new InputStreamReader(client.getInputStream()));
            in = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        name = "Client_" + id++;
        in.println("Your name is " + name);
    }

    @Override
    public void run() {
        while (true) {
            try {
                String line;
                if ((line = out.readLine()) != null) {
                    notifier.notify("From " + name + ": " + line);
                    System.out.println("From " + name + ": " + line);
                }
//                in.println(line.toUpperCase());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void notifyClient(String msg) {
        in.println(msg);
    }


    private static int id = 0;


}
