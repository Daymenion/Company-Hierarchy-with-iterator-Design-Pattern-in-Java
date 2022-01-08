package util;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class GroupOfLeaves<T> extends AbstractTree<T> {

    protected List<Tree<T>> children;

    /**
     * yaprak yapımızdan farklı olarak bu yapımızda childrenları da tutuyoruz.
     * Bu yapımız yaprak kümesi yapımız haliyle çalışanların superviseelerini
     * tuttuğumuz ve ağacın birçok özelliğini kodladığımız kısım.
     * @param data
     */
    public GroupOfLeaves(T data) {
        super(data);
        this.children = new LinkedList<Tree<T>>();

    }

    @Override
    public List<Tree<T>> children() {
        return new ArrayList<Tree<T>>(children);
    }


    /**
     * Yaprak nesnemizden çağrı geldikten sonra recursion olarak yaprak kümesi aranıyor.
     * @param identifier dugumu tanimlamak icin kullanilan predicate.
     * @param child child olarak eklenecek node
     * @return hiearchy
     */
    @Override
    public Tree<T> addChild(Predicate<T> identifier, Tree<T> child) {
        //recursion olarak gelenin superviser olup olmadığının kontrolu
        //eğer öyleyse de children listesine nodeumuzu ekliyoruz
        if (identifier.test(this.data)) {
            this.children.add(child);
            return this;
        }
        //nodelar içerisinde recursion olarak childları set ediyoruz.
        //eğer nodeumuz o superviser nodu eklenecek yaprak node, değilse çağrılan nodeun kendisi döner
        for (int i=0;i<this.children.size();i++) {
            this.children.set(
                    i,
                    this.children.get(i).addChild(identifier,child));
        }
        return this;
    }

    /**
     * identifier ile tanımlanan node u yaprak kümesinde arar
     * node u bulursa o nodu bulmazsa rootu (hiearchy) döner
     * bu metodu recursion olarak kurduğumda bulamadığı değerlerde
     * çift metodlu recorsion
     * @param identifier dugumu tanimlamak icin kullanilan predicate.
     * @return Tree<T> bulunan node ya da rootu (hiearchy) döner
     */
    @Override
    public Tree<T> find(Predicate<T> identifier) {
        if(identifier.test(this.getData()))
            return this; //çağrım yapılan node aranan node mu diye kontrol edilir.
        // Aslında bulunmazsa da thisi yolladığımız için bu kısım silinebilir ama bizi tüm hiearchy'i arama külfetinden kutarır


        // gonderilen nodeun (rootun) childları kontrol edilerek başlanır,
        // root harici çağrılarda bulunacak nodun; çağrılan nodun bir çocuğu olduğuna emin olun
        for (Tree<T> child : this.children){
            if (identifier.test(child.getData()))
                return child; // bulundu demektir
            //root nodenn childları kontrol edilir eğer identifer başarısız olursa
            //başarısız olduğu nodeun çoçukları araştırılmak için checkerChild metoduna yollanır
            else if (child.children() != null) {
                Tree<T> child2 = checkerChild(identifier, child);
                if (child2 != null) return child2;
                //checkerChild null dönmemizse nodeu bulmuş demektir
            }
        }
        throw new IllegalStateException("Aradığınız özellikte bir node bulunamadı!\n" +
                "Aradığınız ismin doğru yazıldığını kontrol edin\n" +
                "metodu çağıran node'un aradığınız nodeun hiearcysinde ve onun en az bir nesil üstünde bir node olduğuna emin olun!\n" +
                "Tavsiye edilen metod çağırım şekli root(başlangıç node'u) üzerinden çağırmaktır\n");
        //sürecin durdurulması istenilmiyorsa exepcition fırlatılması kapatılıp aşağıdaki yorum satırı açılabilir.
        //return this; //bulunamadı demektir, sürecin durmaması için çağrım yapılan node yollanır. istenirse null da yazılabilir.
    }

    /**
     * find metodunun yardımcı metodudur
     * loop[gönderilen çocuğun çocuklarını] kontrol eder
     * @param identifier finde gelen koşul
     * @param child finddan gönderilen childlarını kontrol edeceğimiz child
     * @return Tree<T> bulunan node ya da null döner
     */
    private Tree<T> checkerChild(Predicate<T> identifier, Tree<T> child) {
        for (Tree<T> child2 : child.children()) {
            if (identifier.test(child2.getData()))
                return child2; //bulundu demektir
            //findda yaptığımız gibi identifierı kontrol eder eğer false ise
            //false olan nodedun çocukları var mı diye kontrol ederek çocuklarını recursion olarak döner
            else if (child2.children() != null) {
                Tree<T> child3 = checkerChild(identifier, child2);
                if (child3 != null) return child3; //null değilse bulundu demektir
            }
        }
        return null; //bulunamadı demektir
    }

    /**
     * basit bir tolist metodu, yaprak sınıfını incelerseniz orada dataların eklenme işi yapılır
     * buradada yaprak kümesindeki yapraklar tolist ile, bilgileri listeye dönüştürülmüş olur
     * @return List<T></>
     */
    @Override
    public List<T> toList() {
        List<T> result = new ArrayList<T>();
        result.add(this.data);
        for (Tree<T> child: children) {
            result.addAll(child.toList());
        }
        return result;
    }

    /**
     * Klasik bir mapleme işlemidir. Açıkcası ben bu tarz yapılarda bu mapleme işlemini kullanmayı
     * oldukça seviyorum. birçok satırda yapılacak işlem bir mapleme bir de yardımcı fonksiyonla kolayca halledilebilir.
     * @param transform suandaki agactan arzulanan ciktidaki agaca verilerimizi maplememizi saglar.
     * @param <R> generic mapleme sonucu türü
     * @return <R> Tree<R>
     */
    @Override
    public <R> Tree<R> map(Function<T,R> transform) {
        Tree<R> newNode = createNode(transform.apply(this.data));
        if (!(newNode instanceof GroupOfLeaves)) {
            throw new IllegalStateException("New node'un formu GroupOfLeaves değil");
        }
        //bu yapı yaprak kümesini dolaşarak nodelar içerisinde maplemeyi tekrar çağırır
        //childlar eğer bir yaprak kümesi rootu yani GroupOfLeaves objesi içe recursion olarak çağırır
        //eğer bir yaprak kümesinin rootu değilse de tek bir yaprak nodedudur öyleyse yaprak classındaki mapi çağırır
        // bütün hiearchy yaprak nodelarına ayrıştırılana kadar işlem devam eder. Yukarıda da hata kontrolu yapılır
        for (Tree<T> child:children) {
            ((GroupOfLeaves)newNode).children.add(child.map(transform));
        }
        return newNode;
    }

    /**
     * Reduce, maplenen ağacımızdaki değerleri birbiri arkasınca belirli bir işlemin uygulanma fonksiyonudr, örneğin
     * root.map(IEmployee::getPay).reduce(0.0, Double::sum); //burada rootun maliyeti buluyoruz
     * @param initialValue baslangic degeri
     * @param combiner T türünde iki değer alan ve bunları T türünde
     *                 tek bir değerde birleştiren işlev
     * @return T türünde result
     */
    @Override
    public T reduce(T initialValue, BiFunction<T,T,T> combiner) {
        T result = this.data;
        //maplenmiş olan ağacımız üzerinden çağrılır ve ve birbiri üzerine koyarak istenen işlemi uygular. Örneğin
        /*        accumulator  item    örnek işlem(Toplama)
        0. eleman      0        2        2      2, sonraki accumulatora parametre olarak geçer
        1. eleman      2        4        6      6, sonraki accumulatora parametre olarak geçer
        2. eleman      6        1        7      .....buradaki accumulatorumuz result değişkeni,
        en sonda da initialvalue ile son çıktımız arasında son işlem yapılır ve sonuç döndürülür
         */
        for (Tree<T> child:children) {
            //işlemin uygulanması için yaprak classına yolluyoruz
            result = child.reduce(result,combiner);
        }
        //son değerin initialValue'a uygulanması, initialvalue başlangıç değerimiz,
        return combiner.apply(initialValue,result);
    }

    @Override
    protected <R> Tree<R> createNode(R data) {
        return new GroupOfLeaves(data);
    }
}
