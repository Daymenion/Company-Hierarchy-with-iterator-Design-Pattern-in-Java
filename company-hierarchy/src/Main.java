import util.*;


public class Main {

    public static void main(String[] args) {
        DataManager dataManager = new DataManager();
        IterableOrganization daemon = dataManager.base_Source("input.txt");
        //daemon örnek şirketimizin ismidir.
        /*
        Dikkat!
        Lutfen sorguladiginiz isimleri ingilizce alfabesine gore yaziniz ve var olduğuna emin olunuz
         */
        System.out.println("Mustafa Turksever'in maliyeti: " + daemon.getCost("Mustafa Turksever"));
        daemon.print("Mustafa Turksever");


        System.out.println("\n\nOğuz Demir'in maliyeti: " + daemon.getCost("Oguz Demir"));
        daemon.print("Oguz Demir");

        System.out.println("\n\nAhmet Egeli'in maliyeti: " + daemon.getCost("Ahmet Egeli"));

        //daemon.WhoisRich(); //örnek bir kim en çok maaşa sahip methodu

        /* //iterator örneği, önce adların hepsi sonra maaşların hepsi listeye eklenir
        List<String> names = new ArrayList<String>();
        for (IEmployee e: daemon)  //iterationa örnek olması için
            names.add(e.getName());

        Iterator<IEmployee> iterator = daemon.iterator();
        while(iterator.hasNext()) {
            IEmployee e = iterator.next();
            names.add(Double.toString(e.getPay()));
        }
        for (String name: names)
            System.out.println(name);

         */
    }
}
