package edu.hm.network.group12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {

    private static final int BUFFER_SIZE = 1400;
    private static final int PORT = 4445;
    private static final int TIMEOUT = 20000;

    public void runUDP() throws IOException {
        int numberOfPakets = 0;
        int sumOfLength = 0;
        long startTime = 0;
        long lastPaket = 0;

        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            socket.setSoTimeout(TIMEOUT);
            boolean firstPaket = true;

            while (true) {
                DatagramPacket p = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
                socket.receive(p);
                if (firstPaket) {
                    startTime = System.currentTimeMillis();
                    firstPaket = false;
                }
                System.out.println(new String(p.getData()));
                numberOfPakets++;
                sumOfLength = sumOfLength + p.getData().length;
                lastPaket = System.currentTimeMillis();
            }
        } catch (SocketTimeoutException te) {
            System.out.println("Nothing received. Timeout!");
            System.out.println(numberOfPakets);
            System.out.println(sumOfLength);
            final long durationInSeconds = (lastPaket - startTime) / 1000;

            System.out.println(durationInSeconds);

            if (durationInSeconds > 0) {
                System.out.println("[UDP/GOODPUT]: " + ((sumOfLength * 1000L) / durationInSeconds) + " kbit/s");
            } else {
                System.out.println("I'm speed! [UDP/GOODPUT]: " + ((sumOfLength * 1000L)) + " kbit/s");
            }
        }
    }

    public void runTCP() throws IOException {
        int numberOfPakets = 0;
        int sumOfLength = 0;
        long startTime = 0;
        long lastPaket = 0;
        boolean running = true;
        boolean firstPaket = true;

        final ServerSocket serverSocket = new ServerSocket(9080);
        final Socket socket = serverSocket.accept();
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        try {
            serverSocket.setSoTimeout(10000);
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                if (firstPaket) {
                    startTime = System.currentTimeMillis();
                    firstPaket = false;
                }
                numberOfPakets++;
                sumOfLength = sumOfLength + inputLine.getBytes().length;
                System.out.println(inputLine);
                lastPaket = System.currentTimeMillis();
            }

            System.out.println("[Number of pakets]: " + numberOfPakets);
            final long durationInSeconds = (lastPaket - startTime) / 1000;
            System.out.println("[TCP/GOODPUT]: " + ((sumOfLength * 1000L) / durationInSeconds) + " kbit/s");

            bufferedReader.close();
            socket.close();
            serverSocket.close();
        } catch (SocketTimeoutException te) {
            System.out.println("Nothing received. Timeout!");
            bufferedReader.close();
            socket.close();
            serverSocket.close();
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.runTCP();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
