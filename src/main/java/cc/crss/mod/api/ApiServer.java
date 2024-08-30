package cc.crss.mod.api;

import cc.crss.mod.api.data.ServerInfo;
import net.minecraft.MinecraftVersion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.world.dimension.DimensionType;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static cc.crss.mod.CRSSMod.LOGGER;

public class ApiServer {
    private static ApiServer instance;

    public static MinecraftServer server;

    private Thread thread;

    public ApiServer(MinecraftServer server) {
        if (instance != null)
            throw new RuntimeException("API Server already started.");

        ApiServer.server = server;

        start();
    }

    public static ApiServer getInstance() {
        return instance;
    }

    public static void createInstance(MinecraftServer server) {
        instance = new ApiServer(server);
    }

    private void start() {
        this.thread = new Thread(
            new ServerRunnable(25580)
        );

        this.thread.start();
    }

    public void stop() {
        this.thread.interrupt();

        instance = null;
    }

    static class ServerRunnable implements Runnable {
        private int port;

        public ServerRunnable(int port) {
            this.port = port;
        }

        @Override
        public void run() {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("Server is listening on port " + port);

                while (true) {
                    Socket clientSocket = serverSocket.accept();

                    clientSocket.setTcpNoDelay(true);

                    DataInputStream dataInputStream = new DataInputStream(
                        clientSocket.getInputStream()
                    );

                    DataOutputStream dataOutputStream = new DataOutputStream(
                        clientSocket.getOutputStream()
                    );

                    byte packetId = dataInputStream.readByte();

                    switch (packetId) {
                        case 0x00:
                            MinecraftServer server = ApiServer.server;

                            ArrayList<String> worlds = new ArrayList<>();

                            server.getWorlds().forEach(world -> {
                                worlds.add(world.getDimension().getType().toString());
                            });

                            String jsonData = new ServerInfo(
                                server.getVersion(),
                                server.getCurrentPlayerCount(),
                                worlds.toArray(new String[0])
                            ).toJson();

                            int length = jsonData.length();

                            byte[] responseBytes = new byte[1 + 4 + length];

                            responseBytes[0] = 0x00;

                            responseBytes[1] = (byte) ((length >> 24) & 0xFF);
                            responseBytes[2] = (byte) ((length >> 16) & 0xFF);
                            responseBytes[3] = (byte) ((length >> 8) & 0xFF);
                            responseBytes[4] = (byte) (length & 0xFF);

                            System.arraycopy(jsonData.getBytes(), 0, responseBytes, 5, length);

                            dataOutputStream.write(responseBytes);
                            dataOutputStream.flush();

                            break;
                        default:
                            LOGGER.warn("Received unknown packet: {}", packetId);

                            break;
                    }
                }
            } catch (IOException e) {
                LOGGER.error("Error starting API server", e);
            }
        }
    }
}
