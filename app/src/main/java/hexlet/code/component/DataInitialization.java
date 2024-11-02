package hexlet.code.component;

import hexlet.code.model.Label;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class DataInitialization implements ApplicationRunner {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final TaskStatusRepository taskStatusRepository;

    @Autowired
    private final LabelRepository labelRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    private final String defaultEmail = "hexlet@example.com";

    private final String defaultPass = "qwerty";

    private final List<String> defaultTaskStatuses = List.of(
            "draft", "to_review", "to_be_fixed", "to_publish", "published");

    private final List<String> defaultLabels = List.of("bug", "feature");

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userRepository.findByEmail(defaultEmail).orElseGet(() -> {
            var user = new User();
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode(defaultPass)); // Обновлено на setPassword
            return userRepository.save(user);
        });

        defaultTaskStatuses.stream().map(s -> taskStatusRepository.findBySlug(s)
                .orElse(new TaskStatus()
                        .setName(s.substring(0, 1).toUpperCase() + s.substring(1))
                        .setSlug(s)
                )).forEach(taskStatusRepository::save);

        defaultLabels.stream().map(s -> labelRepository.findByName(s)
                .orElse(new Label()
                        .setName(s)
                )).forEach(labelRepository::save);
    }
}
