package example.example.API;

import example.example.POJO.UserPOJO;
import example.example.constants.Constants;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.response.Response;

import java.io.PrintStream;
import java.io.StringWriter;
import java.util.Random;

import org.apache.commons.io.output.WriterOutputStream;

import static example.example.setUp.TestSetup.testLevelLog;
import static io.restassured.RestAssured.given;

public class GoRestAPIs {
    static StringWriter requestWriter;
    static PrintStream requestCapture;
    public static Response getUser() {
        requestWriter = new StringWriter();
        requestCapture = new PrintStream(new WriterOutputStream(requestWriter));

        Response response = given().filter(new RequestLoggingFilter(requestCapture))
                .get(Constants.USERS_ENDPOINT);

        requestCapture.flush();
        testLevelLog.get().info("Request : "+ requestWriter.toString());
        return response;
    }

    public static Response getSpecificUser(int userID){
        requestWriter = new StringWriter();
        requestCapture = new PrintStream(new WriterOutputStream(requestWriter));
        Response response = given().filter(new RequestLoggingFilter(requestCapture))
                .get(Constants.USERS_ENDPOINT+"/"+userID);

        requestCapture.flush();
        testLevelLog.get().info("Request : "+ requestWriter.toString());
        return response;
    }

    public static Response createNewUser(String name, String gender, String email, String status) {

        requestWriter = new StringWriter();
        requestCapture = new PrintStream(new WriterOutputStream(requestWriter));
        UserPOJO user = new UserPOJO();
        user.setName(name);
        user.setEmail(email);
        user.setGender(gender);
        user.setStatus(status);

        Response response = given().filter(new RequestLoggingFilter(requestCapture))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 05334b37498beb44a99cb4920b8aeba743afebba6d4269fa32ea879e5efffb33")
                        .body(user)
                .post(Constants.USERS_ENDPOINT);

        requestCapture.flush();
        testLevelLog.get().info("Request : "+ requestWriter.toString());
        return response;
    }

    public static Response updateNewlyCreatedUser(int userID, String name, String gender, String email, String status) {

        requestWriter = new StringWriter();
        requestCapture = new PrintStream(new WriterOutputStream(requestWriter));
        UserPOJO user = new UserPOJO();
        user.setName(name);
        user.setEmail(email);
        user.setGender(gender);
        user.setStatus(status);

        Response response = given().filter(new RequestLoggingFilter(requestCapture))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 05334b37498beb44a99cb4920b8aeba743afebba6d4269fa32ea879e5efffb33")
                .body(user)
                .patch(Constants.USERS_ENDPOINT+"/"+userID);

        requestCapture.flush();
        testLevelLog.get().info("Request : "+ requestWriter.toString());
        return response;
    }

    public static Response deleteNewUser(int userID) {
        requestWriter = new StringWriter();
        requestCapture = new PrintStream(new WriterOutputStream(requestWriter));

        Response response = given().filter(new RequestLoggingFilter(requestCapture))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 05334b37498beb44a99cb4920b8aeba743afebba6d4269fa32ea879e5efffb33")
                        .delete(Constants.USERS_ENDPOINT+"/"+userID);

        requestCapture.flush();
        testLevelLog.get().info("Request : "+ requestWriter.toString());
        return response;
    }

}
