package need.mc.project.markdown.service;

import need.mc.project.markdown.IMarkDownService;
import org.springframework.stereotype.Service;

@Service
public class MarkdownService implements IMarkDownService {


    @Override
    public String transformHTML(String markdownInput) {
        /** Splitting the input into a line separated stream */
        String[] inputArr=markdownInput.split("\n\n");
        StringBuilder transformedHTMLLine=new StringBuilder();
        for(String s:inputArr){
            transformedHTMLLine.append(transformLine(s));
            //append a new line to format the output
            transformedHTMLLine.append("\n");
        }
        return transformedHTMLLine.toString();
    }





    /**
     *  Method to transform a line of 'markdown' into corresponding HTML.
     *  Rules:
     *  1.First transform the links
     *  2.then identifies the headers
     *  3.defaulting to <p></p> if not found
     *
     *  indexOf - complexity O(n)
     *  substring - before java 7 - O(1) as a new string object on same underlying char[] - after java 7 its maybe O(n)
     *
     *  The two transformation could have been done using regex -
     *  ("^#+ (.*)$") for headers for example
     *
     */
    private String transformLine(String markdownString){
        //remove leading spaces
        markdownString=markdownString.trim();
        StringBuilder htmlString=new StringBuilder();
        //converts the links to <a> tags
        markdownString=transFormLinks(markdownString);

        //convert the headers into <h> and defaults to <p> if not found
        if(markdownString.indexOf("######")==0){
            htmlString.append("<h6>").append(markdownString.substring(6,markdownString.length()).trim()).append("</h6>");
        }else if(markdownString.indexOf("#####")==0){
            htmlString.append("<h5>").append(markdownString.substring(5,markdownString.length()).trim()).append("</h5>");
        }else if(markdownString.indexOf("####")==0){
            htmlString.append("<h4>").append(markdownString.substring(4,markdownString.length()).trim()).append("</h4>");
        }else if(markdownString.indexOf("###")==0){
            htmlString.append("<h3>").append(markdownString.substring(3,markdownString.length()).trim()).append("</h3>");
        }else if(markdownString.indexOf("##")==0){
            htmlString.append("<h2>").append(markdownString.substring(2,markdownString.length()).trim()).append("</h2>");
        }else  if(markdownString.indexOf("#")==0){
            htmlString.append("<h1>").append(markdownString.substring(1,markdownString.length()).trim()).append("</h1>");
        }else{
            if(markdownString.trim().length()>0) {
                htmlString.append("<p>").append(markdownString).append("</p>");
            }
        }
        return htmlString.toString();
    }

    /***
     * Method to convert lines containing links to <a> tags
     *
     * Finds the first occurrence of '['
     * puts the text before [ into prefix
     * puts the text enclosed within [] into linkText
     * puts the text enclosed within () into linkURL
     * puts the text after []() into postFix which is recursively transformed.
     *
     * Creates a builder with prefix+linkURL+linkText + recurse(postfix)
     *
     */

    private String transFormLinks( String line){
        //base condition
        if(line.length()==0) return "";

        int idxLinkTxtStart=line.indexOf("[");
        if(idxLinkTxtStart<0) return line;//if [ not found return the line and stop parsing
        String prefix = line.substring(0, idxLinkTxtStart); //prefix= text before the first '['
        int idxLinkTxtClose = line.indexOf("]", idxLinkTxtStart);
        if(idxLinkTxtClose<0) return line;//if ] not found return the line and stop parsing
        int idxLinkStart = line.indexOf("(", idxLinkTxtStart);
        if(idxLinkStart<0) return line; //if ( not found return the line and stop parsing
        int idxLinkClose = line.indexOf(")", idxLinkTxtStart);
        if(idxLinkClose<0) return line; //if ) not found return the line and stop parsing

        String linkText = line.substring(idxLinkTxtStart+1, idxLinkTxtClose);
        String linkUrl = line.substring(idxLinkStart+1, idxLinkClose);
        String postFix = line.substring(idxLinkClose+1, line.length());

        StringBuilder sb = new StringBuilder().append(prefix.trim()).append("<a href=\"").append(linkUrl).append("\">").append(linkText).append("</a>").append(transFormLinks(postFix).trim());

        return sb.toString();

    }

    /** Parsing without using java String utils - not part of final code*/

    /*
    private String transform(String input){
        char[] carr=input.trim().toCharArray();
        StringBuilder sb=new StringBuilder();

        int hashcount=0;
        boolean isHeading=false;
        for(int i=0;i<carr.length;i++){
            while(i<carr.length && carr[i++]=='#'){
                if(hashcount<=6) {
                    hashcount++;
                }else{
                    break;
                }
                isHeading=true;
            }
            if(carr[i]=='['){
                StringBuilder linkText=new StringBuilder();
                StringBuilder linkUrl=new StringBuilder();
                while(i<carr.length && carr[i]==']'){
                    linkText.append(carr[++i]);
                }
                int checkpointIdx=i;

                //remove spaces between ] and (
                while(i<carr.length && carr[i]==' ')i++;
                if(carr[i]=='('){
                    //StringBuilder linkUrl=new StringBuilder();
                    while(i<carr.length && carr[i]==')'){
                        linkUrl.append(carr[++i]);
                    }
                    if(carr[i]!=')'){

                    }
                    sb.append("<a href=\"").append(linkUrl).append("\">").append(linkText).append("</a>");
                }else{
                    System.out.println("Wrong char between ] and ( ..not a valid link..rollback");
                    sb.append("[").append(linkText).append("]");
                    i=checkpointIdx;
                }

            }

            sb.append(carr[i]);

        }

        StringBuilder parsedString=new StringBuilder();
        if(isHeading){

            parsedString.append("<h").append(Integer.toString(hashcount)).append(">").append(sb).append("</h").append(Integer.toString(hashcount)).append(">");
        }else{
            parsedString=sb;
        }


        return parsedString.toString();
    }


    private String[] extractLinkTokens(int index,String line){
        StringBuilder linkText=new StringBuilder();
        StringBuilder linkURL=new StringBuilder();
        // while()
        return null;
    }
    */




}
