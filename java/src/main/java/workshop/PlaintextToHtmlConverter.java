package workshop;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class HtmlUtil{
    public Map<String, String> htmlEscape =new HashMap<>();
    public HtmlUtil(){
        htmlEscape.put("<","&lt");
        htmlEscape.put(">","&gt");
        htmlEscape.put("&","&amp");
    }
    protected String readTextFile() throws IOException {
        return new String(Files.readAllBytes(Paths.get("sample.txt")));
    }
    public boolean isEndOfLine(String characterToConvert){
        return characterToConvert=="\n";
    }
}

public class PlaintextToHtmlConverter {
    String text;
    List<String> result;
    List<String> convertedLine;
    HtmlUtil util=new HtmlUtil();
    public RevisedHTML(){
        result = new ArrayList<>();
        convertedLine = new ArrayList<>();
        try {
            this.text = util.readTextFile();
        }
        catch (IOException e){
            System.out.println(e);
        }
    }

    public String toHtml() throws Exception {
        String htmlLines = basicHtmlEncode();
        return htmlLines;
    }


    private String basicHtmlEncode() {
        for (char character: this.text.toCharArray() ){
            String characterToConvert=String.valueOf(character);
            if(util.isEndOfLine(characterToConvert)){
                addAnNewHtmlLine();
            }
            else if(util.htmlEscape.containsKey(characterToConvert)){
                convertedLine.add(util.htmlEscape.get(characterToConvert));
            }
            else{
                pushACharacterToTheOutput(characterToConvert);
            }
        }
        return getFinalResult();
    }
    private String getFinalResult(){
        addAnNewHtmlLine();
        return String.join("<br />", result);
    }

    private void addAnNewHtmlLine() {
        String line = String.join("", convertedLine);
        result.add(line);
        convertedLine.clear();
    }

    private void pushACharacterToTheOutput(String characterToConvert) {
        convertedLine.add(characterToConvert);
    }
}
