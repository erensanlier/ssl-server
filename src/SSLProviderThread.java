import java.io.*;
import java.net.Socket;

public class SSLProviderThread extends Thread {
    private final Socket socket;
    private PrintWriter outputStream;
    private BufferedReader inputStream;

    public SSLProviderThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            outputStream = new PrintWriter(socket.getOutputStream());
            inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean authenticated = false;

        try {
            outputStream.println("Connected to SSL Provider. Enter your credentials to receive the SSL certificate");
            outputStream.flush();
            while (!authenticated){
                String credentials = inputStream.readLine();
                if(!checkCredentials(credentials)){
                    System.err.println("Unsuccessful auth attempt!");
                    outputStream.println("Fail");
                    outputStream.flush();
                }
                else authenticated = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        System.out.println("Successful auth! Starting to send the certificate.");
        outputStream.println("Success");
        outputStream.flush();

        File certificate = new File("server_crt.crt");
        try {
            FileInputStream certificateFileInputStream = new FileInputStream(certificate);
            byte[] bytes = new byte[4096];
            int count;
            while ((count = certificateFileInputStream.read(bytes)) > 0) {
                socket.getOutputStream().write(bytes, 0, count);
            }
            // send EOF character
            socket.getOutputStream().write(26);
            System.out.println("Certificate sent! Good job me.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkCredentials(String credentials) throws Exception {
        boolean auth = false;
        BufferedReader credentialsFileReader = new BufferedReader(new FileReader("user_db.txt"));
        String line = credentialsFileReader.readLine();
        while (line != null) {
            if (line.equals(credentials)) {
                auth = true;
                break;
            }
            line = credentialsFileReader.readLine();
        }
        credentialsFileReader.close();
        return auth;
    }


}
