import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SSLProvider extends Thread {

    private ServerSocket serverSocket;
    private int port;

    public SSLProvider(int port) {
        this.port = port;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("SSL provider service is up and running on port " + this.port);
        while (true) {
            listenAndAccept();
        }
    }

    /**
     * Listens to the line and starts a connection on receiving a request with the client
     */
    private void listenAndAccept() {
        Socket socket;
        try {
            socket = serverSocket.accept();
            System.out.println("A connection was established with a client on the address of " + socket.getRemoteSocketAddress());
            SSLProviderThread sslProviderThread = new SSLProviderThread(socket);
            sslProviderThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
