import java.io.*;
import java.net.*;
import java.util.Enumeration;

public class FileServer extends Thread {
    public SttUI ui;
    int j = 0;
    public FileServer() throws IOException {
        int filesize =60223860; // filesize temporary hardcoded
        String path;
        long start = System.currentTimeMillis();
        int bytesRead;
        int current = 0;
        int count = 0;
        Thread Thread1 = null;

        ui = new SttUI(); //apro l'interfaccia utente
        ui.loading();

        // create socket
        ServerSocket servsock = new ServerSocket(4747);

        ui.browse(); //CHIEDO ALL'UTENTE DOVE SALVARE I FILE CHE ARRIVANO

        //PRENDO GLI INDIRIZZO IP E LO MOSTRO NEL BROWSE
        getIP();

        System.out.println("Waiting Connection...");

       do{
            path = ui.location;
            System.out.println(path);
        }while(path==null);

        while (true) {
            System.out.println("Waiting...");
            Socket sock = servsock.accept();
            System.out.println("Accepted connection : " + sock);
            ui.conectionOpen();
            // receive file
            byte [] mybytearray  = new byte [filesize];
            InputStream is = sock.getInputStream();
            //lettura del nome del file
            DataInputStream clientData = new DataInputStream(is);
            String fileName = clientData.readUTF();
            //
            String destination = path + "\\"+fileName;
            FileOutputStream fos = new FileOutputStream(destination); // destination path and name of file
            count++; //conta le foto salvate
            long size = clientData.readLong(); //dimensione del file
            System.out.println("la dimensione del file"+fileName+ "è"+size);

            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray,0,mybytearray.length);
            current = bytesRead;

            ui.progress(); //barra di caricamento
            int i = 0; //indice per barra di caricamento
            do {
                ui.bar.setValue(i);
                bytesRead =
                        is.read(mybytearray, current, (mybytearray.length-current));
                System.out.println(current);
                i++;
                if(bytesRead >= 0) current += bytesRead;
            } while(bytesRead > -1);

            System.out.println("Fine prima foto");
            ui.showImage(destination);
            bos.write(mybytearray, 0 , current);
            bos.flush();
            bos.close();

            long end = System.currentTimeMillis();
            System.out.println(end-start);
            j++;
            ui.numOfFile(j);
        }
    }

    public void getIP() throws SocketException {

        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while(interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            //drop inactive
            if (!networkInterface.isUp())
                continue;
            //smth we can explore
            Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();
                //if(networkInterface.getName().substring(0,4) == "wlan")
                if (networkInterface.getName().length() >= 4) {
                    if (networkInterface.getName().substring(0, 4).equals("wlan")) { //wifi
                        if(!addr.getHostAddress().contains(":")){
                            System.out.println("WI-FI IP = " + addr.getHostAddress());
                            ui.ip1(addr.getHostAddress());
                        }
                    }

                    if (networkInterface.getName().substring(0, 3).equals("eth")) {   //ethernet
                        if(!addr.getHostAddress().contains(":")){
                            System.out.println("Ethernet IP = " + addr.getHostAddress());
                            ui.ip2(addr.getHostAddress());
                        }
                    }
                }
            }
        }
    }
    public static void main(String[] args) throws IOException {
        FileServer start = new FileServer();
    }
}