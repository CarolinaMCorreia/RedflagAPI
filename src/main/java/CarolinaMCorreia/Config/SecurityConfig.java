package CarolinaMCorreia.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class SecurityConfig {

    /**
     * Konfigurerar säkerhetsinställningarna för HTTP-begäranden i applikationen.
     * Här inaktiveras CSRF-skydd, CORS aktiveras med standardinställningar, och olika
     * säkerhetsregler tillämpas på olika URL-mönster.
     *
     * @param httpSecurity - Objekt som tillåter konfiguration av webbaserad säkerhet för specifika HTTP-begäranden.
     * @return En SecurityFilterChain som specificerar säkerhetskonfigurationen.
     * @throws Exception - Om det uppstår något fel under konfigurationen.
     */


    /**
     * Konfigurerar CORS-filter för att tillåta specifika domäner, metoder och headers.
     * Detta möjliggör att klienter från andra domäner kan skicka HTTP-begäranden till servern.
     *
     * @return CorsFilter som hanterar CORS-konfigurationen för alla inkommande begäranden.
     */
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
