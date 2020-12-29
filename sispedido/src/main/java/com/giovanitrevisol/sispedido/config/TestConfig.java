package com.giovanitrevisol.sispedido.config;

import com.giovanitrevisol.sispedido.services.DBService;
import com.giovanitrevisol.sispedido.services.EmailService;
import com.giovanitrevisol.sispedido.services.MockEmailService;
import com.giovanitrevisol.sispedido.services.SmtpEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.validation.constraints.Email;
import java.text.ParseException;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DBService dbService;

    @Bean
    public boolean instantiateDataBase() throws ParseException {
        dbService.instantiateTestDataBase();
        return true;
    }

    //@Bean
    //public EmailService emailService(){
    //    return new MockEmailService();
   // }

    @Bean
    public EmailService emailService(){
        return new SmtpEmailService() ;
    }
}
