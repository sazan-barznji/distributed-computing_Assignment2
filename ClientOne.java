import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.data.*; //Stat
import java.io.*;

public class ClientOne {

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);

        final CountDownLatch connectedSignal = new CountDownLatch(1);
        ZooKeeper zoo = new ZooKeeper("127.0.0.1:2181", 500, null);

        System.out.println("Enter a Message: ");
        String message = input.nextLine();

        byte[] convertedMessage = toBytes(message);

        String path = "/Clients/ClientOne";
        String masterPath = "/Clients";
        Stat st = zoo.exists(path, false);

        Watcher w = new Watcher(){

            @Override
            public void process(WatchedEvent event) {
                System.out.println("Something Modified");
                try {
                    zoo.getChildren(masterPath, this, null);
                } catch (KeeperException | InterruptedException e) {
                    
                    e.printStackTrace();
                }
            } 
        };

        
        

        if (st != null) {
            
            for (String s : zoo.getChildren(masterPath, null)) {
                if (s.equals(path)) {
                    continue;
                } else {
                    try {
                        byte[] bn = zoo.getData(s, w, null);
                        String data = new String(bn, "UTF-8");
                        System.out.println(data);
                        connectedSignal.countDown();

                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }

                }
            }

            // try {
            // byte[] bn = zoo.getData(path, false, null);
            // String data = new String(bn, "UTF-8");
            // System.out.println(data);
            // connectedSignal.countDown();

            // } catch (Exception ex) {
            // System.out.println(ex.getMessage());
            // }
        } else {
            // Creating the node with a unique name
            System.out.println("ZNode does not exist");

            if (zoo.exists("/Clients/ClientOne", false) == null) {
                zoo.create("/Clients/ClientOne", convertedMessage, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            } else {

                zoo.setData("/Clients/ClientOne", convertedMessage, st.getVersion());

            }

        }
    }

    private static byte[] toBytes(final String i) throws IOException {
        return i.getBytes();
    }

}
