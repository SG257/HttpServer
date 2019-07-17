import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SleepCommand implements CommandExecute {
    public static Map<Integer,Integer> connectionMap = new HashMap<>(); //<Conn-ID,ThreadID>
    public static Map<Integer,Integer> connectionMap_rev = new HashMap<>(); //<ThreadID,Conn-ID>
    public static Map<Integer,Integer> connecTimeleft = new HashMap<>();//<Thread-ID,TimeLeft>
    public static Set<Integer> Connections = new HashSet<>();
    private static String sleepCommandSuccessReponse="{\"stat\": ok}";
    private final static String lock = "lock";

    private void UpdateTimeLeft(Map<Integer,Integer> timeleftMap){
        for(Map.Entry<Integer,Integer> entry: timeleftMap.entrySet()){
            entry.setValue(entry.getValue()-1);
        }
    }

    @Override
    public void execute(HttpRequest httpRequest) throws Exception {
        System.out.println("Executing Sleep Command...");
        synchronized (lock) {
            int connid = Integer.parseInt(httpRequest.getRequestParams().get("connid"));
            int timeleft = Integer.parseInt(httpRequest.getRequestParams().get("timeout"));
            if (!Connections.contains(connid)){
                Connections.add(connid);
                connectionMap_rev.put((int) Thread.currentThread().getId(),connid);
                connectionMap.put(connid, (int) Thread.currentThread().getId());
                connecTimeleft.put( (int) Thread.currentThread().getId(),timeleft);
            }
            else throw new Exception("Already Running Thread with connid: " + connid);
        }
        while(connecTimeleft.get((int)Thread.currentThread().getId())>0){
            UpdateTimeLeft(connecTimeleft);
            Thread.sleep(1*1000);
        }
        synchronized (lock){
            int thread_id = (int)Thread.currentThread().getId();
            RequestHandler.ResponseMap.put(thread_id,sleepCommandSuccessReponse);
            int th_connid = connectionMap_rev.get(thread_id);
            Connections.remove(th_connid);
            connectionMap.remove(th_connid);
            connecTimeleft.remove(thread_id);
        }
    }
}
