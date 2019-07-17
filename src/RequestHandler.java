import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import static jdk.nashorn.internal.objects.NativeString.substring;

public class RequestHandler extends Thread {
    public Socket socket;
    private final static String httpReponseFormat = "HTTP/1.1 200 OK\r\n\r\n";
    private static final String lock ="lock";
    public static Map<Integer,String>  ResponseMap = new HashMap<>();
    private static CommandHandler commandHandler;
    RequestHandler(Socket socket){
        this.socket = socket;
    }
    private HttpRequest parseRequest(String request) throws Exception{
        System.out.println("Parsing Request");
        HttpRequest httpRequest ;
        String method="",command="";
        Boolean requestValidFlag = Boolean.TRUE;
        Map<String,String> paramMap = new HashMap<>();
        try {
            String requestComponents[] = request.split(" ");
            method = requestComponents[0];
            System.out.println("Method ::" + method);
            String query = requestComponents[1];
            int queryLen = query.length();
            query = query.substring(1, queryLen);
            String params[] = query.split("\\|\\s|/|\\?|\\&", 0);
            String queryCommand = params[0];
            command = queryCommand;
            System.out.println("Command ::" + command);
            switch (queryCommand) {
                case "sleep": {
                    String param_1[] = params[1].split("=");
                    String param_2[] = params[2].split("=");
                    paramMap.put(param_1[0], param_1[1]);
                    paramMap.put(param_2[0], param_2[1]);
                }
                break;
                case "server-status": {
                    if (params.length > 1) {
                        throw new Exception();
                    }
                }
                break;
                case "kill": {
                    String param_1[] = params[1].split("=");
                    paramMap.put(param_1[0], param_1[1]);
                }
                break;
                default: {
                    throw new Exception();
                }
            }
        }
        catch(Exception e){
            requestValidFlag = Boolean.FALSE;
            System.out.println("InValid Request Type");
        }
        httpRequest = new HttpRequest(method,command,paramMap,requestValidFlag);
        return httpRequest;
    }

    public void run(){
        try{
            System.out.println("Running Thread :: "+ Thread.currentThread().getId());
            InputStreamReader isr=  new InputStreamReader(socket.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String request = br.readLine();
            HttpRequest httpRequest = parseRequest(request);
            commandHandler= new CommandHandler(httpRequest);
            commandHandler.HandleCommand();
            synchronized (lock) {
                String httpResponse = httpReponseFormat + ResponseMap.get((int) Thread.currentThread().getId());
                ResponseMap.remove((int) Thread.currentThread().getId());
                socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                socket.getOutputStream().close();
                socket.close();
                System.out.println("Closing Socket..."+"Closing Thread with Thread id "+Thread.currentThread().getId());
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }

}
