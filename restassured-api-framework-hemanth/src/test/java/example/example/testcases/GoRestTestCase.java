package example.example.testcases;

import com.github.javafaker.Faker;
import example.example.API.GoRestAPIs;
import example.example.setUp.TestSetup;
import example.example.util.Data;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.Hashtable;
import java.util.List;
import java.util.Random;


public class GoRestTestCase extends TestSetup {

    @Test()
    public void getUsersTest(ITestContext context) {
        Response response = GoRestAPIs.getUser();

        testLevelLog.get().info("Response : " + response.asString());
        testLevelLog.get().info("Response status code -" + response.getStatusCode());

        List<String> users = response.jsonPath().getList("name");
        Assert.assertTrue(users.size() == 10);
        Assert.assertTrue(response.getHeader("Content-Type").equals("application/json; charset=utf-8"));
        Assert.assertTrue(response.getHeader("x-pagination-limit").equals("10"));
        Assert.assertEquals(response.statusCode(), 200);
        context.setAttribute("userID", response.jsonPath().get("[0].id"));
        context.setAttribute("userName", response.jsonPath().get("[0].name"));
    }

    @Test(dependsOnMethods = "getUsersTest")
    public void getSpecificUserTest(ITestContext context) {

        int id = (int) context.getAttribute("userID");
        Response response = GoRestAPIs.getSpecificUser(id);

        testLevelLog.get().info("Response : " + response.asString());
        testLevelLog.get().info("Response status code -" + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().get("name"), context.getAttribute("userName").toString());
        testLevelLog.get().info(context.getAttribute("userName").toString() + " matched in response");
    }

    @Test()
    public void createNewUserTest(ITestContext context) {
        Faker faker = new Faker();

        final String[] genderOpt = {"male","female"};
        final String[] status = {"active","inactive"};
        Random random = new Random();
        int index = random.nextInt(genderOpt.length);

        final String name = faker.name().fullName();
        final String gender = genderOpt[index];
        final String email =faker.internet().emailAddress();
        final String status1 = status[index];

        Response response = GoRestAPIs.createNewUser(name,gender,email ,status1);

        context.setAttribute("newUserID",response.jsonPath().get("id"));

        testLevelLog.get().info("Response : " + response.asString());
        testLevelLog.get().info("Response status code -" + response.getStatusCode());
        Assert.assertFalse(response.jsonPath().get("id").toString().isEmpty());
        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertEquals(response.jsonPath().get("name"), name);
        testLevelLog.get().info(name + " matched in response");
    }

    @Test(dependsOnMethods = "createNewUserTest")
    public void updateNewlyCreatedUser(ITestContext context) {
        int newUserID = Integer.parseInt(context.getAttribute("newUserID").toString());

        Faker faker = new Faker();

        final String[] genderOpt = {"male","female"};
        final String[] status = {"active","inactive"};
        Random random = new Random();
        int index = random.nextInt(genderOpt.length);

        final String name = faker.name().fullName();
        final String gender = genderOpt[index];
        final String email =faker.internet().emailAddress();
        final String status1 = status[index];

        Response response = GoRestAPIs.updateNewlyCreatedUser(newUserID,name,gender,email ,status1);

        testLevelLog.get().info("Response : " + response.asString());
        testLevelLog.get().info("Response status code -" + response.getStatusCode());
        Assert.assertFalse(response.jsonPath().get("id").toString().isEmpty());
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().get("name"), name);
        testLevelLog.get().info(name + " matched in response");

    }

    @Test(dependsOnMethods = "updateNewlyCreatedUser")
    public void deleteNewUser(ITestContext context) {
        int newUserID = Integer.parseInt(context.getAttribute("newUserID").toString());

        Response response = GoRestAPIs.deleteNewUser(newUserID);

        testLevelLog.get().info("Response : " + response.asString());
        testLevelLog.get().info("Response status code -" + response.getStatusCode());
        Assert.assertEquals(response.getStatusCode(), 204);
        try {
            Assert.assertFalse(response.getBody().toString().isEmpty());
        } catch (AssertionError assertionError) {
            testLevelLog.get().error(assertionError);
            throw new AssertionError();
        }
        testLevelLog.get().info( "User with userID - "+newUserID+" Deleted successfully");
    }

    @Test(dataProvider="data",dataProviderClass= Data.class)
    public void createNewUserWithData(Hashtable<String, String> data) {

        final String name = data.get("Name");
        final String gender = data.get("Gender");
        final String email = data.get("Email");
        final String status = data.get("Status");

        Response response = GoRestAPIs.createNewUser(name,gender,email ,status);

        testLevelLog.get().info("Response : " + response.asString());
        testLevelLog.get().info("Response status code -" + response.getStatusCode());
        try {
            Assert.assertFalse(response.jsonPath().get("id").toString().isEmpty());
            Assert.assertEquals(response.getStatusCode(), 201);
            Assert.assertEquals(response.jsonPath().get("name"), name);
        } catch (AssertionError assertionError) {
            testLevelLog.get().error(assertionError);
            throw new AssertionError();
        }
        testLevelLog.get().info(name + " matched in response");
    }
}
