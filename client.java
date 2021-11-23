import java.util.concurrent.CountDownLatch;
// import zookeeper classes
import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.data.*; //Stat
import java.io.*;

public class client{
   public static void main(String[] args) throws Exception{
       Zookeeper zoo= new  Zookeeper("ipaddress",500,null);//input!! ip adress:portnumber, null== watcher

   }
}