package br.com.bonaldo.encurtador.gateways.mongo;

import br.com.bonaldo.encurtador.domain.ShortenedURL;
import br.com.bonaldo.encurtador.domain.TotalClicks;
import br.com.bonaldo.encurtador.gateways.ShortenedURLGateway;
import br.com.bonaldo.encurtador.repository.ShortenedURLRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ShortenedURLMongoGateway implements ShortenedURLGateway {

    private static final String CLICK_COUNTER = "clickCounter";
    private static final String TOTAL = "total";
    private static final String COLLECTION_NAME = "urls";

    private final ShortenedURLRepository repository;
    private final MongoOperations mongoOperations;

    @Override
    public ShortenedURL save(final ShortenedURL shortenedURL) {
        return repository.save(shortenedURL);
    }

    @Override
    public Optional<ShortenedURL> findById(final String code) {
        return repository.findById(code);
    }

    @Override
    public Long countURLs() {
        return repository.count();
    }

    @Override
    public Long totalClicks() {

        final GroupOperation group = Aggregation.group().sum(CLICK_COUNTER).as(TOTAL);
        final ProjectionOperation projection = Aggregation.project(TOTAL);

        final Aggregation aggregation =  Aggregation.newAggregation(group, projection);

        final TotalClicks totalClicks = mongoOperations
                .aggregate(aggregation, COLLECTION_NAME, TotalClicks.class)
                .getUniqueMappedResult();

        return totalClicks.getTotal();
    }

    @Override
    public List<ShortenedURL> findMostClickedURLs() {
        return repository.findFirst10ByOrderByClickCounterDesc();
    }
}