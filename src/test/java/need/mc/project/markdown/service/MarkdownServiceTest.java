package need.mc.project.markdown.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MarkdownServiceTest {

    @Autowired
    private MarkdownService markdownService;


    @Test
    public void transformHTML_headings_good() {
        String response=markdownService.transformHTML("### test");
        assertThat(response.trim()).isEqualTo("<h3>test</h3>");
        String response2=markdownService.transformHTML("##   test");
        assertThat(response2.trim()).isEqualTo("<h2>test</h2>");
        String response1=markdownService.transformHTML("#   test");
        assertThat(response1.trim()).isEqualTo("<h1>test</h1>");
        String response4=markdownService.transformHTML("####   test");
        assertThat(response4.trim()).isEqualTo("<h4>test</h4>");
        String response5=markdownService.transformHTML("#####   test");
        assertThat(response5.trim()).isEqualTo("<h5>test</h5>");
        String response6=markdownService.transformHTML("######   test");
        assertThat(response6.trim()).isEqualTo("<h6>test</h6>");
        String response7=markdownService.transformHTML("### ### test");
        assertThat(response7.trim()).isEqualTo("<h3>### test</h3>");
        String response8=markdownService.transformHTML("######");
        assertThat(response8.trim()).isEqualTo("<h6></h6>");

    }

    @Test
    public void transformHTML_para() {
        String response=markdownService.transformHTML("test test");
        assertThat(response.trim()).isEqualTo("<p>test test</p>");
    }

    @Test
    public void transformHTML_link_good() {
        String response=markdownService.transformHTML("### test [test](test.com) test");
        assertThat(response.trim()).isEqualTo("<h3>test<a href=\"test.com\">test</a>test</h3>");
    }

    @Test
    public void transformHTML_link_edgeCases() {
        String response=markdownService.transformHTML("[link]");
        assertThat(response.trim()).isEqualTo("<p>[link]</p>");

        String response1=markdownService.transformHTML("[link](dubdubdub.com");
        assertThat(response1.trim()).isEqualTo("<p>[link](dubdubdub.com</p>");


    }

    @Test
    public void transformHTML_multLinks() {
        String response=markdownService.transformHTML("## test [linktxt](linkURL) more text [linktxt2](linkurl2) some more text ");
        assertThat(response.trim()).isEqualTo("<h2>test<a href=\"linkURL\">linktxt</a>more text<a href=\"linkurl2\">linktxt2</a>some more text</h2>");
        String response1=markdownService.transformHTML("");
        assertThat(response1.trim()).isEqualTo("");
    }

    @Test
    public void transformHTML_emptyString1() {
        String response=markdownService.transformHTML(" ");
        assertThat(response.trim()).isEqualTo("");
        String response1=markdownService.transformHTML("");
        assertThat(response1.trim()).isEqualTo("");
    }


    @Test
    public void transformHTML_bigInput() {
        String response=markdownService.transformHTML("# Header one\n" +
                "\n" +
                "Hello there\n" +
                "\n" +
                "How are you?\n" +
                "What's going on?\n" +
                "\n" +
                "## Another Header\n" +
                "\n" +
                "This is a paragraph [with an inline link](http://google.com). Neat, eh? this is another link [another link](www.test.com) and [yet another](www.other.com) okay! \n" +
                "\n" +
                "## This is a header [with a link]xxxx(http://yahoo.com)");
        assertThat(response.trim()).isEqualTo("<h1>Header one</h1>\n" +
                "<p>Hello there</p>\n" +
                "<p>How are you?\n" +
                "What's going on?</p>\n" +
                "<h2>Another Header</h2>\n" +
                "<p>This is a paragraph<a href=\"http://google.com\">with an inline link</a>. Neat, eh? this is another link<a href=\"www.test.com\">another link</a>and<a href=\"www.other.com\">yet another</a>okay!</p>\n" +
                "<h2>This is a header<a href=\"http://yahoo.com\">with a link</a></h2>");

    }




}
