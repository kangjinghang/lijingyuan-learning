package top.lijingyuan.other;

import cn.hutool.core.io.resource.Resource;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.XmlUtil;
import lombok.Data;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.List;
import java.util.Map;

/**
 * XmlReaderTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-07-13
 * @since 1.0.0
 */
public class XmlReaderTest {

    public static void main(String[] args) throws Exception {
        Resource indexInfoResource = ResourceUtil.getResourceObj("indexinfo_20210713.xml");
        System.out.println(indexInfoResource.getName());
        Document indexInfoDocument = XmlUtil.readXML(indexInfoResource.getStream());
//        NodeList nodeList = doc.getElementsByTagName("index");

        Element indexSecurities = XmlUtil.getRootElement(indexInfoDocument);
        List<Element> indexSecurityList = XmlUtil.getElements(indexSecurities, "Security");
        for (Element indexSecurity : indexSecurityList) {
            Map<String, Object> indexMap = XmlUtil.xmlToMap(indexSecurity);
            System.out.println(indexMap);
        }

        System.out.println();
        System.out.println();
        System.out.println("-----------------------------------");
        Resource cnIndexResource = ResourceUtil.getResourceObj("cnindex_20210713.xml");
        System.out.println(cnIndexResource.getName());
        Document cnIndexDocument = XmlUtil.readXML(cnIndexResource.getStream());

        Element tSInstructions = XmlUtil.getRootElement(cnIndexDocument);
        List<Element> indexList = XmlUtil.getElements(tSInstructions, "index");
        for (Element index : indexList) {
            Map<String, Object> indexMap = XmlUtil.xmlToMap(index);
            System.out.println(indexMap);
            NodeList symbolExList = index.getElementsByTagName("SymbolEx");
            if (symbolExList.getLength() == 0) {
                symbolExList = index.getElementsByTagName("Symbol");
            }
            System.err.println(symbolExList.item(0).getTextContent().trim());
        }
    }

    @Data
    public static class IndexInfo {
        private String securityId;
        private String securityIdSource;
        private String symbol;
        private String symbolEx;
        private String currency;
        private Double prevCloseIdx;
    }

}
