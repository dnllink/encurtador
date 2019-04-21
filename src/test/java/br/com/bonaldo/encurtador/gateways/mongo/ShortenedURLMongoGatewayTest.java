package br.com.bonaldo.encurtador.gateways.mongo;

import br.com.bonaldo.encurtador.domain.ShortenedURL;
import br.com.bonaldo.encurtador.domain.TotalClicks;
import br.com.bonaldo.encurtador.repository.ShortenedURLRepository;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ShortenedURLMongoGatewayTest {

    private static final String URL = "http://www.google.com";
    private static final String CODE = "123";
    private static final String COLLECTION_NAME = "urls";

    @InjectMocks
    private ShortenedURLMongoGateway shortenedURLMongoGateway;

    @Mock
    private ShortenedURLRepository repository;

    @Mock
    private MongoOperations mongoOperations;

    @Test
    public void saveShouldSendParameterToRepository() {
        ShortenedURL shortenedURL = new ShortenedURL(URL);

        shortenedURLMongoGateway.save(shortenedURL);

        verify(repository).save(shortenedURL);
    }

    @Test
    public void findByIdShouldSendParameterToRepository() {
        shortenedURLMongoGateway.findById(CODE);

        verify(repository).findById(CODE);
    }

    @Test
    public void countURLsShouldCallRepository() {
        shortenedURLMongoGateway.countURLs();

        verify(repository).count();
    }

    @Test
    public void findMostClickedURLsShouldCallRepository() {
        shortenedURLMongoGateway.findMostClickedURLs();

        verify(repository).findFirst10ByOrderByClickCounterDesc();
    }

    @Test
    public void totalClicksShouldCallAggregationWithCorrectParameters() {
        when(mongoOperations
                .aggregate(any(Aggregation.class), eq(COLLECTION_NAME), eq(TotalClicks.class)))
                .thenReturn(new AggregationResults<>(Collections.singletonList(new TotalClicks()), new Document()));

        shortenedURLMongoGateway.totalClicks();

        verify(mongoOperations).aggregate(any(Aggregation.class), eq(COLLECTION_NAME), eq(TotalClicks.class));
    }

}