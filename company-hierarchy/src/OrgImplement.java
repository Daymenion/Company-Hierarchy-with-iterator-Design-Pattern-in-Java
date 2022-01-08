import util.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

    /**
    * Bu sınıf, çalışanları olan bir kuruluşu temsil eder.
    * Organization ve employe üzerine tüm işlemlerin hayat bulduğu yerdir.
    */

public class OrgImplement implements IterableOrganization {
    private Tree<IEmployee> root;

        /**
         * şirket nesnemizin tanımlanması
         * @param nameCEO şirketi çalışanlarının rootu
         * @param pay ceonun maaşı
         */
    public OrgImplement(String nameCEO, double pay) {
        root = new LeafOfTree(new Employee(nameCEO, pay));
    }

        /**
         * çalışan nesnemiz ve çalışan nesnemizin ekleneceği nodeumuz yaratılıyor.
         * çalışan rootu supervısor olacak şekilde child olarak ekleniyor yani
         * çalışan nodeumuz groupnodu arasında yerini alıyor.
         * @param name çalışan adı
         * @param pay calısanın yıllık ya da aylık ucretı ucretı
         * @param supervisorName supervisorun ismi, supervısor hali hazırda olan bir çalışan olmalı
         */
    @Override
    public void addEmployee(String name, double pay, String
            supervisorName) {
        IEmployee newEmployee = new Employee(name, pay);
        Tree<IEmployee> newNode = new LeafOfTree(newEmployee);
        root = root.addChild(e -> e.getName().equals(supervisorName), newNode);
    }

    @Override
    public int NumberOfEmployees() {
        //return root.map(e -> 1).reduce(0, Integer::sum); //kolay yol
        int number = 0;
        for (IEmployee e: this) //iterator kolay yol
            number ++;
        return number;

        /* //iterator diğer yol
        Iterator<IEmployee> iteratorfornumber = iterator();
        while(iteratorfornumber.hasNext()) {
            iteratorfornumber.next();
            number ++;
        }
        return number;
         */
    }

    //çalışanların bilgilerini hiearchy'ye uyarak liste halinde döner
    @Override
    public List<String> EmployeesOfCompany() {
        //return root.map(IEmployee::getName).toList(); //kolqy yol

        List<String> names = new ArrayList<String>();

        for (IEmployee e: this) //iterator kolay yol
            names.add(e.getName());
        return names;

        /* //iterator diğer yol
        Iterator<IEmployee> iteratorftemp = iterator();
        while(iteratorftemp.hasNext()) {
            IEmployee e = iteratorftemp.next();
            names.add(e.getName());
        }
        return names;
         */

    }

        /**
         * çağıranları hiearchy uygun olarak ekrana basar
         * ağaçta bulundukları levele göre içeriye girintili bir yapı istediğinizden dolayı recursion
         * olarak döner, bu sebeplede yardımcı fonksiyona gönderip recursion yapısını orada sağlıyorum
         * @param name bastırılacak ilk node'un adı
         */
    @Override
    public void print(String name) {
        Tree<IEmployee> roots = root.find(e -> e.getName().equals(name));
        printEmployees(roots, 0);
    }

        /**
         * çalışanın şirkete maliyeti
         * @param name
         * @return
         */
    @Override
    public double getCost(String name) {
        return root.find(e -> e.getName().equals(name)).map(IEmployee::getPay).reduce(0.0, Double::sum);
        /* //başka bir yöntem
        Tree<IEmployee> roots = root.find(e -> e.getName().equals(name));
        double pay = roots.getData().getPay();
        for (Tree<IEmployee> child:roots.children())
            pay += getCost(child.getData().getName());
        return pay;
         */
    }

        /**
         *
         * @param roots kendisinden sonraki çalışanlar başılacak
         * @param level istediğiniz formattan bir tık daha iyi benzer bir formatta yazabilmek için
         *              level 0 root çalışandır, onun supervise ettiği level1dir. böyle devam eder.
         */

    private void printEmployees(Tree<IEmployee> roots, int level) {
        for (int i = 0; i < level; i++)
            System.out.print("\t");
        IEmployee e = roots.getData();
        System.out.println(e.getName() + "," + e.getPay());
        for (Tree<IEmployee> c: roots.children())
            printEmployees(c, level+1);
    }

        /**
         * çalışanlar arasında en yüksek maaşi alanı bulur, iterator için extra bir örnek olsun diye
         */
    @Override
    public void WhoisRich() {
        double maxPay = root.map(IEmployee::getPay).reduce(0.0, Double::max);
        //iterator kullanımı
        Iterator<IEmployee> iteratorforrich = iterator();
        while(iteratorforrich.hasNext()) {
            IEmployee employee = iteratorforrich.next();
            if(employee.getPay() == maxPay)
                System.out.println(employee.getName() + " is richest man with this pay: " + employee.getPay() + "");
        }
        /* //iteratorun daha sade kullanımı
        for (IEmployee employee: this)
            if(employee.getPay() == maxPay)
                System.out.println(employee.getName() + "richest man with this pay" + employee.getPay() + "");

         */
    }

        /**
         * iterator nesnesi yaratmak için
         * @return iterator nesnesi
         */
    @Override
    public Iterator<IEmployee> iterator() {
        return new OrganizationIterator(root);
    }

}
