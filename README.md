# Ideasoft Case Study
https://qatestcase.myideasoft.com/ sitesi ziyaret edilir.
Arama kısmına "ürün" yazılarak arama yapılır.
Arama sonucunda listelenen ürünün detayına girilir ve ilgili üründen 5 adet sepete eklenir.
"SEPETİNİZE EKLENMİŞTİR" yazısının görünmesi kontrol edilir.
/sepet sayfasına gidilir ve sepet içeriğinde ilgili üründen 5 adet olduğu kontrol edilir.

BaseTest class ında ayağa kaldırma ve temel kurulumlar bulunmakta. StepImplementation class ında kullanılan metotlar bulunmakta. Specs dosyasının altında bulunan Concept ve Spec. dosyaları ile test koşumu gerçekleştirilmekte. Log4j ile log alınmakta. Gauge frameworkü ve bdd kullanılmakta. Raporlama olarak Gauge Report kullanılmakta. Java version 11 Proje chromedriver olarak 131.0.6778.205 versiyonunu kullanmakta.
Projede geliştirilmesi gereken noktalar : POM yapısı eklenmeli. Chrome versiyon bağımlılığı düzenlenmeli. Allure gibi bir report tool eklenmeli.
