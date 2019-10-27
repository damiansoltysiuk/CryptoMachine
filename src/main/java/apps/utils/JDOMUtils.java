package apps.utils;

import apps.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JDOMUtils {
    public static void writeJDOMData(User user, String filepath) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            Element rootElement = doc.createElementNS("US", "Users");
            doc.appendChild(rootElement);
            rootElement.appendChild(getUser(doc, "1", user.getLogin(), user.getLoginPass(), user.getEmail(), user.getEmailPass(), user.getCipherMethod()));
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult console = new StreamResult(System.out);
            StreamResult file = new StreamResult(new File(filepath));
            transformer.transform(source, console);
            transformer.transform(source, file);
            System.out.println("DONE");


        } catch (Exception e) {
            DialogUtils.errorDialog(e.getMessage());
        }
    }

    private static Node getUser(Document doc, String id, String login, String loginpass, String email, String emailpass, String cipherMethod) {
        Element user = doc.createElement("User");
        user.setAttribute("id", id);
        user.appendChild(getUserElements(doc, user, "login", login));
        user.appendChild(getUserElements(doc, user, "loginPass", loginpass));
        user.appendChild(getUserElements(doc, user, "email", email));
        user.appendChild(getUserElements(doc, user, "emailPass", emailpass));
        user.appendChild(getUserElements(doc, user, "cipherMethod", cipherMethod));
        return user;
    }

    private static Node getUserElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

//    static void readerXML() {
//        String filepath = "src/main/resources/dbfile/users.xml";
//        File xmlFile = new File(filepath);
//        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder dBuilder;
//        try {
//            dBuilder = dbFactory.newDocumentBuilder();
//            Document doc = dBuilder.parse(xmlFile);
//            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
//            NodeList nodeList = doc.getElementsByTagName("User");
//            List<User> userList = new ArrayList<>();
//            for (int i = 0; i < nodeList.getLength(); i++) {
//                userList.add(getUser(nodeList.item(i)));
//            }
//            for (User u : userList) {
//                System.out.println(u.toString());
//            }
//        } catch (ParserConfigurationException | SAXException | IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static User getUser(Node node) {
//        User user = new User();
//        if (node.getNodeType() == Node.ELEMENT_NODE) {
//            Element element = (Element) node;
//            user.setLogin(getTagValue("login", element));
//            user.setLoginPass(getTagValue("loginPass", element));
//            user.setEmail(getTagValue("email", element));
//            user.setEmailPass(getTagValue("emailPass", element));
//            user.setCipherMethod(getTagValue("cipherMethod", element));
//        }
//        return user;
//    }
//
//    private static String getTagValue(String tag, Element element) {
//        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
//        Node node = (Node) nodeList.item(0);
//        return node.getNodeValue();
//    }
//
//    static void writerXML() {
//        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder dBuilder;
//        User testUser1 = new User("Damian", "haha1", "pipini@o2.pl", "hahah1", "Vigenere");
//        User testUser2 = new User("Marek", "paha1", "habala@o2.pl", "faifask2", "AtBash");
//        try {
//            dBuilder = dbFactory.newDocumentBuilder();
//            Document doc = dBuilder.newDocument();
//            Element rootElement = doc.createElementNS("US", "Users");
//            doc.appendChild(rootElement);
//            rootElement.appendChild(getUser(doc, "1", testUser1.getLogin(), testUser1.getLoginPass(), testUser1.getEmail(), testUser1.getEmailPass(), testUser1.getCipherMethod()));
//            rootElement.appendChild(getUser(doc, "2", testUser2.getLogin(), testUser2.getLoginPass(), testUser2.getEmail(), testUser2.getEmailPass(), testUser2.getCipherMethod()));
//            TransformerFactory transformerFactory = TransformerFactory.newInstance();
//            Transformer transformer = transformerFactory.newTransformer();
//            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//            DOMSource source = new DOMSource(doc);
//            StreamResult console = new StreamResult(System.out);
//            StreamResult file = new StreamResult(new File("src/main/resources/dbfile/userXML.xml"));
//            transformer.transform(source, console);
//            transformer.transform(source, file);
//            System.out.println("DONE");
//
//
//        } catch (Exception e) {
//            DialogUtils.errorDialog(e.getMessage());
//        }
//    }
}