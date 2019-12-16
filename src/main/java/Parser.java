import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;


public class Parser {
    public Map<String, Double> MAP() {
        Map<String, Double> map = new HashMap<String, Double>();
        try {
            Document document = Jsoup.connect("https://finance.rambler.ru/currencies/").get();
            final Elements aElements = document.getElementsByAttributeValue("class", "finance-currency-table__tr");
            aElements.forEach(aElement -> {
                Element element = aElement.child(1);
                Double number_currency = Double.valueOf(element.text());
                element = aElement.child(2);
                String name_currency = element.text();
                element = aElement.child(3);
                Double price_currency = Double.valueOf(element.text());
                map.put(name_currency, price_currency/number_currency);
            });
            map.put("Российский рубль", 1.0);
        }
        catch (Exception e){
            System.out.println(e);
        }
        return map;
    }
}
