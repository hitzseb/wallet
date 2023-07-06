package com.hitzseb.wallet;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Wallet Wizard API",
        version = "1.0", description = "Backend for Wallet Wizard web application"))
public class WalletWizardApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WalletWizardApiApplication.class, args);
    }

}
