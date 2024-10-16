package need.mc.project.markdown.controller;

import need.mc.project.markdown.service.MarkdownService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class MarkdownControllerTest {

    @Autowired
    private MarkdownController  markdownController;

    @Mock
    private MarkdownService markdownService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void shouldReturnDefaultMessage() throws Exception {
        String text = "Hello, world!";
        String expectedHtml = "<p>Hello, world!</p>";
        when(markdownService.transformHTML(anyString())).thenReturn(expectedHtml);

        this.mockMvc.perform(post("/upload-text").contentType("application/json").accept("*/*").content(text)).
                andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(expectedHtml)));
    }

    @Test
    void emptyRequest() throws Exception {
        String text = "";
        String expectedHtml = "<p>Hello, world!</p>";
        when(markdownService.transformHTML(anyString())).thenReturn(expectedHtml);

        this.mockMvc.perform(post("/upload-text").contentType("application/json").accept("*/*").content(text)).
                andDo(print()).andExpect(status().isBadRequest());
    }




}
