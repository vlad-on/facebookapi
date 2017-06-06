package com.egnyte.facebookapi.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

/**
 * Created by Vlad on 06.06.2017.
 */

@RunWith(MockitoJUnitRunner.class)
//@ContextConfiguration(classes = {TestContext.class, WebAppContext.class})
//@WebAppConfiguration
public class HelloControllerTest {

    //    @Value("${spring.social.facebook.app-id}")
    //TODO: read from property file
//    private String appId = "462724750743444";
    //    @Value("${spring.social.facebook.app-secret}")
//    private String appSecret = "e6ddc6ab45d201c143ef677ba7285da4";
    private final String ACCESS_TOKEN = "access_token=" + "462724750743444" + "|" + "e6ddc6ab45d201c143ef677ba7285da4";
    private static final String BASE_URL = "https://graph.facebook.com/v2.9/";

    @InjectMocks
    private HelloController helloController = new HelloController();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void helloFacebook() throws Exception {
    }

    @Test
    public void makeSureThatFacebookApiIsUp() {
        given().when().get(BASE_URL + "1458558184372813?" + ACCESS_TOKEN).then().statusCode(200);
    }

    @Test
    public void getSearchRequestCheckOneResult() throws Exception {
        String str = helloController.getSearchRequest("Poland", "Poznan", "egnyte");
        assertEquals(str, "[\n" +
                "  {\n" +
                "    \"name\": \"Egnyte Poland\",\n" +
                "    \"latitude\": 52.40474913,\n" +
                "    \"longitude\": 16.940680915\n" +
                "  }\n" +
                "]");
    }

    @Test
    public void getSearchRequestCheckListResults() throws Exception {
        String str = helloController.getSearchRequest("Poland", "Poznan", "egnyte");
        assertEquals(str, "[\n" +
                "  {\n" +
                "    \"name\": \"Egnyte Poland\",\n" +
                "    \"latitude\": 52.40474913,\n" +
                "    \"longitude\": 16.940680915\n" +
                "  }\n" +
                "]");
    }
//
//        mockMvc.perform(get("/too/{id}", 1L))
//                .andExpect(status().isNotFound())
//                .andExpect(view().name("error/404"))
//                .andExpect(forwardedUrl("/WEB-INF/jsp/error/404.jsp"));
//
//        verify(todoServiceMock, times(1)).findById(1L);
//        verifyZeroInteractions(todoServiceMock);
}