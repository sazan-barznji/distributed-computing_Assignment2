
import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.data.*; //Stat
import java.io.*;


public class WorkedClient {

    public static void main(String[] args) throws Exception {
        final CountDownLatch connectedSignal = new CountDownLatch(1);
        ZooKeeper zoo = new ZooKeeper("127.0.0.1:2181", 5000, null);


        //store data
        byte[] arr = toBytes("hello world");
        zoo.create("/node1",arr,ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);

        //get data
        byte[] bs = zoo.getData("/node1",false,null);
        String s = new String(bs);
        System.out.println(s);
        connectedSignal.await();


    }


    private static byte[] toBytes(final String i) throws IOException {
		return i.getBytes();
	}
    
}
