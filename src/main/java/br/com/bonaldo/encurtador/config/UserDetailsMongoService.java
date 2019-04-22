package br.com.bonaldo.encurtador.config;

import br.com.bonaldo.encurtador.domain.User;
import br.com.bonaldo.encurtador.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
@RequiredArgsConstructor
public class UserDetailsMongoService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException(format("User %s not found", username)));

        return user;
    }
}