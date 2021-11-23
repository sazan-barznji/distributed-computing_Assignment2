import java.util.concurrent.CountDownLatch;
// import zookeeper classes
import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.data.*; //Stat
import java.io.*;

public class client{
    public static byte[] toBytes (final String i) throws IOException{
        return i.getBytes();
    }
   public static void main(String[] args) throws Exception{
       Zookeeper zoo= new  Zookeeper("ipaddress",500,null);//input!! ip adress:portnumber, null== watcher
        //create znode 
        byte[] arr= toBytes("message");
        zoo.create("/clients",null,ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);//null is the data
        zoo.create("/clients/node1",arr,ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);//null is the data

        connectedSignal.await();
   }
}