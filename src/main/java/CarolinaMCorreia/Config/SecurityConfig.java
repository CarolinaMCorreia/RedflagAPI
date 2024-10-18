package CarolinaMCorreia.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // Tillåt bara begäranden från https://cityatlas-env.eba-3dsjymff.eu-north-1.elasticbeanstalk.com
        config.setAllowedOrigins(List.of("*"));
        // Tillåt specifika HTTP-metoder
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        // Tillåt headers som Authorization och Content-Type
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        config.setAllowCredentials(true);

        // Skapa en källa som matchar alla URL-mönster (/**) med ovanstående konfiguration
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
