package util;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Tree<T> {
    /**
     * Verilen düğümü, predicate tarafından tanımlanan
     * bu ağaçtaki bir düğüme alt öğe olarak ekliyorum.
     * Predicate tarafından hiçbir düğüm tanımlanmazsa, ağaç değişmeden kalır.
     * @param identifier dugumu tanimlamak icin kullanilan predicate.
     * @param child child olarak eklenecek node
     * @return hierarchy'i donuyoruz.
     */
    Tree<T> addChild(Predicate<T> identifier, Tree<T> child);

    /**
     * Düğümler dolaşılarak verilen predicate koşulu aranır
     * koşul bulunursan o node dondurulur, bulunamazsa root node u dondurulur
     * @param identifier dugumu tanimlamak icin kullanilan predicate.
     * @return node
     */
    Tree<T> find(Predicate<T> identifier);

    /**
     * maplememizi saglayan high order fonksiyon
     * Agacin uzerinde yapilacak olan manupulasyon islemlerinin daha kolay yapilmasi saglar
     * ayni zamanda da donus tipini bizim belirledigimiz farkli yapilara agaca donusturebilir.
     * @param transform suandaki agactan arzulanan ciktidaki agaca verilerimizi maplememizi saglar.
     * @param <R> istenen sonucun data turu
     * @return maplenen agac dondurulur
     */
    <R> Tree<R> map(Function<T, R> transform);

    /**
     * Bir azaltma islemidir ve high order bir islemdir.
     * T turunde tek bir deger dondurur.
     * @param initialValue baslangic degeri
     * @param combiner T türünde iki değer alan ve bunları T türünde
     *                 tek bir değerde birleştiren işlev
     * @return azaltmanin sonucu da T turunde bir deger olarak dondurulur.
     */

    T reduce(T initialValue, BiFunction<T, T, T> combiner);

    /**
     * Agaci listeye cevirir bu bir utility fonksiyonumuz hem diger fonksiyonlarin
     * ve yapinin duzgun olup olmadigini test etmek icin kullanacagiz hem de islemlerimizi kolaylastiracak
     * @return liste dondurur
     */
    List<T> toList();

    /**
     * Bu düğümün tüm childlerini (superviseelerini, altindaki calisanlari) döndürün.
     * Düğümün çocuğu yoksa boş bir liste döndürülür.
     * @return childlarin listesi
     */
    List<Tree<T>> children();

    /**
     * o nodedaki datayi almamizi saglar elbetteki bu T turunde olacaktir.
     * @return T turunde dondurur.
     */
    T getData();

}
