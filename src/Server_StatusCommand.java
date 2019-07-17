import java.util.Map;

public class Server_StatusCommand implements CommandExecute {

    @Override
    public void execute(HttpRequest httpRequest) {
        System.out.println("Executing Server-Status");
        String SSC_reponse="";
        for(Map.Entry entry:SleepCommand.connecTimeleft.entrySet()){
            SSC_reponse = SSC_reponse.concat("Running Connection ID :"+ SleepCommand.connectionMap_rev.get(entry.getKey())+" || "+ "Running Time Left : "+ entry.getValue()+"\n");
        }
        RequestHandler.ResponseMap.put((int)Thread.currentThread().getId(),SSC_reponse);
    }
}

