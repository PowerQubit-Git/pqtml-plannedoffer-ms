package pt.tml.plannedoffer;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SwaggerDocGenerationTest
{

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void swaggerJsonExists() throws Exception
    {
        var targetApiFile = "planned-offer-v3-api-docs.json";
        String contentAsString = mockMvc
                .perform(MockMvcRequestBuilders.get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();
        try (var writer = new OutputStreamWriter(new FileOutputStream(targetApiFile), StandardCharsets.UTF_8))
        {
            IOUtils.write(contentAsString, writer);
        }
    }
}