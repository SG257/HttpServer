public class KillCommand implements CommandExecute{
    private static final String killSuccessResponse="{\"stat\": killed}";
    @Override
    public void execute(HttpRequest httpRequest) throws Exception {

        System.out.println("Executing Kill Command");
        int connid = Integer.parseInt(httpRequest.getRequestParams().get("connid"));
        if(SleepCommand.connectionMap.containsKey(connid)) {
            int thread_id = SleepCommand.connectionMap.get(connid);
            SleepCommand.connecTimeleft.put(thread_id,0);
            RequestHandler.ResponseMap.put((int)Thread.currentThread().getId(),killSuccessResponse);
        }
        else
            throw new Exception("Connection Already Closed");

    }
}
