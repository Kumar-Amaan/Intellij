package resources;

import java.io.*;

public class TestDataBuild extends  Utils{
    String expMessage="I had updated the comment";



public String getAuthenticateJson() throws IOException {
    String uName=getGlobalValue("username");
    String pass=getGlobalValue("password");
    return "{\n" +
            "    \"username\":\""+uName+"\",\"password\":\""+pass+"\"\n" +
            "}\n" +
            " \n";
}
    public String createIssueJson(){
        return "{\n" +
                "    \"fields\": {\n" +
                "        \"project\": {\n" +
                "            \"key\": \"DEM\"\n" +
                "        },\n" +
                "        \"summary\": \"Raised Defect\",\n" +
                "        \"description\":\"Creating a defect\",\n" +
                "        \"issuetype\": {\n" +
                "            \"name\": \"Bug\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
    }
    public String addComment(){
    return "{\n" +
            "    \"body\": \"Comment from Intellij\",\n" +
            "    \"visiblity\": {\n" +
            "        \"type\":\"role\",\n" +
            "        \"value\":\"Senior\"\n" +
            "    }\n" +
            "}";
    }
    public String updateCommentJson(){
    return "{\n" +
            "    \"body\": \""+expMessage+"\",\n" +
            "    \"visiblity\": {\n" +
            "        \"type\":\"role\",\n" +
            "        \"value\":\"Tester\"\n" +
            "    }\n" +
            "}";
    }

}
