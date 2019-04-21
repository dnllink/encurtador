package br.com.bonaldo.encurtador.gateways.controllers;

import br.com.bonaldo.encurtador.domain.ShortenedURL;
import br.com.bonaldo.encurtador.usecases.CreateShortenedURL;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(ShortenedURLController.class)
public class ShortenedURLControllerTest {

    private static final String VALID_URL = "http://springfox.github.io/springfox/docs/current/#q13";
    private static final String INVALID_URL = "springfox.github.io/springfox/docs/current/#q13";
    private static final String ID = "123";
    private static final Long CLICK_COUNTER = 0L;

    private MockMvc mockMvc;

    @Autowired
    private ShortenedURLController shortenedURLController;

    @MockBean
    private CreateShortenedURL createShortenedURL;

    @Autowired
    private MessageSource messageSource;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(shortenedURLController).setControllerAdvice(new CustomExceptionHandler(messageSource)).build();
    }

    @Test
    public void createShortenedURLWithValidDataShouldReturnCreatedStatus() throws Exception {
        final ShortenedURL shortenedURL = new ShortenedURL(ID, VALID_URL, CLICK_COUNTER);
        when(createShortenedURL.execute(VALID_URL)).thenReturn(shortenedURL);

        final MvcResult mvcResult = mockMvc.perform(
                post("/v1/url")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(buildPayload(VALID_URL)))
                .andReturn();

        verify(createShortenedURL).execute(VALID_URL);
        assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void createShortenedURLWithInvalidURLShouldReturnErrorStatus() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(
                post("/v1/url")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(buildPayload(INVALID_URL)))
                .andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void createShortenedURLWithNoURLShouldReturnErrorStatus() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(
                post("/v1/url")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(buildPayload("")))
                .andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    private String buildPayload(final String url) {
        return "{\"originalURL\": \"" + url + "\"}";
    }
}