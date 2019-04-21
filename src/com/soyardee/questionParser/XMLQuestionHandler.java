package com.soyardee.questionParser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.Stack;

public class XMLQuestionHandler extends DefaultHandler {

    private ArrayList<Question> questionList = new ArrayList<>();
    private Stack<String> elementStack = new Stack<>();
    private Stack<Question> questionStack = new Stack<>();


    //override the default start document method to do whatever
    public void startDocument() throws SAXException {}

    public void endDocument() throws  SAXException {}

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //the list of elements in the stack
        elementStack.push(qName);

        if("question".equals(qName)){
            this.questionStack.push(new Question());
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        this.elementStack.pop();

        if("question".equals(qName)) {
            questionList.add(questionStack.pop());
        }
    }

    public void characters(char[] ch, int start, int length) {
        String value = new String(ch, start, length).trim();
        if (value.length() == 0) return;

        if("prompt".equals(currentElement())) {
            Question q = questionStack.peek();
            q.setPrompt(value);
        }
        else if("correct".equals(currentElement())) {
            Question q = questionStack.peek();
            q.setCorrectAnswer(value);
            q.addAnswer(value);
        }
        else if("alt".equals(currentElement())) {
            Question q = questionStack.peek();
            q.addAnswer(value);
        }

    }

    private String currentElement() { return elementStack.peek();}

    public ArrayList<Question> getQuestionList() {
        return questionList;
    }

}
