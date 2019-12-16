import com.sun.net.httpserver.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Map;

public class SimpleHttpServer {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(8000), 0);

        server.createContext("/", new EchoHandler());

        server.setExecutor(null);
        server.start();
    }

    static class EchoHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            StringBuilder builder = new StringBuilder();
            Map<String, Double> data = new Parser().MAP();
            ArrayList<Double> values = new ArrayList<>();
            builder.append("<html><head><meta charset=\"utf-8\"></head><select id=\"from\">");
            int i = 0;
            for (Map.Entry<String, Double> entry : data.entrySet()) {
                //System.out.println(entry.getKey());
               builder.append("<option value=" + i +">"+ entry.getKey() + "</option>");
               i++;
            }
            builder.append("</select>");
            builder.append("конвентировать в");
            builder.append("<select id=\"to\">");
            i = 0;
            for (Map.Entry<String, Double> entry : data.entrySet()) {
                //System.out.println(entry.getKey());
                builder.append("<option value="+ i +">"+ entry.getKey() + "</option>");
                values.add(entry.getValue());
                i++;
            }
            builder.append("</select>");
            builder.append("<p>");
            builder.append("<input id=\"find\" onkeypress=\"change()\">");
            builder.append("<input id=\"get\" disabled>");
            builder.append(script(values));
            builder.append("</body></html>");

            byte[] bytes = builder.toString().getBytes();
            exchange.sendResponseHeaders(200, bytes.length);

            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        }
    }
    public  static String script(ArrayList<Double> values){
        String result = new String();
        result += "\n<script type=\"text/javascript\">function change(){\n";
        result += "var s1,s2,i1;\n";
        result +="s1 = document.getElementById(\"from\").options[document.getElementById(\"from\").selectedIndex].value;\n"+
                "s2 = document.getElementById(\"to\").options[document.getElementById(\"to\").selectedIndex].value;\n";
        result += "i1 = document.getElementById(\"find\").value;\n";
        result += "let array = [";
        for (Double a : values){
            result += a;
            result +=',';
        }
        result += "];\n";
        result += "document.getElementById(\"get\").value = (i1*array[parseInt(s1)])/array[parseInt(s2)];\n}";
        result += "</script>";
        return result;

    }

}