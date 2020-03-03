package executer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.DependencyV;
import utils.DriverSetUp;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Updater {
    public static void main(String[] args) {
        WebDriver driver= DriverSetUp.getDriver();
        Map dependenciesAndVersions=DependencyV.getDependecyAndVersion();
        Set<Map.Entry<String,Integer>> entrySet=dependenciesAndVersions.entrySet();
        for (Map.Entry mE:entrySet){
            String link= (String) mE.getKey();
            driver.get(link);
            By updatesXPath=By.xpath("//a[@class='vbtn release']");
           WebElement lastUpdate=driver.findElements(updatesXPath).get(0); //doesn't contain alpha versions
            String versionText=lastUpdate.getText().replaceAll("[^0-9]","");
            Integer version=Integer.parseInt(versionText);
            if (version>(Integer)mE.getValue()){
                lastUpdate.click();
                WebElement dependecyOfLatest=driver.findElement(By.xpath("//textarea[@id='maven-a']"));
                String newDependency=dependecyOfLatest.getText();
                driver.quit();
                String line;
                StringBuffer fullString = new StringBuffer();
                try {
                    BufferedReader pom= Files.newBufferedReader(Paths.get("pom.xml"));
                    while((line=pom.readLine())!=null){
                        fullString.append(line+"\n");
                    }
                    pom.close();
                } catch (IOException e){
                    System.out.println("pom file couldn't find");
                }

                String fatihString=fullString.toString();
                String endOfDependency="</dependency>";
                fatihString=fatihString.replaceFirst("<!-- "+link+"(?s)(.*)"+endOfDependency,newDependency);
                System.out.println("updated to: "+fatihString);
                try {
                    FileOutputStream outFatih = new FileOutputStream("pom.xml");
                    outFatih.write(fatihString.getBytes());
                    outFatih.close();
                }catch (IOException e){
                    System.out.println("couldn't find the file to write on");
                }

            }
        }

    }

}
