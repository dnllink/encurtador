package br.com.bonaldo.encurtador.gateways.controllers;

import br.com.bonaldo.encurtador.domain.ShortenedURL;
import br.com.bonaldo.encurtador.gateways.mongo.ShortenedURLMongoGateway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(RedirectController.class)
@ActiveProfiles("test")
public class RedirectControllerTest {

    private static final String VALID_URL = "http://springfox.github.io/springfox/docs/current/#q13";
    private static final String ID = "123";
    private static final Long CLICK_COUNTER = 0L;

    private MockMvc mockMvc;

    @Autowired
    private RedirectController redirectController;

    @MockBean
    private ShortenedURLMongoGateway shortenedURLMongoGateway;

    @Autowired
    private MessageSource messageSource;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(redirectController).setControllerAdvice(new CustomExceptionHandler(messageSource)).build();
    }

    @Test
    public void redirectToExistentCodeShouldRedirect() throws Exception {
        final ShortenedURL shortenedURL = new ShortenedURL(ID, VALID_URL, CLICK_COUNTER);
        when(shortenedURLMongoGateway.findById(ID)).thenReturn(Optional.of(shortenedURL));

        final MvcResult mvcResult = mockMvc.perform(
                get("/" + ID))
                .andReturn();

        verify(shortenedURLMongoGateway).save(shortenedURL);
        assertEquals(HttpStatus.MOVED_PERMANENTLY.value(), mvcResult.getResponse().getStatus());
        assertEquals(VALID_URL, mvcResult.getResponse().getHeader(HttpHeaders.LOCATION));
    }

    @Test
    public void redirectToNonExistentCodeShouldReturnError() throws Exception {
        when(shortenedURLMongoGateway.findById(ID)).thenReturn(Optional.empty());

        final MvcResult mvcResult = mockMvc.perform(
                get("/" + ID))
                .andReturn();

        verify(shortenedURLMongoGateway, times(0)).save(any());
        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    }
}