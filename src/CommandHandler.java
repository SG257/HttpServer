public class CommandHandler {
    private HttpRequest httpRequest;
    CommandHandler(HttpRequest httpRequest){
        this.httpRequest = httpRequest;
    }
    public void HandleCommand() throws Exception {
        CommandExecute commandExecute;

        switch (httpRequest.getRequestMethod()){
            case "GET":{
                switch(httpRequest.getRequestCommand()){
                    case "sleep":{
                        commandExecute = new SleepCommand();
                    }
                    break;
                    case "server-status":{
                        commandExecute = new Server_StatusCommand();
                    }
                    break;
                    default:{
                        throw new Exception();
                    }
                }
            }
            break;
            case "POST": {
                commandExecute = new KillCommand();
            }
            break;
            default: {
                throw new Exception();
            }
        }
        commandExecute.execute(httpRequest);

    }

}
