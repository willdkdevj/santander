package br.com.infotera.santander;

import br.com.infotera.common.util.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class SantanderApplication {

    public static String nrVersao = "Santander Boletos ON versão ";

    @PostConstruct
    public void init() {
        // Setting Spring Boot SetTimeZone
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        Utils.setTpAmbiente("H");
        if (args.length > 0)
            Utils.setTpAmbiente(args[0]);

        nrVersao += SantanderApplication.class.getPackage().getImplementationVersion();
        SpringApplication.run(SantanderApplication.class, args);
    }
}
