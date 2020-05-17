package pt.ua.deti.es.g54.services;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

/**
 * Where all the steps of all features are defined here
 * Each step has referenced on javadoc on what Scenario(s) of which Feature(s) it is used
 */
@TestPropertySource (locations={"classpath:application-test.properties"})
@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StepsDefs {
    
    
//    @Autowired
//    private EmbeddedKafkaBroker embeddedKafkaBroker;

    private long MAX_WAIT_TIME = 500;

    private static int usernameCount = 0;
    private static String currentUsername;
    private static String currentFriendname;
    private static long currentSessionId;

    

//    @Before
//    public void before() {
//        System.setProperty("KAFKA_BOOTSTRAP_SERVERS", embeddedKafkaBroker.getBrokersAsString());
//    }

    @LocalServerPort
    int randomServerPort;
    
    @Autowired
    private KafkaTemplate<String, String> kt;
    
    private String server="http://localhost:";

    //private static WebDriver driver;

    //static {
    //    ChromeOptions options = new ChromeOptions();
    //    options.addArguments("--whitelisted-ips");
    //    options.addArguments("--headless");
    //    options.addArguments("--no-sandbox");
    //    options.addArguments("--disabled-extensions");

    //    WebDriverManager.chromedriver().setup();
    //    driver = new ChromeDriver(options);
    //}
    
    private KafkaConsumer consumer;
    private WebSocketStompClient stompClient;
    BlockingQueue<String> blockingQueue;

    @Value("${KAFKA_BOOTSTRAP_SERVERS}")
    private String KAFKA_BOOTSTRAP_SERVERS;

    @Before
    public void stetUp() throws InterruptedException, ExecutionException{
        Properties properties = new Properties();
        properties.put("bootstrap.servers", KAFKA_BOOTSTRAP_SERVERS);
        properties.put("group.id", "es_g54_group_test");
        properties.put("auto.offset.reset", "latest");
        properties.put("auto.commit.enable", "false");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<String,String>(properties);
        
        //stompClient = new WebSocketStompClient(new SockJsClient(Arrays.asList(new WebSocketTransport(new StandardWebSocketClient()))));
        //WebSocketHttpHeaders handshakeHeaders = new WebSocketHttpHeaders();
        //handshakeHeaders.add("Authorization", "Basic am9hbzoxMjM0NTY3OA==");
        //StompSession stompSession = stompClient.connect("ws://localhost:"+randomServerPort+"/game/skeletons",handshakeHeaders, new StompHeaders(), new DefaultStompSessionHandler()).get();
    
        /*
        stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        
        WebSocketHttpHeaders handshakeHeaders = new WebSocketHttpHeaders();
        handshakeHeaders.add("Authorization", "Basic am9hbzoxMjM0NTY3OA==");
        
        StompHeaders connectHeaders = new StompHeaders();
        //connectHeaders.add("login", "test1");
        //connectHeaders.add("passcode", "test");
        
        //stompClient.connect("ws://localhost:"+randomServerPort+"/game/skeletons/game/admin", new DefaultStompSessionHandler());
        StompSession stompSession = stompClient.connect("ws://localhost:"+randomServerPort+"/game/skeletons", handshakeHeaders, connectHeaders, new DefaultStompSessionHandler()).get();
        stompSession.subscribe("/game/admin", new StompFrameHandler() {

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                // ...
                JSONObject json = (JSONObject) payload;//new JSONParser().parse(o.value());
                System.out.println("\n\n\n\n\n\n\n\n\nHERE\n\n\n\n\n\n\n\n\n\n" + json);
            }

        });
        
        //new Scanner(System.in).nextLine();
        */

    }
    
    private void changeCurrentUsername() {
        usernameCount++;
        currentUsername = "cucumber_tests" + usernameCount;
    }
    
    @Given("that I am logged in,")
    public void that_I_am_logged_in() throws Exception {
        changeCurrentUsername();
//        UserData ud = new UserData();
//        ud.setUsername(currentUsername);
//        ud.setEmail(currentUsername+"@mail.com");
//        ud.setPassword("123".toCharArray());
//        us.registerUser(ud);
//        
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBasicAuth(currentUsername, "123");
//        
//        HttpEntity entity = new HttpEntity(headers);
//        
//        ResponseEntity<String> result = restTemplate.exchange(server+randomServerPort+"/login", HttpMethod.GET, entity, String.class);
//        assertEquals(result.getStatusCodeValue(),200);
    }

    @When("I choose the option to create a game session")
    public void i_choose_the_option_to_create_a_game_session() {
        //nothing to do; only validate UI
    }

    @Then("I should see a form to be filled.")
    public void i_should_see_a_form_to_be_filled() {
        //nothing to do; only validate UI
    }

    @When("I execute the previous steps")
    public void i_execute_the_previous_steps() {
        //nothing to do; only validate UI
    }

    @When("I fill in and submit the form,")
    public void i_fill_in_and_submit_the_form() throws ParseException {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBasicAuth(currentUsername, "123");
//        
//        JSONObject jsonBody = new JSONObject();
//        jsonBody.put("name", "test");
//        jsonBody.put("duration", 600);
//        
//        HttpEntity entity = new HttpEntity(jsonBody, headers);
//        
//        ResponseEntity<String> result = restTemplate.exchange(server+randomServerPort+"/session", HttpMethod.POST, entity, String.class);
//        JSONObject json = (JSONObject)new JSONParser().parse(result.getBody());
//        currentSessionId=(long)json.get("id");
//        assertEquals(result.getStatusCodeValue(),200);
    }

    @Then("I should see a message informing me about the success\\/failure of the operation")
    public void i_should_see_a_message_informing_me_about_the_success_failure_of_the_operation() {
        //nothing to do; only validate UI
    }

    @Then("\\(if successful) I should be redirected to the session lobby.")
    public void if_successful_I_should_be_redirected_to_the_session_lobby() {
        //nothing to do; only validate UI
    }

    @Then("I should see the game session lobby")
    public void i_should_see_the_game_session_lobby() {
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {}
//        List<DBSession> sessions = sr.getSessionById(currentSessionId);
//        assertEquals(sessions.size(), 1);
//        DBSession session = sessions.get(0);
//        assertEquals(session.getIsAvailable(), true);
//        List<DBUser> users = ur.getUserByUsername(currentUsername);
//        assertEquals(users.size(),1);
//        DBUser user = users.get(0);
//        assertThat(session.getPlayers().contains(user));
    }

    @Then("I should be the admin.")
    public void i_should_be_the_admin() {
//        List<DBSession> sessions = sr.getSessionById(currentSessionId);
//        assertEquals(sessions.size(), 1);
//        DBSession session = sessions.get(0);
//        assertEquals(session.getIsAvailable(), true);
//        List<DBUser> users = ur.getUserByUsername(currentUsername);
//        assertEquals(users.size(),1);
//        DBUser user = users.get(0);
//        assertThat(session.getCreator().equals(user));
    }

    @Given("I see the game session I want in the game session list,")
    public void i_see_the_game_session_I_want_in_the_game_session_list() throws ParseException {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBasicAuth(currentUsername, "123");
//        
//        JSONObject jsonBody = new JSONObject();
//        jsonBody.put("name", "test");
//        jsonBody.put("duration", 600);
//        
//        HttpEntity entity = new HttpEntity(jsonBody, headers);
//        
//        ResponseEntity<String> result = restTemplate.exchange(server+randomServerPort+"/session", HttpMethod.POST, entity, String.class);
//        JSONObject json = (JSONObject)new JSONParser().parse(result.getBody());
//        currentSessionId=(long)json.get("id");
//        assertEquals(result.getStatusCodeValue(),200);
//        
//        result = restTemplate.exchange(server+randomServerPort+"/session", HttpMethod.GET, entity, String.class);
//        json = (JSONObject)new JSONParser().parse(result.getBody());
//        assertThat(((JSONObject)json.get("sessions")).containsKey(String.valueOf(currentSessionId)));
    }

    @When("I click the join button of that session,")
    public void i_click_the_join_button_of_that_session() throws ParseException {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBasicAuth(currentUsername, "123");
//        
//        JSONObject jsonBody = new JSONObject();
//        jsonBody.put("action", "join");
//        
//        HttpEntity entity = new HttpEntity(jsonBody, headers);
//        
//        ResponseEntity<String> result = restTemplate.exchange(server+randomServerPort+"/session/"+currentSessionId, HttpMethod.POST, entity, String.class);
//        JSONObject json = (JSONObject)new JSONParser().parse(result.getBody());
//        assertEquals(result.getStatusCodeValue(),200);
    }
    
    @When("I click the invite user as friend button")
    public void i_click_the_invite_user_as_friend_button() {
        //nothing to do; only validate UI
    }

    @Then("I should see a form that allows the user to insert another user's username.")
    public void i_should_see_a_form_that_allows_the_user_to_insert_another_user_s_username() {
        //nothing to do; only validate UI
    }

    @Given("I'm in the invite user as friend form")
    public void i_m_in_the_invite_user_as_friend_form() {
        //nothing to do; only validate UI
    }

    @When("I click the send notification button,")
    public void i_click_the_send_notification_button() throws ParseException {
//        currentFriendname=currentUsername+"friend";
//        UserData ud = new UserData();
//        ud.setUsername(currentFriendname);
//        ud.setEmail(currentFriendname+"@mail.com");
//        ud.setPassword("123".toCharArray());
//        us.registerUser(ud);
//        
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBasicAuth(currentUsername, "123");
//        
//        HttpEntity entity = new HttpEntity(headers);
//        
//        ResponseEntity<String> result = restTemplate.exchange(server+randomServerPort+"/friends/"+currentFriendname, HttpMethod.GET, entity, String.class);
//        JSONObject json = (JSONObject)new JSONParser().parse(result.getBody());
//        assertEquals(result.getStatusCodeValue(),200);
    }

    @Then("I should be notified that a message with my friendship request was sent.")
    public void i_should_be_notified_that_a_message_with_my_friendship_request_was_sent() {
        //nothing to do; only validate UI
    }

    @When("I'm using the platform and other user accepts my friendship request,")
    public void i_m_using_the_platform_and_other_user_accepts_my_friendship_request() throws ParseException {
//        currentFriendname=currentUsername+"friend";
//        UserData ud = new UserData();
//        ud.setUsername(currentFriendname);
//        ud.setEmail(currentFriendname+"@mail.com");
//        ud.setPassword("123".toCharArray());
//        us.registerUser(ud);
//        
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBasicAuth(currentUsername, "123");
//        
//        HttpEntity entity = new HttpEntity(headers);
//        
//        ResponseEntity<String> result = restTemplate.exchange(server+randomServerPort+"/friends/"+currentFriendname, HttpMethod.GET, entity, String.class);
//        JSONObject json = (JSONObject)new JSONParser().parse(result.getBody());
//        assertEquals(result.getStatusCodeValue(),200);
//        
//        headers = new HttpHeaders();
//        headers.setBasicAuth(currentFriendname, "123");
//        
//        entity = new HttpEntity(headers);
//        
//        result = restTemplate.exchange(server+randomServerPort+"/friends/"+currentUsername, HttpMethod.GET, entity, String.class);
//        json = (JSONObject)new JSONParser().parse(result.getBody());
//        assertEquals(result.getStatusCodeValue(),200);
    }

    @Then("I should be notified that that user has accepted my friendship request.")
    public void i_should_be_notified_that_that_user_has_accepted_my_friendship_request() throws ParseException {
//        Properties properties = new Properties();
//        properties.put("bootstrap.servers", System.getProperty("KAFKA_BOOTSTRAP_SERVERS"));
//        properties.put("group.id", "es_g54_group_test"+currentUsername);
//        properties.put("auto.offset.reset", "latest");
//        properties.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
//        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        KafkaConsumer consumer = new KafkaConsumer<Integer,String>(properties);
//        consumer.subscribe(Arrays.asList(currentUsername));
//        
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {}
//
//        ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofMillis(100));
//        for (ConsumerRecord<Integer,String> record : records){
//            JSONObject json = (JSONObject) new JSONParser().parse(record.value());
//            assertThat(json.containsKey("friendInvite"));
//            JSONObject invite = (JSONObject)json.get("friendInvite");
//            assertEquals(invite.get("user"), currentFriendname);
//        }
//        consumer.commitSync();
//        consumer.close();
    }

    @Given("I see a friend I want to invite at the friends list,")
    public void i_see_a_friend_I_want_to_invite_at_the_friends_list() {
        //nothing to do; only validate UI
    }

    @When("I click the invite button related to that friend")
    public void i_click_the_invite_button_related_to_that_friend() throws ParseException {
//        currentFriendname=currentUsername+"friend";
//        UserData ud = new UserData();
//        ud.setUsername(currentFriendname);
//        ud.setEmail(currentFriendname+"@mail.com");
//        ud.setPassword("123".toCharArray());
//        us.registerUser(ud);
//        
//        
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBasicAuth(currentUsername, "123");
//        
//        HttpEntity entity = new HttpEntity(headers);
//        
//        ResponseEntity<String> result = restTemplate.exchange(server+randomServerPort+"/friends/"+currentFriendname+"/session/"+currentSessionId, HttpMethod.GET, entity, String.class);
//        JSONObject json = (JSONObject)new JSONParser().parse(result.getBody());
//        assertEquals(result.getStatusCodeValue(),200);
    }

    @Then("I should be notified that a message with the invitation was sent to my friend.")
    public void i_should_be_notified_that_a_message_with_the_invitation_was_sent_to_my_friend() {
        //nothing to do; only validate UI
    }
    
    @Given("I am in a game session,")
    public void i_am_in_a_game_session() throws ParseException {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBasicAuth(currentUsername, "123");
//        
//        JSONObject jsonBody = new JSONObject();
//        jsonBody.put("name", "test_"+currentUsername);
//        jsonBody.put("duration", 600);
//        List<String> words=new ArrayList();
//        words.add("banana");
//        words.add("pera");
//        jsonBody.put("words", words);
//        
//        HttpEntity entity = new HttpEntity(jsonBody, headers);
//        
//        ResponseEntity<String> result = restTemplate.exchange(server+randomServerPort+"/session", HttpMethod.POST, entity, String.class);
//        JSONObject json = (JSONObject)new JSONParser().parse(result.getBody());
//        currentSessionId=(long)json.get("id");
//        assertEquals(result.getStatusCodeValue(),200);
//        
//        jsonBody = new JSONObject();
//
//        entity = new HttpEntity(jsonBody, headers);
//        
//        result = restTemplate.exchange(server+randomServerPort+"/session/"+currentSessionId, HttpMethod.PUT, entity, String.class);
//        assertEquals(result.getStatusCodeValue(),200
        currentSessionId++;
        String jsonBody = "{\"command\": \"startSession\", \"session\": \"esp54_"+currentSessionId+"\"}";
        kt.send("esp54_kafkaTranslatorTopic", jsonBody);
    }

    @When("I raise my right hand above my head")
    public void i_raise_my_right_hand_above_my_head() {
        String jsonBody = "{\"command\": \"notifyAdmin\", \"session\": \"esp54_"+currentSessionId+"\", \"username\":\"testUser1\",\"msg\":\"Notified admin\"}";
        kt.send("esp54_kafkaTranslatorTopic", jsonBody);
    }

    @Then("I should be notified that a message was send to the admin")
    public void i_should_be_notified_that_a_message_was_send_to_the_admin() throws ParseException, InterruptedException {
//        System.out.println(blockingQueue.poll(1, TimeUnit.SECONDS));
        consumer.subscribe(Arrays.asList("esp54_"+currentSessionId));
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5));
        consumer.commitSync();
        assertEquals(records.count(),1);
        for(ConsumerRecord<String,String> r : records){
            JSONObject json = (JSONObject) new JSONParser().parse(r.value());
            //System.out.println(json);
            assertEquals(json.get("user"), "testUser1");
            assertEquals(json.get("session"), "esp54_"+currentSessionId);
            assertEquals(json.get("msg"), "Notified admin");
        }
        consumer.unsubscribe();
        
        
    }

    @When("I raise my left hand above my head")
    public void i_raise_my_left_hand_above_my_head() {
        String jsonBody = "{\"command\": \"notifyAdmin\", \"session\": \"esp54_"+currentSessionId+"\", \"username\":\"testUser1\",\"msg\":\"Notified admin\"}";
        kt.send("esp54_kafkaTranslatorTopic", jsonBody);
    }

    @When("I perform the initial position\\(spread arms)")
    public void i_perform_the_initial_position_spread_arms() throws ParseException {
//        String jsonBody = "{\"username\": \""+currentUsername+"\", \"positions\": {\"Head\": [2098.635,1708.936,0.0], \"Neck\": [2227.439,1410.903,0.0], \"LeftCollar\": [2252.219,1217.666,0.0], "
//                + "\"Torso\": [2314.787,729.7457,0.0], \"Waist\": [2356.344,278.0999,0.0], \"LeftShoulder\": [1860.994,1193.861,0.0], \"RightShoulder\": [2813.465,1175.847,0.0], "
//                + "\"LeftElbow\": [1751.346,713.7789,0.0], \"RightElbow\": [0.0,0.0,0.0], \"LeftWrist\": [0.0,0.0,0.0], \"RightWrist\": [0.0,0.0,0.0], \"LeftHand\": [0.0,1193.861,0.0], "
//                + "\"RightHand\": [0.0,1175.847,0.0], \"LeftHip\": [0.0,0.0,0.0], \"RightHip\": [2672.045,226.5393,0.0], \"LeftKnee\": [0.0,0.0,0.0], \"RightKnee\": [0.0,0.0,0.0], "
//                + "\"LeftAnkle\": [0.0,0.0,0.0], \"RightAnkle\": [0.0,0.0,0.0]}}";
//        kt.send("esp54_"+currentSessionId, jsonBody);
    }

    @Then("I should be recognized by the platform")
    public void i_should_be_recognized_by_the_platform() throws ParseException, InterruptedException {
//        Properties properties = new Properties();
//        properties.put("bootstrap.servers", embeddedKafkaBroker.getBrokersAsString());
//        properties.put("group.id", "es_g54_group_test"+currentUsername);
//        properties.put("auto.offset.reset", "latest");
//        properties.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
//        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        KafkaConsumer consumer = new KafkaConsumer<Integer,String>(properties);
//        consumer.subscribe(Arrays.asList("esp54_"+currentSessionId));
//
//        ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofMillis(100));
////        assertEquals(2, records.count());
//        for (ConsumerRecord<Integer,String> record : records){
//            JSONObject json = (JSONObject) new JSONParser().parse(record.value());
//            if (json.get("positions") != null) {
//                continue;
//            }
//            assertEquals(json.get("username"), currentUsername);
//            assertEquals(json.get("msg"), "User recognized.");
//        }
//        consumer.commitSync();
//        consumer.close();
    }

    @Then("if am the last user recognized, the game session should start.")
    public void if_am_the_last_user_recognized_the_game_session_should_start() {

    }

    @When("I perform the stopping position\\(cross arms over head)")
    public void i_perform_the_stopping_position_cross_arms_over_head() {
//        String jsonBody = "{\"command\": \"stopSession\", \"session\": \"esp54_1\", \"username\":\"testUser1\"}";
//        kt.send("esp54_commandsServiceTopic", jsonBody);
    }

    @Then("I should see the game session to be immediately stopped.")
    public void i_should_see_the_game_session_to_be_immediately_stopped() throws ParseException {
//        consumer.subscribe(Arrays.asList("esp54_eventHandlerTopic"));
//        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5));
//        consumer.commitSync();
//        assertEquals(records.count(),1);
//        for(ConsumerRecord<String,String> r : records){
//            JSONObject json = (JSONObject) new JSONParser().parse(r.value());
//            System.out.println(json);
//            assertEquals(json.get("username"), "testUser1");
//            assertEquals(json.get("session"), "esp54_1");
//            assertEquals(json.get("msg"), "Session ended.");
//        }
//        consumer.unsubscribe();
    }
    
    class DefaultStompSessionHandler implements StompSessionHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return byte[].class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
//            JSONObject json = (JSONObject) o;//new JSONParser().parse(o.value());
//            System.out.println("\n\n\n\n\n\n\n\n\nHERE\n\n\n\n\n\n\n\n\n\n" + json);
            blockingQueue.offer(new String((byte[]) o));
        }

        @Override
        public void afterConnected(StompSession ss, StompHeaders sh) {
            ss.subscribe("/game/admin",this);
        }

        @Override
        public void handleException(StompSession ss, StompCommand sc, StompHeaders sh, byte[] bytes, Throwable thrwbl) {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void handleTransportError(StompSession ss, Throwable thrwbl) {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
