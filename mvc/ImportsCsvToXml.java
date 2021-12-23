import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileReader;
import java.util.List;

public class ImportsCsvToXml {

    List<String> parametersFromClassAuthor = List.of("id", "first_name", "last_name", "username");
    List<String> parametersFromClassPostAuthor = List.of("id_post", "id_author");
    List<String> parametersFromClassPost = List.of("id", "post_content", "tags");
    List<String> parametersFromClassComment = List.of("id", "username", "id_post", "comment_content");
    List<String> parametersFromClassAttachment = List.of("id_post", "filename");

    public ImportsCsvToXml() {
    }

    public List<String[]> readCsv(String path) {
        try {
            FileReader fileReaderAuthors = new FileReader(path);
            CSVReader csvReaderAuthors = new CSVReaderBuilder(fileReaderAuthors)
                    .withSkipLines(1)
                    .build();
            return csvReaderAuthors.readAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void createBeans(String classPath, Document doc, Element rootElement,
                            List<String[]> allData, List<String> parametersFromClass) {
        for (String[] row : allData) {

            Element bean = doc.createElement("bean");
            rootElement.appendChild(bean);

            Attr beanClass = doc.createAttribute("class");
            beanClass.setValue(classPath);
            bean.setAttributeNode(beanClass);

            parametersFromClass.forEach(parameter -> {
                Element constructor = doc.createElement("constructor-arg");
                bean.appendChild(constructor);

                Attr constructorName = doc.createAttribute("name");
                constructorName.setValue(parameter);
                constructor.setAttributeNode(constructorName);

                Attr constructor1Value = doc.createAttribute("value");
                constructor1Value.setValue(row[parametersFromClass.indexOf(parameter)]);
                constructor.setAttributeNode(constructor1Value);
            });
        }
    }

    public void writeDataFromCsvToXml() {

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("beans");
            doc.appendChild(rootElement);

            Attr attr1 = doc.createAttribute("xmlns");
            attr1.setValue("http://www.springframework.org/schema/beans");
            rootElement.setAttributeNode(attr1);

            Attr attr2 = doc.createAttribute("xmlns:xsi");
            attr2.setValue("http://www.w3.org/2001/XMLSchema-instance");
            rootElement.setAttributeNode(attr2);

            Attr attr3 = doc.createAttribute("xsi:schemaLocation");
            attr3.setValue("http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd");
            rootElement.setAttributeNode(attr3);

            String authorsPathToClass = "project.mvc.domain.Author";
            String authorsPathToFile = "./src/main/java/project/mvc/data/Authors.csv";
            createBeans(authorsPathToClass, doc, rootElement, readCsv(authorsPathToFile), parametersFromClassAuthor);


            String postsAuthorsPathToClass = "project.mvc.domain.PostAndAuthor";
            String postsAuthorsPathToFile = "./src/main/java/project/mvc/data/Posts_Authors.csv";
            createBeans(postsAuthorsPathToClass, doc, rootElement, readCsv(postsAuthorsPathToFile), parametersFromClassPostAuthor);

            String postsPathToClass = "project.mvc.domain.Post";
            String postsPathToFile = "./src/main/java/project/mvc/data/Posts.csv";
            createBeans(postsPathToClass, doc, rootElement, readCsv(postsPathToFile), parametersFromClassPost);

            String commentsPathToClass = "project.mvc.domain.Comment";
            String commentsPathToFile = "./src/main/java/project/mvc/data/Comments2.csv";
            createBeans(commentsPathToClass, doc, rootElement, readCsv(commentsPathToFile), parametersFromClassComment);

            String attachmentsPathToClass = "project.mvc.domain.Attachment";
            String attachmentsPathToFile = "./src/main/java/project/mvc/data/Attachments.csv";
            createBeans(attachmentsPathToClass, doc, rootElement, readCsv(attachmentsPathToFile), parametersFromClassAttachment);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("./src/main/resources/beans.xml"));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ImportsCsvToXml importsCsvToXml = new ImportsCsvToXml();
        importsCsvToXml.writeDataFromCsvToXml();
    }
}