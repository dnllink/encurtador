package br.com.bonaldo.encurtador.gateways.controllers;

import br.com.bonaldo.encurtador.domain.ShortenedURL;
import br.com.bonaldo.encurtador.gateways.controllers.resource.AnalyticsSummaryResource;
import br.com.bonaldo.encurtador.gateways.mongo.ShortenedURLMongoGateway;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(AnalyticsController.class)
@ActiveProfiles("test")
public class AnalyticsControllerTest {

    private static final String VALID_URL = "http://springfox.github.io/springfox/docs/current/#q13";
    private static final String ID = "123";
    private static final Long CLICK_COUNTER = 0L;
    private static final Long COUNT = 1L;
    private static final int LIST_SIZE = 1;

    private MockMvc mockMvc;

    @Autowired
    private AnalyticsController analyticsController;

    @MockBean
    private ShortenedURLMongoGateway shortenedURLMongoGateway;

    @Autowired
    private MessageSource messageSource;

    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(analyticsController).setControllerAdvice(new CustomExceptionHandler(messageSource)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void redirectToExistentCodeShouldRedirect() throws Exception {
        final ShortenedURL shortenedURL = new ShortenedURL(ID, VALID_URL, CLICK_COUNTER);
        when(shortenedURLMongoGateway.findMostClickedURLs()).thenReturn(Collections.singletonList(shortenedURL));
        when(shortenedURLMongoGateway.countURLs()).thenReturn(COUNT);
        when(shortenedURLMongoGateway.totalClicks()).thenReturn(COUNT);

        final MvcResult mvcResult = mockMvc.perform(
                get("/v1/analytics"))
                .andReturn();

        final AnalyticsSummaryResource response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AnalyticsSummaryResource.class);

        verify(shortenedURLMongoGateway).findMostClickedURLs();
        verify(shortenedURLMongoGateway).totalClicks();
        verify(shortenedURLMongoGateway).countURLs();
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals(COUNT, response.getTotalClicksOnAllURLs());
        assertEquals(COUNT, response.getTotalQuantityURLs());
        assertEquals(LIST_SIZE, response.getTopTenMostClicked().size());
    }
}