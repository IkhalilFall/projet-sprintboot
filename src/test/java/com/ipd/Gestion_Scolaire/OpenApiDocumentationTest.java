package com.ipd.Gestion_Scolaire;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class OpenApiDocumentationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldExposeSwaggerMetadataAndSecurityScheme() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.info.title").value("Gestion Scolaire API"))
                .andExpect(jsonPath("$.info.version").value("1.0.0"))
                .andExpect(jsonPath("$.components.securitySchemes['Bearer Authentication']").exists());
    }

    @Test
    void shouldDocumentAllEndpointsAndRequestBodies() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paths['/auth/register'].post.requestBody").exists())
                .andExpect(jsonPath("$.paths['/auth/login'].post.requestBody").exists())
                .andExpect(jsonPath("$.paths['/auth/refresh-token'].post.requestBody").exists())
                .andExpect(jsonPath("$.paths['/auth/register'].post.requestBody.content['application/json'].examples").exists())
                .andExpect(jsonPath("$.paths['/cours'].post.requestBody").exists())
                .andExpect(jsonPath("$.paths['/cours/{id}'].put.requestBody").exists())
                .andExpect(jsonPath("$.paths['/cours'].get.responses['200'].content['application/json'].schema.type").value("array"))
                .andExpect(jsonPath("$.paths['/enseignants'].post.requestBody").exists())
                .andExpect(jsonPath("$.paths['/enseignants/{id}'].put.requestBody").exists())
                .andExpect(jsonPath("$.paths['/enseignants'].get.responses['200'].content['application/json'].schema.type").value("array"))
                .andExpect(jsonPath("$.paths['/etudiants'].post.requestBody").exists())
                .andExpect(jsonPath("$.paths['/etudiants/{id}'].put.requestBody").exists())
                .andExpect(jsonPath("$.paths['/etudiants'].get.responses['200'].content['application/json'].schema.type").value("array"))
                .andExpect(jsonPath("$.paths['/etudiants/paginated'].get").exists())
                .andExpect(jsonPath("$.paths['/inscriptions'].post.requestBody").exists())
                .andExpect(jsonPath("$.paths['/inscriptions'].get.responses['200'].content['application/json'].schema.type").value("array"));
    }
}
