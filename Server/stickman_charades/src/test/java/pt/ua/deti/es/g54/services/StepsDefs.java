package pt.ua.deti.es.g54.services;


import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import junit.framework.Assert;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.TestPropertySource;
import pt.ua.deti.es.g54.api.entities.UserData;
import pt.ua.deti.es.g54.entities.DBSession;
import pt.ua.deti.es.g54.repository.SessionRepository;

/**
 * Where all the steps of all features are defined here
 * Each step has referenced on javadoc on what Scenario(s) of which Feature(s) it is used
 */
@TestPropertySource (locations={"classpath:application-test.properties"}, properties={"KAFKA_HOST=localhost", "KAFKA_PORT=9092"})
@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StepsDefs {

    private long MAX_WAIT_TIME = 500;

    private static WebDriver driver;
    
    private static int usernameCount = 0;
    private static String currentUsername;
    private static long currentSessionId;
    
    @Value("${KAFKA_HOST}")
    private String KAFKA_HOST;

    @Value("${KAFKA_PORT}")
    private String KAFKA_PORT;
    
    @LocalServerPort
    int randomServerPort;
    
    @Autowired
    private TestRestTemplate restTemplate;  
    
    @Autowired
    private UserService us;
    
    @Autowired
    private KafkaTemplate kt;
    
    @Autowired
    private SessionRepository sr;
    
    private String server="http://localhost:";

    static {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--whitelisted-ips");
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disabled-extensions");

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        
    }
    
    private void changeCurrentUsername() {
        usernameCount++;
        currentUsername = "cucumber_tests" + usernameCount;
    }
    
    @Given("that I am logged in,")
    public void that_I_am_logged_in() throws Exception {
        changeCurrentUsername();
        UserData ud = new UserData();
        ud.setUsername(currentUsername);
        ud.setEmail(currentUsername+"@mail.com");
        ud.setPassword("123".toCharArray());
        us.registerUser(ud);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(currentUsername, "123");
        
        HttpEntity entity = new HttpEntity(headers);
        
        ResponseEntity<String> result = restTemplate.exchange(server+randomServerPort+"/login", HttpMethod.GET, entity, String.class);
        assertEquals(result.getStatusCodeValue(),200);
    }

    @Given("I have access to home lobby page,")
    public void i_have_access_to_home_lobby_page() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    @When("I choose the option to create a game session")
    public void i_choose_the_option_to_create_a_game_session() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    @Then("I should see a form to be filled.")
    public void i_should_see_a_form_to_be_filled() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    @When("I execute the previous steps")
    public void i_execute_the_previous_steps() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    @When("I fill in and submit the form,")
    public void i_fill_in_and_submit_the_form() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    @Then("I should see a message informing me about the success\\/failure of the operation")
    public void i_should_see_a_message_informing_me_about_the_success_failure_of_the_operation() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    @Then("\\(if successful) I should be redirected to the session lobby.")
    public void if_successful_I_should_be_redirected_to_the_session_lobby() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }
    
    @Given("I see the game session I created that I want to join in the game sessions list,")
    public void i_see_the_game_session_I_created_that_I_want_to_join_in_the_game_sessions_list() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    @When("I click the join button of that game session")
    public void i_click_the_join_button_of_that_game_session() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    @Then("I should see the game session lobby")
    public void i_should_see_the_game_session_lobby() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    @Then("I should be the admin.")
    public void i_should_be_the_admin() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    @Given("I see the game session I want in the game session list,")
    public void i_see_the_game_session_I_want_in_the_game_session_list() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    @When("I click the join button of that session,")
    public void i_click_the_join_button_of_that_session() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }
    
    @When("I click the invite user as friend button")
    public void i_click_the_invite_user_as_friend_button() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    @Then("I should see a form that allows the user to insert another user's username.")
    public void i_should_see_a_form_that_allows_the_user_to_insert_another_user_s_username() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    @Given("I'm in the invite user as friend form")
    public void i_m_in_the_invite_user_as_friend_form() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    @When("I click the send notification button,")
    public void i_click_the_send_notification_button() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    @Then("I should be notified that a message with my friendship request was sent.")
    public void i_should_be_notified_that_a_message_with_my_friendship_request_was_sent() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    @When("I'm using the platform and other user accepts my friendship request,")
    public void i_m_using_the_platform_and_other_user_accepts_my_friendship_request() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    @Then("I should be notified that that user has accepted my friendship request.")
    public void i_should_be_notified_that_that_user_has_accepted_my_friendship_request() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    @Given("I am at the game session lobby,")
    public void i_am_at_the_game_session_lobby() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    @Given("I see a friend I want to invite at the friends list,")
    public void i_see_a_friend_I_want_to_invite_at_the_friends_list() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    @When("I click the invite button related to that friend")
    public void i_click_the_invite_button_related_to_that_friend() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    @Then("I should be notified that a message with the invitation was sent to my friend.")
    public void i_should_be_notified_that_a_message_with_the_invitation_was_sent_to_my_friend() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }
    
    @Given("I am in a game session,")
    public void i_am_in_a_game_session() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    @When("I raise my right hand above my head")
    public void i_raise_my_right_hand_above_my_head() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    @Then("I should be notified that a message was send to the admin")
    public void i_should_be_notified_that_a_message_was_send_to_the_admin() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    @Then("short after I should see the admin in my game session.")
    public void short_after_I_should_see_the_admin_in_my_game_session() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    @When("I raise my left hand above my head")
    public void i_raise_my_left_hand_above_my_head() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    @Given("I just joined a game session,")
    public void i_just_joined_a_game_session() throws ParseException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(currentUsername, "123");
        
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("name", "test");
        jsonBody.put("duration", 600);
        
        HttpEntity entity = new HttpEntity(jsonBody, headers);
        
        ResponseEntity<String> result = restTemplate.exchange(server+randomServerPort+"/session", HttpMethod.POST, entity, String.class);
        JSONObject json = (JSONObject)new JSONParser().parse(result.getBody());
        currentSessionId=(long)json.get("id");
        assertEquals(result.getStatusCodeValue(),200);
        
        jsonBody = new JSONObject();

        entity = new HttpEntity(jsonBody, headers);
        
        result = restTemplate.exchange(server+randomServerPort+"/session/"+currentSessionId, HttpMethod.PUT, entity, String.class);
        assertEquals(result.getStatusCodeValue(),200);
    }

    @When("I perform the initial position\\(spread arms)")
    public void i_perform_the_initial_position_spread_arms() throws ParseException {
        String jsonBody = "{\"username\": \""+currentUsername+"\", \"positions\": {\"Head\": [2098.635,1708.936,0.0], \"Neck\": [2227.439,1410.903,0.0], \"LeftCollar\": [2252.219,1217.666,0.0], "
                + "\"Torso\": [2314.787,729.7457,0.0], \"Waist\": [2356.344,278.0999,0.0], \"LeftShoulder\": [1860.994,1193.861,0.0], \"RightShoulder\": [2813.465,1175.847,0.0], "
                + "\"LeftElbow\": [1751.346,713.7789,0.0], \"RightElbow\": [0.0,0.0,0.0], \"LeftWrist\": [0.0,0.0,0.0], \"RightWrist\": [0.0,0.0,0.0], \"LeftHand\": [0.0,1193.861,0.0], "
                + "\"RightHand\": [0.0,1175.847,0.0], \"LeftHip\": [0.0,0.0,0.0], \"RightHip\": [2672.045,226.5393,0.0], \"LeftKnee\": [0.0,0.0,0.0], \"RightKnee\": [0.0,0.0,0.0], "
                + "\"LeftAnkle\": [0.0,0.0,0.0], \"RightAnkle\": [0.0,0.0,0.0]}}";
        kt.send("esp54_"+currentSessionId, jsonBody);
    }

    @Then("I should be recognized by the platform")
    public void i_should_be_recognized_by_the_platform() throws ParseException {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", KAFKA_HOST + ":" + KAFKA_PORT);
        properties.put("group.id", "es_g54_group_test");
        properties.put("auto.offset.reset", "latest");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer consumer = new KafkaConsumer<Integer,String>(properties);
        consumer.subscribe(Arrays.asList("esp54_"+currentSessionId));
        ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofMillis(100));
        assertEquals(records.count(), 1);
        for (ConsumerRecord<Integer,String> record : records){
            JSONObject json = (JSONObject) new JSONParser().parse(record.value());
            assertEquals(json.get("username"), currentUsername);
            assertEquals(json.get("msg"), "User recognized.");
        }
    }

    @Then("if am the last user recognized, the game session should start.")
    public void if_am_the_last_user_recognized_the_game_session_should_start() throws InterruptedException {
        Thread.sleep(3000);
        List<DBSession> sessions = sr.getSessionById(currentSessionId);
        assertEquals(sessions.size(), 1);
        DBSession session = sessions.get(0);
        assertEquals(session.getIsActive(), true);
    }

    @When("I perform the stopping position\\(cross arms over head)")
    public void i_perform_the_stopping_position_cross_arms_over_head() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

    @Then("I should see the game session to be immediately stopped.")
    public void i_should_see_the_game_session_to_be_immediately_stopped() {
        // Write code here that turns the phrase above into concrete actions
//        throw new cucumber.api.PendingException();
    }

}