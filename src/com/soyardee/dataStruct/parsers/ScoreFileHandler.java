package com.soyardee.dataStruct.parsers;

import com.soyardee.dataStruct.Score;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Collections;
import java.util.Iterator;
import java.util.PriorityQueue;

public class ScoreFileHandler {

    //WARNING Overwrites previous file with the current heap every time.
    public static void writeOut(PriorityQueue<Score> list, String filename) {
        try{
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = docFactory.newDocumentBuilder();

            //root
            Document doc = builder.newDocument();
            Element root = doc.createElement("scores");
            doc.appendChild(root);

            Iterator<Score> it = list.iterator();

            while(it.hasNext()) {
                Score next = it.next();
                Element scoreTitle = doc.createElement("score");
                root.appendChild(scoreTitle);
                scoreTitle.setAttribute("value", "" + next.getScore());

                Element nameTitle = doc.createElement("name");
                nameTitle.appendChild(doc.createTextNode(next.getName()));
                scoreTitle.appendChild(nameTitle);
            }

            //some stuff that I'm not fully sure how it works, but it seems to function ok.
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);

            //TODO make the file a relative location, or at least not the same directory as the jar
            File outputFile = new File(filename);
            StreamResult result = new StreamResult(outputFile);

            //looks like transformers map a DOM xml file in memory to a file stream location. Makes sense.
            transformer.transform(source, result);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PriorityQueue<Score> readIn(String filepath) {
        PriorityQueue<Score> heap = new PriorityQueue<Score>(Collections.reverseOrder());
        try{
            XMLScoreHandler handler = new XMLScoreHandler();
            XMLReader parser = XMLReaderFactory.createXMLReader();
            parser.setContentHandler(handler);
            InputSource source = new InputSource(filepath);
            parser.parse(source);
            heap = handler.getScoreHeap();
        }
        catch(Exception e) {
            System.err.println("cannot read " + filepath);
        }
        return heap;
    }

}
