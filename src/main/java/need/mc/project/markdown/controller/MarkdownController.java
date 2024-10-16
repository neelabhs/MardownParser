package need.mc.project.markdown.controller;

import need.mc.project.markdown.IMarkDownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * This application provides 2 endpoints
 *
 * 1. /upload-file - which receives a text file upload in a 'markdown' format and returns
 * and HTML formatted text
 *
 * 2. /upload-text - which receives a text input in body in a 'markdown' format and returns
 *  * and HTML formatted text
 *
 *  Any line that is not in the 'markdown' format is returned as a <p>
 *
 * */

@RestController
@RequestMapping("/")
public class MarkdownController {

    @Autowired
    IMarkDownService markDownService;

    /**
     * Controller class takes a markdown text as input and transforms it into html.
     * Empty input results in a bad request.
     * Swagger Link: http://localhost:8080/swagger-ui.html
     */
    @PostMapping("/upload-text")
    public ResponseEntity<String> handleTextRequest(@RequestBody String text) throws IOException {
        if(text.isEmpty()){
            return new ResponseEntity<>("Input is empty!", HttpStatus.BAD_REQUEST);
        }
        String htmlContent = markDownService.transformHTML(text);

        // Set the response headers and return the HTML content
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "text/html");

        return new ResponseEntity<>(htmlContent, headers, HttpStatus.OK);
    }



    /**
     * To send a multi-part file as input use:
     * curl -X POST http://localhost:8080/upload -F "file=@path/to/file.txt" -F "key=value"
     * in Postman
     * form-data in body and then choose file from local disk
     * curl --location 'http://localhost:8080/file/upload-txt' \
     * --form 'file=@"/Users/neelabhsinha/Documents/Projects/mc/Input/test.txt"'
     *
     * from a JS->
     *
     * fetch('/upload', {
     *   method: 'POST',
     *   body: formData
     * }).then(response => response.json())
     *   .then(data => console.log(data))
     *   .catch(error => console.error('Error:', error));
     *
     * Swagger Link: http://localhost:8080/swagger-ui.html
     * */

    @PostMapping(value = "/upload-file", consumes = "multipart/form-data")
    public ResponseEntity<byte[]> handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        // Check if the file is empty
        if (file.isEmpty()) {
            return new ResponseEntity<>("File is empty!".getBytes(), HttpStatus.BAD_REQUEST);
        }

        // Read the contents of the text file
        String fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);

        // Convert the text content to an HTML format
        String htmlContent = markDownService.transformHTML(fileContent);

        // Prepare the HTML file to be returned as a downloadable response
        byte[] htmlBytes = htmlContent.getBytes(StandardCharsets.UTF_8);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=output.html");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/html");

        return new ResponseEntity<>(htmlBytes, headers, HttpStatus.OK);
    }








}
