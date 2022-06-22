package br.com.infotera.santander.getnet.config;

import br.com.infotera.common.util.Utils;
import com.google.gson.Gson;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration(value = "GetNetConfiguration")
public class GetNetConfiguration {

    @Bean(name = "restTemplateGetNet")
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(240000);
        return new RestTemplate(factory);
    }

    @Bean(name = "mappingJackson2HttpMessageConverterGetNet")
    public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_FORM_URLENCODED));
        return mappingJackson2HttpMessageConverter;
    }

    @Bean(name = "gsonGetNet")
    public Gson gson() {
        return Utils.getGson();
    }
}
