package io.github.ederco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class VendasApplication extends SpringBootServletInitializer {
//    @Bean
//    public CommandLineRunner commandLineRunner(@Autowired Clientes clientes) {
//        return args -> {
//            Cliente c = new Cliente(null,"Fulano");
//            clientes.save(c);
//        };
//    }
    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}



