package edu.hm.network.group12;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class Client {

    private static final int NUMBER_OF_PAKETS = 15;
    private static final int DELAY = 1000;

    private final DatagramSocket datagramSocket;

    public Client() throws SocketException {
        this.datagramSocket = new DatagramSocket();
    }

    public void runUDP() throws IOException, InterruptedException {
        long startTime = 0;
        long lastPaket = 0;
        boolean firstPaket = true;
        int sumOfLength = 0;

        final byte[] data = getClass().getClassLoader().getResourceAsStream("data.txt").readAllBytes();

        for (int paket = 0; paket < NUMBER_OF_PAKETS; paket++) {
            final DatagramPacket p = new DatagramPacket(data, data.length, InetAddress.getByName("192.168.1.10"), 4445);
            sumOfLength = sumOfLength + p.getData().length;
            if (firstPaket) {
                firstPaket = false;
                startTime = System.currentTimeMillis();
            }
            datagramSocket.send(p);
            lastPaket = System.currentTimeMillis();
            Thread.sleep(DELAY);
        }
        datagramSocket.close();

        final long durationInSeconds = (lastPaket - startTime) / 1000;

        if (durationInSeconds > 0) {
            System.out.println("[UDP/GOODPUT]: " + ((sumOfLength * 1000L) / durationInSeconds) + " kbit/s");
        } else {
            System.out.println("I'm speed! [UDP/GOODPUT]: " + ((sumOfLength * 1000L)) + " kbit/s");
        }
    }

    public void runTCP() throws IOException, InterruptedException {
        long startTime = 0;
        long endTime = 0;
        boolean firstPaket = true;
        int sizeOfAllPakets = 0;
        final byte[] data = getClass().getClassLoader().getResourceAsStream("data.txt").readAllBytes();

        final Socket socket = new Socket("192.168.1.10", 9080);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        for (int paket = 0; paket < NUMBER_OF_PAKETS; paket++) {
            sizeOfAllPakets = sizeOfAllPakets + data.length;
            if (firstPaket) {
                firstPaket = false;
                startTime = System.currentTimeMillis();
            }
            bufferedWriter.write(new String(data) + "\n");
            bufferedWriter.flush();
            endTime = System.currentTimeMillis();
            Thread.sleep(DELAY);
        }
        bufferedWriter.close();
        socket.close();

        final long durationInSeconds = (endTime - startTime) / 1000;
        System.out.println("[TCP/GOODPUT]: " + ((sizeOfAllPakets * 1000L) / durationInSeconds)+ " kbit/s");
    }

    public static void main(String[] args) {
        try {
            final Client client = new Client();
            client.runTCP();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
