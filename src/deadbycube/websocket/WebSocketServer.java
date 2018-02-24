package deadbycube.websocket;

import deadbycube.DeadByCube;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WebSocketServer implements Runnable {

    private static final Logger LOGGER = Logger.getLogger("WebSocket");

    private final DeadByCube plugin;
    private final ServerSocket serverSocket = new ServerSocket();
    private final HashMap<Player, Socket> playerSocketMap = new HashMap<>();

    public WebSocketServer(DeadByCube plugin) throws IOException {
        this.plugin = plugin;
    }

    public void bind(int port) {
        try {
            this.serverSocket.bind(new InetSocketAddress(port));
            Thread webSocketServerThread = new Thread(this::start, "Server");
            webSocketServerThread.setDaemon(true);
            webSocketServerThread.start();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Unable to bind the web socket server", e);
        }
    }

    private void start() {
        while (!serverSocket.isClosed()) {
            try {
                LOGGER.info("Waiting for connection...");

                Socket socket = serverSocket.accept();
                socket.setTcpNoDelay(true);
                socket.setKeepAlive(false);
                socket.setSoTimeout(30000);

                String ipAddress = socket.getInetAddress().getHostAddress();
                int port = socket.getPort();

                LOGGER.log(Level.INFO, "Connection from {}:{}", new String[]{ipAddress, String.valueOf(port)});

                new Thread(() -> {
                    try {

                        for (Player player : Bukkit.getOnlinePlayers()) {
                            InetSocketAddress playerAddress = player.getAddress();

                            if (!socket.getInetAddress().getHostAddress().equals(playerAddress.getAddress().getHostAddress()))
                                continue;
                            if (playerSocketMap.containsKey(player))
                                return;

                            playerSocketMap.put(player, socket);
                            handleConnection(player, socket);
                        }

                        socket.close();
                        LOGGER.log(Level.SEVERE, "Unable to find a player at {}", socket.getInetAddress().getHostAddress());

                    } catch (IOException e) {
                        LOGGER.log(Level.SEVERE, "Unable to handle connection from {}:{}", new String[]{ipAddress, String.valueOf(port)});
                    }
                }, "Connection#" + ipAddress + ":" + port).start();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Unable to accept connection: {}", e);
            }
        }
    }

    private void handleConnection(Player player, Socket socket) {
    }

    public void stop() {
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Unable to close the web socket server", e);
        }
    }

    @Override
    public void run() {

    }

}
