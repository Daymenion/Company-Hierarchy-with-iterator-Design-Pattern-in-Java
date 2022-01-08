package util;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class LeafOfTree<T> extends AbstractTree<T> {

    /**
     * nodeun bulunduracağı data atanır, atama işlemi abstract classımızda yaptığımız için
     * direkt olarak süper ile datayı ona yolluyoruz
     * @param data nodun içerdiği bilgi
     */
    public LeafOfTree(T data) {
        super(data);
    }

    /**
     * yapımız ağacın yaprakları ve yaprak grupları olarak oluşuyor,
     * bu classımız yaprakları içirdir ağaç içerisinde mudahale yapılacağı zaman
     * yaprak gruvu GroupOfLeaves classımıza çağrılar yapılır, nitekim en alttaki metodumuz olan
     * createnodun amacı da budur. Haliyle bu clasıımız tekil yaprak özellikleri için olduğundan
     * childimizi GroupOfLeaves classımızda erişeceğiz. Bu nitekim diğer metodlarımız için de geçerli olacaktır.
     * @return
     */
    @Override
    public List<Tree<T>> children() {
        return new ArrayList<Tree<T>>();
    }


    @Override
    public Tree<T> addChild(Predicate<T> identifier, Tree<T>
            child) {
        //identifeerımız getname ile ağacı dolaşıp eşlenik isme sahip olanı bulmak olcaktır.
        //Buradaki yapıya giriyorsa da rootumuzun childlarından birisidir demektir.
        //öyleyse de groupofleaves nesnesi ile yaprak kümesininin içinden tam olarak hangisi olduğunu bulmaya yolluyoruz
        //Örnek kullanım root = root.addChild(e -> e.getName().equals(supervisorName), newNode);
        if (identifier.test(data)) {
            //yapragimizi olusturup onu yaprak kumesi
            // icindeki ait oldugu yerde yaratiyoruz.
            //bunu yapmak için de identifierdan supervisiorla uyuşanı arayıp ona child olarak ekliyoruz
            //child ekleme işlemi yukarıdaki metodda açıkladığım gibi groupofleaves de olacaktır.
            //yapı recursion bir yapıdır, oraya baktığınızda da bunu net olarak göreceksiniz
            // recursion yapısını tüm kısımlarda da göreceksiniz.
            Tree<T> newNode = createNode(this.data); //groupofleaves nesnesi yarattık
            newNode.addChild(identifier,child); //groupofleaves çağrısı
            return newNode; //groupofleavesden gelen çağrı sonucunun dönülmesi.
        }
        //bulamamışsa ekleme işlemi yapılmayacak ve root direkt olarak geri dönülecek
        return this;
    }

    /**
     * ağaç yapımmızdaki belirli bir nodeu bulmamıza sağlar.
     * node çağrılan nodesa direkt olarak dönüyoruz. Değilse de yaprak kümesinde aramak için
     * GroupOfLeaves yaratıp çağrımızı yapıyoruz.
     * @param identifier dugumu tanimlamak icin kullanilan predicate.
     * @return aradığımız node
     */
    @Override
    public Tree<T> find(Predicate<T> identifier) {
        //ilk node test edilir
        if (identifier.test(data)) { return this; }

        //ilk nodedan groupofleaves find metod cağrısı yapılır
        Tree<T> newNode = createNode(this.data);
        return newNode.find(identifier);

    }

    /**
     * yaprağımızın bilgisini liste olarak döndürür, GroupOfLeaves de o yaprak groubunu liste olarak döndürür
     * @return liste<data>
     */
    @Override
    public List<T> toList() {
        List<T> result = new ArrayList<T>();
        result.add(this.data);
        return result;
    }

    /**
     * Yaprak kümesindeki yapraklara transformun uygulanma işlemi
     *
     * @param transform suandaki agactan arzulanan ciktidaki agaca verilerimizi maplememizi saglar.
     * @param <R>
     * @return
     */
    @Override
    public <R> Tree<R> map(Function<T,R> transform) {
        return createNode(transform.apply(this.data));
    }

    /**
     * maplemeyle beraber kullanarak belirli functionları yerine getirmemizi sağlar
     * örneğin toplam çalışan sayısını maple, int sum kullanarak alabiliriz. Örnek için
     * maplenmiş ağacımız üzerinden çağrılarak nodeları dolaşarak uygulanması istenen functionu birbiri arkasınca değerleri devam ettirerek uygular
     * mapleme işleminde olduğu gibi uygulama adımı bu classdadır.
     * @param initialValue baslangic degeri
     * @param combiner T türünde iki değer alan ve bunları T türünde
     *                 tek bir değerde birleştiren işlev
     * @return <T></T>
     */
    @Override
    public T reduce(T initialValue, BiFunction<T, T,T> combiner) {
        return combiner.apply(initialValue,this.data);
    }

    /**
     * groupofleaves nesnesi oluşturmamız içindir, yukarıdaki birçok çağrıda kullanıyoruz.
     * @param data
     * @param <R>
     * @return groupofleaves
     */
    @Override
    protected <R> Tree<R> createNode(R data) {
        return new GroupOfLeaves<R>(data);
    }
}
