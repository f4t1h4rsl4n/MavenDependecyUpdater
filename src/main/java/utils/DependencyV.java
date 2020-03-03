package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DependencyV {
    public static Map<String,Integer> getDependecyAndVersion() {
        List<String> pom=null;
        try {
            pom= Files.readAllLines(Paths.get("pom.xml"));
        } catch (IOException e){
            System.out.println("pom file couldn't find");
        }
        Map<String,Integer> dependenciesAndVersions=new HashMap<>();
        String key="empty";
        for (String s:pom){
            Integer value=null;
            if (s.contains("<!-- https://mvnrepository.com/")){
                key=s.replace("<!-- ","").replace(" -->","").trim();
            }
            if (s.contains("<version>")&&s.contains("</version>")){
                value=Integer.parseInt(s.replaceAll("[^0-9]",""));
                if (!key.equals("empty")) {
                    dependenciesAndVersions.put(key, value);
                }
            }

        }
        return dependenciesAndVersions;
    }
}
