package hexlet.code.component;

import hexlet.code.model.Label;
import hexlet.code.repository.LabelRepository;
import io.sentry.Sentry;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LabelInitializer implements ApplicationRunner {

    private final LabelRepository labelRepository;

    @Override
    public void run(ApplicationArguments args) {
        Label feature = new Label();
        feature.setName("feature");

        Label bug = new Label();
        bug.setName("bug");

        var labels = List.of(feature, bug);
        for (Label label : labels) {
            try {
                labelRepository.save(label);
            } catch (DataIntegrityViolationException e) {
                Sentry.captureException(e);
            }
        }
    }
}
