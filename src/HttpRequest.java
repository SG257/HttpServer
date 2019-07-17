import java.util.Map;

public class HttpRequest {
    private String requestMethod;
    private String requestCommand;
    private Map<String,String> requestParams;
    private Boolean requestValidityFlag;

    public HttpRequest(String requestMethod,String requestCommand,Map<String,
            String> requestParams,Boolean requestValidityFlag){
        this.requestMethod=requestMethod;
        this.requestCommand=requestCommand;
        this.requestParams=requestParams;
        this.requestValidityFlag=requestValidityFlag;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestCommand() {
        return requestCommand;
    }

    public void setRequestCommand(String requestCommand) {
        this.requestCommand = requestCommand;
    }

    public Map<String, String> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(Map<String, String> requestParams) {
        this.requestParams = requestParams;
    }
}
