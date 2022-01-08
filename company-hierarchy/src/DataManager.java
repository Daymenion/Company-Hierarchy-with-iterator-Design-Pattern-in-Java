import util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
public class DataManager {

    /* basit bir dataManager mantığıdır farklı objeler geldiğinde farklı şekilde yapılar kurulur
    tabii bunu tek bir interfacee ya da abstract classa bağlayıp onu imza olarak tutmak gerekir
    ama zaten bu bizim projenin konusu değil

    public IterableOrganization base_Source(Nosql_Class  source){ return null;}
    public IterableOrganization base_Source(Sql_Class source){ return null;}
    public IterableOrganization base_Source(AWS_Class source){ return null;}
    public IterableOrganization base_Source(GoogleCloud_Class source){ return null;}

     */


    public IterableOrganization base_Source(String filename) {
        IterableOrganization company = null;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) { //dosya try içerisinde okunuyor
            //kullanıcıdan input alabilmemiz için
            String[] backbone = new String[40];
            ArrayList<String[]> arrOfStr = new ArrayList<>();
            String ceo = "";

            String currentLine;  //Dosyada o anda okunan line ı tutacağımız string
            while ((currentLine = br.readLine()) != null) {
                arrOfStr.add(currentLine.split(",")); //virgüle göre ayırıyoruz
                String supervisor_name = new String(arrOfStr.get(arrOfStr.size()-1)[3]);

                if (supervisor_name.equals("Root")) {
                    company = new OrgImplement(arrOfStr.get(arrOfStr.size()-1)[1], Double.parseDouble(arrOfStr.get(arrOfStr.size()-1)[2]));
                    ceo = arrOfStr.get(arrOfStr.size()-1)[1];
                    arrOfStr.remove(arrOfStr.size()-1);
                }
            }
            for (String[] arr: arrOfStr) {
                boolean found = false;
                for (String[] arr1: arrOfStr) {
                    if (arr1[1].split(" ")[0].equalsIgnoreCase(arr[3])){
                        arr[3] = arr1[1];
                        found = true;
                    }
                }
                if (!found)
                    arr[3] = ceo;
                company.addEmployee(arr[1], Double.parseDouble(arr[2]), arr[3]);
            }
        } catch (Exception e) {
            System.out.println("You have incorrect entries, please double-check the values you entered, " +
                    "\neven a simple letter or space can cause these errors. " +
                    "\nIn the error tree, the error and which region gave this error will be written.");
            e.printStackTrace();
        }
        return company;
    }
}
