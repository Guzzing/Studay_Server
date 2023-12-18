package org.guzzing.studayserver.docs;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(RestDocumentationExtension.class)
public abstract class RestDocsSupport {

    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper;

    @BeforeEach
    void setup(RestDocumentationContextProvider provider) {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTimeSerializer.INSTANCE);

        objectMapper = new ObjectMapper()
                .setSerializationInclusion(Include.NON_NULL)
                .registerModule(javaTimeModule);

        mockMvc = MockMvcBuilders.standaloneSetup(initController())
                .setMessageConverters()
                .apply(documentationConfiguration(provider))
                .build();
    }

    protected abstract Object initController();
}
