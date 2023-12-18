package org.guzzing.studayserver.docs;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.guzzing.studayserver.domain.calendar.facade.AcademyCalendarFacade;
import org.guzzing.studayserver.domain.calendar.service.AcademyCalendarService;
import org.guzzing.studayserver.domain.child.service.ChildFacade;
import org.guzzing.studayserver.domain.child.service.ChildService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
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
        mockMvc = MockMvcBuilders.standaloneSetup(initController())
                .apply(documentationConfiguration(provider))
                .build();

        objectMapper = new ObjectMapper();
    }

    protected abstract Object initController();
}
