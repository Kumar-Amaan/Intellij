package stepDefinition;

import io.cucumber.java.en.*;
import io.restassured.*;
import io.restassured.filter.session.*;
import io.restassured.path.json.*;
import org.junit.*;
import resources.*;

import java.io.*;

import static io.restassured.RestAssured.*;

public class Jira_stepDefinition extends Utils {
    int getId;
    String auth;
    String createIssue;
    String getCommentId;
    String addComment;
    String getIssue;
//    String updateComment;
    String expMessage="I had updated the comment";
    int countComments;
    SessionFilter session = new SessionFilter();
    TestDataBuild data=new TestDataBuild();

    @Given("User logged into Jira and hit the Post httpMethod and get name and value from response")
    public void user_logged_into_jira_and_hit_the_post_http_method_and_get_name_and_value_from_response() throws IOException {
        RestAssured.baseURI = getGlobalValue("baseUrl");
        // Write code here that turns the phrase above into concrete actions
        auth = given().header("Content-Type", "application/json").body(data.getAuthenticateJson())
                .log().all().filter(session)
                .when().log().all().post(getGlobalValue("authorizeAPI"))
                .then().assertThat().statusCode(200)
                .extract().response().asString();
        JsonPath js = new JsonPath(auth);
        String name = js.get("name");
        System.out.println(name);
        String value = js.get("value");
        System.out.println(value);
        String cookie = "" + name + "=" + value + "";
        System.out.println(cookie);

    }

    @When("User create issue with Post httpMethod and get issueId")
    public void user_create_issue_with_post_http_method_and_get_issue_id() throws IOException {

        createIssue = given().header("Content-Type", "application/json").header("Cookie", "cookie")
                .body(data.createIssueJson()).log().all().when().filter(session).post(getGlobalValue("createIssueAPI"))
                .then().log().all().assertThat().statusCode(201).extract().response().asString();

        JsonPath js = new JsonPath(createIssue);
        getId = js.getInt("id");
        System.out.println(getId);

    }

    @When("User add comment to the issue")
    public void user_add_comment_to_the_issue() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        addComment = given().pathParams("i_d", getId).header("Content-Type", "application/json")
                .body(data.addComment())
                .filter(session).when().post(getGlobalValue("addCommentAPI"))
                .then().log().all().assertThat().statusCode(201).extract().response().asString();
        JsonPath js = new JsonPath(addComment);
        getCommentId = js.get("id");
        System.out.println(getCommentId);

    }

    @Then("User attach file to the issue")
    public void user_attach_file_to_the_issue() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        given().pathParams("i_d", getId).header("X-Atlassian-Token", "no-check")
                .header("Content-Type", "multipart/form-data").filter(session)
                .multiPart("file", new File("jira.txt")).log().all().when()
                .post(getGlobalValue("attachFileAPI")).then().log().all().assertThat().statusCode(200);
    }

    @Then("Verify the comment")
    public void verify_the_comment() throws IOException {

        // Write code here that turns the phrase above into concrete actions
        String getIssue = given().filter(session).pathParam("i_d", getId).log().all().get(getGlobalValue("getIssueAPI"))
                .then().log().all().extract().response().asString();
        JsonPath js = rawToJson(getIssue);
//verify the comment
        // this is for counting the no of comments
        countComments = js.getInt("fields.comment.comments.size()");
        for (int i = 0; i < countComments; i++) {
            String commentIdCount = js.get("fields.comment.comments[" + i + "]").toString();
            if (commentIdCount.equalsIgnoreCase(getCommentId)) {
                String message = js.get("fields.comment.comments[" + i + "].body");
                System.out.println(message);
                Assert.assertEquals(expMessage, message);
            }
        }

    }

    @Then("User update the comment")
    public void user_update_the_comment() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        String updateComment = given().filter(session).pathParam("i_d", getId).pathParam("comment_id", getCommentId)
                .header("Content-Type", "application/json").body(data.updateCommentJson()).filter(session).when().log()
                .all().put(getGlobalValue("updateCommentAPI")).then().log().all().assertThat()
                .statusCode(200).extract().response().asString();

        JsonPath js = new JsonPath(updateComment);
        String dateCreated = js.get("created");
        String dateUpdated = js.get("updated");
        System.out.println(dateCreated);
        System.out.println(dateUpdated);
    }


}
