package com.soyardee.dataStruct.parsers;

import com.soyardee.dataStruct.Score;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * Similar to the XMLQuestionHandler, I really should make a super class for that
 * TODO make a super class for these shared resources
 */

public class XMLScoreHandler extends DefaultHandler {
    private PriorityQueue<Score> heap = new PriorityQueue<>(Collections.reverseOrder());
    private Stack<String> elementStack = new Stack<>();
    private Stack<Score> scoreStack = new Stack<>();

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //the list of elements in the stack
        elementStack.push(qName);

        if("score".equals(qName)){
            Score s = new Score();
            if(attributes != null) {
                s.setScore(Integer.parseInt(attributes.getValue(0)));
            }

            this.scoreStack.push(s);
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        this.elementStack.pop();

        if("score".equals(qName)) {
            heap.add(scoreStack.pop());
        }
    }

    public void characters(char[] ch, int start, int length) {
        String value = new String(ch, start, length).trim();
        if (value.length() == 0) return;

        if("name".equals(currentElement())) {
            Score s = scoreStack.peek();
            s.setName(value);
        }
    }

    private String currentElement() { return elementStack.peek();}

    public PriorityQueue<Score> getScoreHeap() {
        return heap;
    }

}
