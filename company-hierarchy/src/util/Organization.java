package util;

import java.util.List;


/**
 Bu arayüz bir organizasyonu temsil eder.
 Bir organizasyonun sunması beklenen yöntemleri içerir.
 */

public interface Organization{
    /**
     * Bu organizasyona belirtilen özelliklere ve süpervizöre sahip bir çalışan ekleme fonksiyonu.
     * Süpervizör bulunamazsa bu çalışan kuruluşa eklenmeyecektir.
     * @param name çalışan adı
     * @param pay calısanın yıllık ya da aylık ucretı ucretı
     * @param supervisorName supervisorun ismi, supervısor hali hazırda olan bir çalışan olmalı
     */
    void addEmployee(String name, double pay, String supervisorName);

    /**
     * @return şirkette çalısan sayısı
     */
    int NumberOfEmployees();

    /**
     * sizin bizden istediğiniz bir şey olmasa da iterator yapısının ve çalışanların supervisea uygun dizliminin
     * testini yapabilmek için oluşturdum. Çok daha işlevsel amaçlar için de kullanılabilir tabii ki
     * şirkette çalışan kişilerin listesini döndürür
     * bir çok amaç için kullanılabilecek genel kapsamlı bir fonksiyon.
     * @return employee namelerını stirng listesi olarak döndürmek için{@link String}
     */
    List<String> EmployeesOfCompany();

    /**
     * gönderilen isimdeki node'u ve onun root olduğu yaprak kümesi hiearchy bastırır
     * yani bir çalışanı ve varsa onun altında çalışanları, orgımplementteki yardımcı bir fonksiyonla beraber kullanılır
     * @param name
     */
    void print(String name);

    /**
     * çalışan maliyeti onun maaşı ve onun supervisor olduğu çalışanların maaşlarının toplamıdır
     * @param name
     * @return double bir çalışanın şirkete maliyetini döndürür
     */
    double getCost(String name);

    /**
     * çalışanlar arsında en yüksek maaşa sahip olanı döndürür
     */
    void WhoisRich();

}
