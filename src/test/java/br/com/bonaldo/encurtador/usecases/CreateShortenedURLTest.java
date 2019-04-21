package br.com.bonaldo.encurtador.usecases;

import br.com.bonaldo.encurtador.domain.ShortenedURL;
import br.com.bonaldo.encurtador.gateways.ShortenedURLGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class CreateShortenedURLTest {

    private static final String URL = "http://www.google.com";

    @InjectMocks
    private CreateShortenedURL createShortenedURL;

    @Mock
    private ShortenedURLGateway shortenedURLGateway;

    @Test
    public void executeShouldBuildObjectAndSendToGateway() {
        createShortenedURL.execute(URL);

        verify(shortenedURLGateway).save(new ShortenedURL(URL));
    }
}