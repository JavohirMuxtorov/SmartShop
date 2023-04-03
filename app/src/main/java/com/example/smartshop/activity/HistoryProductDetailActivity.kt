package com.example.smartshop.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import com.bumptech.glide.Glide
import com.example.smartshop.R
import com.example.smartshop.databinding.ActivityProductDetailHistoryBinding
import com.example.smartshop.model.ProductDetailModel
import com.example.smartshop.model.TopProductModel
import com.example.smartshop.utils.PrefUtils
import com.r0adkll.slidr.Slidr
import com.r0adkll.slidr.model.SlidrInterface

class HistoryProductDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductDetailHistoryBinding
    lateinit var item: TopProductModel
    var productDetail = listOf(
        ProductDetailModel(
            1, "Смартфон iPhone 12 64GB Blue, White, Black, Green, Red", "9 434 000",
            arrayListOf(
                "product1.1.jpg?alt=media&token=427f03f0-8244-427e-b984-2d33ea731010",
                "product1.2.jpeg?alt=media&token=0209a4a8-58a7-45b8-b36c-1b76be9b9765",
                "product1.3.jpeg?alt=media&token=b3e43b2d-3855-4bd3-9ca5-e167f051de3d"
            ),
            "A14 Bionic, eng tezkor iPhone protsessori. Chetdan chetga OLED displey. Ceramic Shield old paneli tushib ketganda displeyning shikastlanish xavfini to'rt baravar kamaytiradi. Va barcha kameralarda tungi rejim. Bularning barchasi iPhone 12 da <a href=\"https://www.kattabozor.uz/product/smartfon-apple-iphone-12-64gb\">batafsil.</a>"
        ),
        ProductDetailModel(
            2, "Смартфон Xiaomi Redmi Note 9T 4/128GB Black (Global version)", "2 883 000",
            arrayListOf(
                "product2.1.jpg?alt=media&token=48306ec7-163c-4e2b-ac76-8bfcdc2c5b6f",
                "product2.2.jpeg?alt=media&token=98323743-a501-4982-9957-819a32a39767",
                "product2.3.jpeg?alt=media&token=01cf8682-c886-4c24-a0a4-76a2dbdd1410"
            ),
            "Yuqori ishlov berish tezligi va aql bovar qilmaydigan aniqlik Tasvirni qayta ishlashning ilg‘or algoritmlari bilan jihozlangan 48 megapikselli asosiy kamera kechayu kunduz eng yuqori darajadagi tafsilotlarda xotiralarni suratga olish imkonini beradi <a href=\"https://www.kattabozor.uz/product/smartfon-xiaomi-redmi-note-9t-4-128gb\">batafsil.</a>",
        ),
        ProductDetailModel(
            3, "Смартфон iPhone 12 Pro max 128GB Black", "13 356 000",
            arrayListOf(
                "product3.1.jpg?alt=media&token=2f5bcdda-2d0c-4c48-baf7-dd531e5c88c3",
                "product3.2.jpeg?alt=media&token=831d2c5e-5bb9-4d2b-93d3-ff2ae3a7bc59",
                "product3.3.jpeg?alt=media&token=b1e07933-8de3-4957-88ec-a2d1cbbc93c8"
            ),
            "A14 Bionic 5 nanometrli texnologiya yordamida yaratilgan birinchi iPhone protsessoridir. Uning rivojlangan komponentlari hajmi jihatidan atomlar bilan solishtirish mumkin. Unda 40% ko'proq tranzistorlar mavjud, shuning uchun protsessor tezroq ishlaydi va shu bilan birga batareyani tejamkorroq sarflaydi. Yangi signal protsessori esa Dolby Vision standartida video suratga olish imkonini beradi - bu boshqa smartfonlar haqida gapirmasa ham, professional kinokameralarda ham mavjud emas <a href=\"https://www.kattabozor.uz/product/smartfon-apple-iphone-12-pro-max-128gb\">batafsil.</a>",
        ),
        ProductDetailModel(
            4, "Смартфон iPhone 12 mini 64GB Black", "8 692 000",
            arrayListOf(
                "product4.1.jpg?alt=media&token=d2ef6022-29c9-48ea-b8e8-55533662f792",
                "product4.2.jpeg?alt=media&token=0ab2414f-265c-47c7-a87f-63d68ef2e54a",
                "product4.3.jpeg?alt=media&token=b4690c34-f4bb-415e-befe-ef94d98871dc"
            ),
            "A14 Bionic, eng tezkor iPhone protsessori. Chetdan chetga OLED displey. Ceramic Shield old paneli tushib ketganda displeyning shikastlanish xavfini to'rt baravar kamaytiradi. Va barcha kameralarda tungi rejim. Bularning barchasi iPhone 12 da <a href=\"https://www.kattabozor.uz/product/smartfon-apple-iphone-12-mini-64gb\">batafsil.</a>",
        ),
        ProductDetailModel(
            5, "Смартфон Samsung Galaxy A51 64GB White, Blue, Black", "2 985 000",
            arrayListOf(
                "product5.1.jpg?alt=media&token=aefd3e9a-0d88-4f51-b52d-341621e41bbc",
                "product5.2.jpeg?alt=media&token=598392ab-1e4a-4209-a842-f5644ea81c15",
                "product5.3.jpeg?alt=media&token=4149949d-332f-4449-b4a7-489114b4b1f3"
            ),
            "Galaxy A51 ning hoshiyasiz ekrani sizga eng yaxshi immersiv tajribani boshdan kechirish imkonini beradi. Endi siz FHD+ o‘lchamli 6,5 dyuymli sAMOLED ekranda uzluksiz o‘ynashingiz, filmlar tomosha qilishingiz, internetda kezishingiz va ko‘p vazifalarni bajarishingiz mumkin. Yupqa displey ramkasi va maksimal foydalanish mumkin bo'lgan joy bilan o'zingizni tarkibga botiring <a href=\"https://www.kattabozor.uz/product/smartfon-samsung-galaxy-a51-4-64gb\">batafsil.</a>",

            ),
        ProductDetailModel(
            6, "Планшет Samsung Galaxy Tab A 10.1 Black, Gold", "2 597 000",
            arrayListOf(
                "product6.1.jpg?alt=media&token=c1b9b5fd-f744-4d04-b756-7afdcb3921b0",
                "product6.2.jpeg?alt=media&token=8ce254a1-b3a4-4183-afac-d5b22f55f958",
                "product6.3.jpeg?alt=media&token=6430d0cf-a68c-4cda-a92a-f47eae774354"
            ),
            "Batafsil texnik xususiyatlar Operatsion tizim - Android 9.0 Protsessor - Samsung Exynos 7904 Yadrolar soni - 8 Hisoblash yadrosi - Cortex-A53/A73 Ishlab chiqarish jarayoni - 14 nm O'rnatilgan xotira - 32 GB RAM - 2 GB Xotira kartasi uyasi - ha, microSDXC, yuqoriga 512 GB gacha Ekran Ekrani - 10,1\", 1920x1200 Keng ekran - ha Ekran turi - porloq Sensorli ekran - sig'imli, ko'p sensorli Dyuymdagi piksellar soni (PPI) - 224 Video protsessor - Mali-G71 MP2 Simsiz Wi-Fi-ni qo'llab-quvvatlash - ha , Wi-Fi 802.11 ac, WiFi Direct Bluetooth-ni qo'llab-quvvatlash - ha, Bluetooth 5.0, A2DP SIM karta turi - nano SIM SIM-kartalar soni - 1 Mobil aloqa - 3G, GSM900, GSM1800, GSM1900, LTE (B1(2100), B2( 1900), B3 (1800), B4 (AWS), B5 (850), B7 (2600), B8 (900), B12 (700), B17 (700), B20 (800), B28 (700), B66 ( AWS-3), B38(2600),B40(2300)) Kamera Asosiy (orqa) kameralar soni - 1 Asosiy (orqa) kameraning o'lchamlari - 8 MP Kamera xususiyatlari - avtofokus Old kamera - ha, 5 MP Ovoz O'rnatilgan dinamik - ha O'rnatilgan mikrofon - ha GPS funksiyasi - ha GLONASS - ha Avtomatik ekran yo'nalishi - ha Sensorlar - akselerometr formatlarini qo'llab-quvvatlash Audio - AAC, WMA, WAV, OGG, FLAC, MP3 Video - MKV, MP4 USB orqali kompyuterga ulanish - ha USB orqali tashqi qurilmalarni ulash - ixtiyoriy h O'lchamlari va vazni O'lchamlar (UxKxD) - 245,2x149,4x7,5 mm Og'irligi - 470 g Galileo kafolati 1 yil. <a href=\"https://www.kattabozor.uz/uz/web-offer/353840\">batafsil.</a>"
        ),
        ProductDetailModel(
            7, "Планшет Samsung Galaxy Tab S6 10.5 4G Gray, Blue, Rose", "2 597 000",
            arrayListOf(
                "product7.1.jpg?alt=media&token=2a2e56b5-06af-480d-9fd6-7e3eb9bf95ef",
                "product7.2.jpeg?alt=media&token=21b19478-2d4a-4b0d-b1ec-880d962644fa",
                "product7.3.jpeg?alt=media&token=0ac8784c-31e8-4e44-8a2a-0477783e703f"
            ),
            "3x7 mm Og'irligi 467 g Qo'shimcha ma'lumotlar Korpus materiali metall Stylus o'z ichiga oladi Xususiyatlar protsessor - 4x2,3 GHz + 4x1,7 GHz; video o'lchamlari 1920x1080; ANT+; Zal sensori E'lon qilingan sana 2020-04-02 Sotishning boshlanish sanasi 2020-yil aprel Sotib olishdan oldin sotuvchidan xususiyatlar va jihozlarni tekshiring.\n <a href=\"https://www.kattabozor.uz/uz/web-offer/1280129\">batafsil.</a>"
        ),
        ProductDetailModel(
            8, "Планшет Apple iPad (2019) 32Gb Wi-Fi+4G Gray, Silver", "2 597 000",
            arrayListOf(
                "product8.1.png?alt=media&token=74afd1a4-8186-4f14-8d1c-6fe41238dea6",
                "product8.2.jpg?alt=media&token=536ef92a-34c3-4063-b33d-99e226f0d86c",
                "product8.3.jpg?alt=media&token=8a91cf9d-13e1-49be-a65f-ae51e103650b"
            ),
            "Yangi iPad kuchli kompyuter quvvatini portativ qurilmaning soddaligi va ko‘p funksiyaliligi bilan birlashtiradi. Endi u kattaroq 10,2 dyuymli Retina displey, to‘liq o‘lchamli Smart klaviatura va ajoyib yangi iPadOS funksiyalariga ega. Juda qiziqarli - faqat iPad bilan <a href=\"https://www.kattabozor.uz/uz/product/planshet-apple-ipad-7-2019-32gb\">batafsil.</a>"
        ),
        ProductDetailModel(
            9, "Планшет Apple iPad (2020) 128Gb Wi-Fi Gray, Silver, Gold", "2 597 000",
            arrayListOf(
                "product9.1.jpg?alt=media&token=f1c46571-8eb2-4540-8f82-bab74e948996",
                "product9.2.jpg?alt=media&token=d9b7c13b-cdec-4bef-a24f-4c66f99d50a2",
                "product9.3.jpg?alt=media&token=4e0901b9-a3a2-4970-a4df-a4add66e6e82"
            ),
            "9 mm Og'irligi 466 g O'yin planshetining maqsadi Korpus materiali metall Paket tarkibi - Apple iPad Pro 11 (2021) plansheti; - Zaryadlash uchun USB-C kabeli (1 m); - 20 Vt USB-C quvvat adapteri; - ko'rsatmalar <a href=\"https://www.kattabozor.uz/uz/web-offer/1340582\">batafsil.</a>"
        ),
        ProductDetailModel(
            10, "Планшет Apple iPad Air (2020) 256Gb Wi-Fi Gray, Rose, Blue", "2 597 000",
            arrayListOf(
                "product10.1.jpg?alt=media&token=a028c751-98aa-4e9c-a4ab-7408084bbe0f",
                "product10.2.jpg?alt=media&token=31d3751d-ff7e-4466-9951-8127dbd6598b",
                "product10.3.jpg?alt=media&token=dbe1933c-0614-4444-b5de-ffc50c0edca6"
            ),
            "Apple brendi Protsessor Apple A14 Bionic Apple brendi Ichki xotira 256 GB Ekran o'lchami 10,9\" Ekran turi IPS Og'irligi 458 g Asosiy kamera 12 MP Old kamera 7 MP Ekran o'lchamlari 2360x1640 Wi-Fi standarti 802.11ax OS versiyasi iPadOS 14 Apple modeli <a href=\"https://www.kattabozor.uz/uz/web-offer/1306593\">batafsil.</a>"
        ),
        ProductDetailModel(
            11, "Смарт браслет Xiaomi Mi Band 5 (Русское Меню)", "2 597 000",
            arrayListOf(
                "product11.1.jpg?alt=media&token=06f2c832-5e4c-464e-9c34-018b6df4c955",
                "product11.2.jpg?alt=media&token=167270f9-8df2-471a-8432-fa2362a48bed",
                "product11.3.jpg?alt=media&token=dd241e9f-3e59-409b-afcf-be431882eea0"
            ),
            "Katta 1,1 dyuymli rangli displey: kattaroq displey xabarlarni va boshqa bildirishnomalarni ko'rishni osonlashtiradi va dinamik soatlarning yangi davrini boshlaydi. <a href=\"https://www.kattabozor.uz/uz/product/fitnes-braslet-xiaomi-mi-band-5\">batafsil.</a>"
        ),
        ProductDetailModel(
            12, "Смарт часы HUAWEI Watch Fit Black", "2 597 000",
            arrayListOf(
                "product12.1.jpg?alt=media&token=610e5cc4-8d67-4bcc-bc43-c35900111fd8",
                "product12.2.jpg?alt=media&token=6b26bdca-5bef-4b08-9592-abb1dcfea276",
                "product12.3.jpg?alt=media&token=ae8a34ca-6556-477e-8a6d-fd0cc9dddcfe"
            ),
            "Nihoyatda ingichka HUAWEI Watch Fit 21 gramm og'irlikka va yaxshilangan batareyaga ega bo'lib, sizning ideal hamroxingizga aylanadi. Soat energiya tejash algoritmlari bilan 10 kun davomida zaryadsiz ishlashi mumkin. Tez zaryadlash texnologiyasi yordamida siz soatni atigi 5 daqiqa quvvat oldirib, kun davomida foydalanishingiz mumkin. <a href=\"https://www.kattabozor.uz/uz/product/umnye-chasy-huawei-watch-fit\">batafsil.</a>"
        ),
        ProductDetailModel(
            13, "Смарт часы Samsung Galaxy Watch Active 2 44", "2 597 000",
            arrayListOf(
                "product13.1.jpg?alt=media&token=7d7a232e-c35c-4c1f-9d2d-4d11554e2f75",
                "product13.2.jpeg?alt=media&token=06588b0e-9039-4798-b374-935f8e7ac2b8",
                "product13.3.jpeg?alt=media&token=2155e78b-4bbc-4122-9b50-5e0f190333da"
            ),
            "Galaxy Watch Active2 zamonaviy dizayn va 24/7 qulaylikdir. O'zingizga mukammal moslashish uchun keng imkoniyatlardan tanlang: 44 mm yoki 40 mm displey o'lchami, zanglamaydigan po'lat yoki engil alyuminiy. Va gullarning ko'pligi sizning individualligingizni ta'kidlashga yordam beradi: Qizilmiya, Arktika yoki Vanil <a href=\"https://www.kattabozor.uz/uz/product/umnye-chasy-samsung-galaxy-watch-active2-44mm\">batafsil.</a>"
        ),
        ProductDetailModel(
            14, "Смарт часы Samsung Galaxy Watch Active 2 (сталь) 44 мм Gold, Black", "2 597 000",
            arrayListOf(
                "product14.1.jpg?alt=media&token=bb5dec8f-8c47-45d0-8823-b160d591eea6",
                "product14.2.jpeg?alt=media&token=95d17ee3-67fd-4a5f-8a99-566133c2ed75",
                "product14.3.jpeg?alt=media&token=284912a0-4b1c-4215-9467-779e40dcc672"
            ),
            "Galaxy Watch Active2 — это стильный дизайн и комфорт 24/7. Выбери свой идеальный вариант из множества опций: размер дисплея 44 мм или 40 мм, корпус из нержавеющей стали или легкого алюминия. А изобилие цветов поможет подчеркнуть твою индивидуальность: Лакрица, Арктика или Ваниль <a href=\"https://www.kattabozor.uz/uz/product/umnye-chasy-samsung-galaxy-watch-active2-40mm\">batafsil.</a>"
        ),
        ProductDetailModel(
            15, "Смарт часы Xiaomi Amazfit GTR 47 mm Black, Silver", "2 597 000",
            arrayListOf(
                "product15.1.jpg?alt=media&token=fd1704d2-450c-4f27-8e75-abe49bd03430",
                "product15.2.jpeg?alt=media&token=9d3a8128-aac2-4565-8a1f-ce3363e544bc",
                "product15.3.jpeg?alt=media&token=c24d165b-c96f-4566-b79c-957887abe6cb"
            ),
            "Amazfit GTR aqlli soati eng zamonaviy texnologiyalarni klassik dizayn bilan birlashtiradi. Siz nafaqat vaqtni nazorat qilishingiz va o'tkazib yuborilgan qo'ng'iroqlarni qabul qilishingiz, balki suzishingiz, tog 'cho'qqilariga chiqishingiz, yurak urishingizni kuzatishingiz va mashq qilishingiz mumkin. Sportchilar va Amazfit GTR ixlosmandlari uchun turli xil sport turlari bo'yicha 12 ta rejim mavjud - kuch mashqlaridan suzish, tog'larda sayr qilish va piyoda yugurish. O'zingiz uchun jismoniy mashqlar turlarini tanlang va o'z yutuqlaringizni yozing. O'rnatilgan qadam hisoblagich va uxlash fazasi hisoblagichi sizning uyqu / uyg'onish davrlarini va faoliyat davrlarini kuzatib boradi <a href=\"https://www.kattabozor.uz/uz/product/umnye-chasy-amazfit-gtr-47mm\">batafsil.</a>"
        ),
        ProductDetailModel(
            16,
            "Ноутбук Asus S432F \\/ Intel I5-8265 \\/ DDR4 8GB \\/ SSD 512GB \\/ 14\\\" FHD \\/ Win 10",
            "2 597 000",
            arrayListOf(
                "product16.1.jpg?alt=media&token=311ed15e-7afc-49ad-b21e-e2ae6e4483b3",
                "product16.2.jpeg?alt=media&token=9fe0ec1c-7fdf-421f-be74-9bb38a483bb4",
                "product16.3.png?alt=media&token=fc5933ba-683b-46a0-9d56-0523183a4928"
            ),
            "ASUS VivoBook S432 - kundalik foydalanish uchun noutbuk bo'lib, uning original dizayni butun dunyoga uning egasi umumiy e'tirof etilgan standartlarning muxlisi emasligini aytadi. Modelning o'ziga xos xususiyati mobil kompyuter bilan ishlashning innovatsion usuli bo'lgan ScreenPad 2.0 eksklyuziv qo'shimcha displeyidir <a href=\"https://www.kattabozor.uz/uz/product/noutbuk-asus-vivobook-s432\">batafsil.</a>"
        ),
        ProductDetailModel(
            17,
            "Ноутбук Acer A315-57G-3104 \\/ Intel i3-1005G1 \\/ DDR4 4GB \\/ HDD 1TB \\/ VGA 2GB \\/ 15.6\\\" HD LED",
            "2 597 000",
            arrayListOf(
                "product17.1.jpg?alt=media&token=37be3ad7-6e02-493a-933d-10ab5f64f2d1",
                "product17.2.jpeg?alt=media&token=735d4ecc-91b0-4857-82eb-367db9aa08ad",
                "product17.3.jpeg?alt=media&token=528a1ed1-14f0-4904-b44a-3c4923fb4a0a"
            ),
            "Acer Aspire 3 A315-57G noutbuki - bu sizning hayotingizga hamroh bo'lishi mumkin bo'lgan qurilma bo'lib, sizga odatiy ishingizni qilish, o'ynash va Internet resurslaridan bahramand bo'lish imkonini beradi. Buning uchun qurilma juda texnologik, kuchli apparat komponentiga ega. Diagonali 15,6\" bo'lgan ekran TN+film texnologiyasi bo'yicha ishlab chiqarilgan, buning yordamida siz 1920x1080 o'lchamdagi aniq tasvirni olasiz, bu yaxshi ko'rish burchaklari, qulay kontrast va yorqinlik parametrlariga ega. Tarmoqqa kirish uchun Acer Aspire 3 A315-57G barqaror aloqa va tezkor ulanishni ta'minlovchi Wi-Fi modulidan foydalanadi <a href=\"https://www.kattabozor.uz/uz/product/noutbuk-acer-aspire-3-a315-57G\">batafsil.</a>"
        ),
        ProductDetailModel(
            18,
            "Ноутбук HP Envy X360 15 \\/ Intel Core i7-10510 \\/ DDR4 8GB \\/ SSD 512GB \\/ 15.6\\u2033 Full HD IPS, TouchScreen \\/ Win 10",
            "2 597 000",
            arrayListOf(
                "product18.1.jpg?alt=media&token=681885ab-f0d3-4668-83d5-a39b95ebe9c5",
                "product18.2.jpeg?alt=media&token=c4ce3bc5-749d-49cc-a115-f383cc73e495",
                "product18.3.jpeg?alt=media&token=7db481d8-7651-43d5-a84b-50e9304195a7"
            ),
            "Tavsif HP ENVY x360 15-es2003ca noutbuki ijodkorlar uchun haqiqiy xudo bo'ladi, chunki u har xil g'oyalarni amalga oshirish uchun cheksiz imkoniyatlarni taqdim etadi. Uning xarakterli xususiyati - ekranning bir zumda aylanishini ta'minlaydigan maxsus pastadir mavjudligi. HP ENVY x360 15-es2003ca 12 yadroli Intel® Core™ i7-1260P protsessor va 16 Gb tezkor xotira bilan jihozlangan bo'lib, hatto eng talabchan ish yuklarida ham eng yuqori samaradorlikni ta'minlaydi. Wi-Fi 5 moduli World Wide Web tarmog'iga uzluksiz kirish uchun javobgardir. USB Power Delivery-ni qo'llab-quvvatlash uzluksiz ijodiy ish jarayoni uchun batareyani yashin tezligida zaryadlashni ta'minlaydi. Xususiyatlari <a href=\"https://www.kattabozor.uz/uz/web-offer/1377939\">batafsil.</a>"
        ),
        ProductDetailModel(
            19,
            "Ноутбук Lenovo Yoga 730-15IWL \\/ Intel i5-8265U \\/ DDR4 8GB \\/ SSD 256Gb \\/ 15.6\\\" FHD IPS \\/ Wn10",
            "2 597 000",
            arrayListOf(
                "product19.1.jpg?alt=media&token=83e53699-0f5a-46bf-a6d0-a82ca3cf349b",
                "product19.2.jpeg?alt=media&token=6b820bbb-4ac2-47d3-9464-c290d905d7d2",
                "product19.3.jpeg?alt=media&token=289e3ced-8fcb-4e95-8d51-9e561e5f8adc"
            ),
            "Ushbu noutbuk eng ko'p talab qilinadigan funksionallikka ega ishonchli va samarali hisoblash qurilmasiga ega bo'lishni xohlaydiganlar uchun mo'ljallangan. Ushbu model ushbu talablarga to'liq javob beradi. Yuqori sig'imli SSD sizga kerakli virtual ma'lumotni uzoq muddatli saqlash imkoniyatlarini taqdim etadi. Qurilma veb-kamera va mikrofon bilan jihozlangan, buning yordamida siz biznes hamkorlar va ish hamkasblari bilan videokonferensiyalarni tashkil qilishingiz mumkin.Noutbuklar haqida ko'proq ma'lumot oling.Dizayn, rang, texnik xususiyatlar va aksessuarlar ishlab chiqaruvchini ogohlantirmasdan o'zgartirilishi mumkin <a href=\"https://www.kattabozor.uz/uz/web-offer/769353\">batafsil.</a>"
        ),
        ProductDetailModel(
            20,
            "Ноутбук DELL Inspiron 3593 \\/ Intel i7-1065G7 \\/ DDR4 12GB \\/ SSD 512GB \\/ 15.6\\\" TN \\/ Win 10",
            "2 597 000",
            arrayListOf(
                "product20.1.jpg?alt=media&token=a9f28547-39cf-4d4a-95ed-22ff1b279c77",
                "product20.2.jpeg?alt=media&token=9fa4042e-7d2b-4f47-86a1-5941e6d8dfd9",
                "product20.3.jpeg?alt=media&token=2d864828-4439-48ed-8186-2cc79e73b810"
            ),
            "Kattalashtirilgan displey. Ixtiyoriy Full HD paneli yaxshilangan tasvir sifati uchun yorqinroq va boy ranglarni taqdim etadi, ikkala tomondagi ingichka ramka esa ko‘rishni kengaytiradi va chalg‘itadigan narsalarni kamaytiradi <a href=\"https://www.kattabozor.uz/uz/product/noutbuk-dell-vostro-3500\">batafsil.</a>"
        ),
        ProductDetailModel(
            21, "Монитор LG 24 24MP59G-P LED Gaming Monitor HDMI", "2 597 000",
            arrayListOf(
                "product21.1.jpg?alt=media&token=8c58bac8-a675-492e-87cc-b59f4c8b7234",
                "product21.2.jpeg?alt=media&token=f09ce994-17a6-40bb-8607-6ae76cc971b8",
                "product21.3.jpeg?alt=media&token=5af882a1-36fb-4349-9243-635a873b3e34"
            ),
            "IPS texnologiyali LG monitori LCD displeylarning ishlashini ta'kidlaydi. Javob berish vaqti qisqaradi, ranglarning aniqligi yaxshilanadi va foydalanuvchilar ekranni keng burchakdan ko'rishlari mumkin <a href=\"https://www.kattabozor.uz/uz/product/monitor-lg-24mp60g-23-8\">batafsil.</a>"
        ),
        ProductDetailModel(
            22, "Монитор LG 24 IPS 34UM69G-B LED Gaming Monitor HDMI", "2 597 000",
            arrayListOf(
                "product22.1.jpg?alt=media&token=a947e3c3-8a4f-4310-8b30-ebe6c622980b",
                "product22.2.jpeg?alt=media&token=5874e2a3-52a0-4372-8ac3-6f080c7b8ac5",
                "product22.3.jpeg?alt=media&token=93b34525-a19b-467e-bdca-b9ce8dc0ada6"
            ),
            "Ekran o'lchamlari - 3440 x 1440 Video ulagichlar - HDMI, DisplayPort Matrix - IPS Ekran diagonali - 34 \"Egri ekran - ha Ekranni yangilash tezligi - 60 Gts <a href=\"https://www.kattabozor.uz/uz/web-offer/1366553\">batafsil.</a>"
        ),
        ProductDetailModel(
            23,
            "Монитор LG 24 27UL500 LED Monitor HDMI (5mc, UHD, 3840x2160, 4K) White",
            "2 597 000",
            arrayListOf(
                "product23.1.jpg?alt=media&token=76b8b958-0b26-40e1-a720-82186ce8b9d9",
                "product23.2.jpeg?alt=media&token=2dc0dfc5-33f8-4312-bba5-262a39147772",
                "product23.3.jpeg?alt=media&token=11845681-742a-48cd-a0a6-ac5ba5c2a6d2"
            ),
            "Ekran o'lchamlari - 3840x2160 Video ulagichlar - HDMI, DisplayPort, TypeC Matrix - IPS Ekran diagonali - 27\" Egri ekran - yo'q Ekranni yangilash tezligi - 60 Gts Maqola - 1B9T0AA <a href=\"https://www.kattabozor.uz/uz/web-offer/1353306\">batafsil.</a>"
        ),
        ProductDetailModel(
            24, "Монитор LG 24 27UL850 LED Monitor HDMI (UHD 3840x2160)", "2 597 000",
            arrayListOf(
                "product24.1.jpg?alt=media&token=a1e2cc40-c92b-4d87-af12-7ea87b7b45d2",
                "product24.2.png?alt=media&token=36f809d2-6daa-41a8-9a80-74a7ac1cc489",
                "product24.3.jpeg?alt=media&token=aa7984ab-48d6-459d-93f2-33558f78697a"
            ),
            "Ekran o'lchamlari - 3840x2160 Video ulagichlar - HDMI, Displey porti, TypeC Matrix - IPS Ekran diagonali - 27\" Egri ekran - yo'q Ekran yangilanish tezligi - 60 Gts Maqola - RMMNT27NU <a href=\"Монитор LG 24 27UL850 LED Monitor HDMI (UHD 3840x2160)\">batafsil.</a>"
        ),
        ProductDetailModel(
            25,
            "Монитор LG 24 32GK650F Gaming LED WQHD (2560x1440) 144 Ghz, (HDMI x2, DisplayPort) Black",
            "2 597 000",
            arrayListOf(
                "product25.1.jpg?alt=media&token=68409de6-e3fa-471e-a3ea-cb4a61c952f3",
                "product25.2.jpeg?alt=media&token=93a20163-664e-4069-a2f7-430f2f601135",
                "product25.3.jpeg?alt=media&token=50baf975-638b-4496-ba75-9216649acfe0"
            ),
            "Tavsif Gigabyte G32QC monitori yuqori sifatli 31,5 dyuymli VA panel bo‘lib, 1500R egrilik radiusi bilan to‘liq immersiv kino yoki o‘yin tajribasi, qulay ko‘rish va ko‘rish qobiliyatini tez moslashtirish (tekis ekranli displeyga nisbatan) uchun zarur shart-sharoitlarni yaratadi <a href=\"https://www.kattabozor.uz/uz/web-offer/930634\">batafsil.</a>"
        ),
        ProductDetailModel(
            26, "Клавиатура с мышью Rapoo X1810 Keyboard & Mouse Combo", "2 597 000",
            arrayListOf(
                "product26.1.jpg?alt=media&token=f3dcabe1-0979-4266-8d31-3ee6e6650e10",
                "product26.2.jpeg?alt=media&token=23026821-2ca7-419f-8c30-1962d91a108f",
                "product26.3.jpeg?alt=media&token=625a8feb-63f2-464a-9f0f-bc52e09472fd"
            ),
            "Umumiy texnik xususiyatlar Ulanish simli USB ulanish interfeysi dizayni o'ng va chap qo'l uchun tugmalar soni 3 ta ovozsiz tugmalar orqa yorug'lik yo'q Turi sichqoncha sensorlari Optik sensor Ruxsat 1000 dpi Quvvat manbai turi AA Umumiy texnik xususiyatlar Tartib tili Ingliz ruscha ulanish Simsiz USB ulanish interfeysi Quvvat manbai AA klaviatura formatini to'liq o'lchamda yozing <a href=\"https://www.kattabozor.uz/uz/web-offer/1386857\">batafsil.</a>"
        ),
        ProductDetailModel(
            27, "Клавиатура и мышью 2E MK410", "2 597 000",
            arrayListOf(
                "product27.1.jpg?alt=media&token=f5a40f88-3cd9-4ae9-818c-407a9fc53677",
                "product27.2.jpeg?alt=media&token=264860c1-d7ce-41c9-a1a5-275b656333b1",
                "product27.3.png?alt=media&token=1d4b49cc-8af2-46da-a752-e2dff8437e01"
            ),
            "To'liq to'plam: klaviatura va sichqoncha Klaviatura Kabel uzunligi: 1,5 (±5%) m Ulanish interfeysi: USB simsiz Simsiz diapazoni: 10 m Rang: qora Qo'llanish sohasi: uy va ofis uchun Dizayn: klassik Raqamli blok: ha Turi: membrana Orqa yorug'lik: yo'q Raqam klavishlar soni: 104+9 Klaviatura quvvat manbai: 1xAA Oʻlchamlari (WxYxD): 43.00 x 12.30 x 2.50 sm Ogʻirligi: 476 g Sichqoncha turi: Optik LED Simsiz: ha Dizayn: oʻng va chap qoʻl Oʻtkazish gʻildiragi: ha Tugmalar soni: 3 Optik sensor o'lchamlari: 1200 dpi Sichqoncha quvvat manbai: 1xAA O'lchamlari: 113 x 62 x 38 mm Og'irligi: 61 g <a href=\"https://www.kattabozor.uz/uz/web-offer/246962\">batafsil.</a>"
        ),
        ProductDetailModel(
            28, "Клавиатура HyperX Alloy Origins RGB Red", "2 597 000",
            arrayListOf(
                "product28.1.jpg?alt=media&token=908193c9-9a30-414b-b261-d6f2480bce9b",
                "product28.2.jpeg?alt=media&token=1f3a73d8-12be-4482-ba18-515050cc6c73",
                "product28.3.jpeg?alt=media&token=5a5e32e1-ea4c-4810-a33a-1a9ed7727729"
            ),
            "HyperX Alloy Origins Core maxsus ishlab chiqilgan HyperX mexanik switchlariga ega o'ta ixcham va mustahkam raqamli bloksiz klaviatura bo'lib, o'yinchilarga uslub, unumdorlik va ishonchlilikning eng yaxshi kombinatsiyasini taklif etadi. Alloy Origins Core klaviaturasi yaxlit alyuminiy korpus bilan yasalgan, shuning uchun u tezkor bosganda ham kuchli va barqaror bo'lib qoladi va uch xil darajadan birini tanlash imkonini beruvchi oyoqlarga ega. Sonlar blokisiz kompakt konstruksiya stolda sichqoncha uchun ko'proq joy qoldiradi, hamda klaviatura maksimal darajada portativ yechiladigan USB-C simiga ega <a href=\"https://www.kattabozor.uz/uz/product/igrovaia-klaviatura-hyperx-alloy-origins-core\">batafsil.</a>"
        ),
        ProductDetailModel(
            29, "Мышью 2Е MF209 WL Black", "2 597 000",
            arrayListOf(
                "product29.1.jpg?alt=media&token=95627925-5428-4145-8bbc-bba55eedbfde",
                "product29.2.jpeg?alt=media&token=4dcf08b3-c394-4867-8b9b-24223c1358ac",
                "product29.2.jpeg?alt=media&token=4dcf08b3-c394-4867-8b9b-24223c1358ac"
            ),
            "Yumshoq mat plastikdan tayyorlangan simsiz ergonomik sichqoncha uzoq vaqt davomida qulay ishlashni ta'minlaydi. Klassik dizaynda qora rangda ishlangan va sichqonchaning yon tomonlarida geometrik qo'shimchalar mavjud. Kursor tezligini sozlash mumkin: 800, 1200 yoki 1600 DPI." +
                    " <a href=\"https://www.kattabozor.uz/uz/web-offer/919039\">batafsil.</a>"
        ),
        ProductDetailModel(
            30, "Клавиатура Defender HM-830 Black USB", "2 597 000",
            arrayListOf(
                "product30.1.jpg?alt=media&token=d8682cd2-2e66-4120-8711-9237f33bfff8",
                "product30.2.jpeg?alt=media&token=5368436c-8fe4-4939-bf55-5c4ab379d502",
                "product30.3.jpeg?alt=media&token=7b3cc2a8-4826-46c7-bab8-7bc0a5aec8d3"
            ),
            "Ish stoli kompyuteri uchun qisqacha tavsif simli klaviatura USB interfeysi klassik dizayn, membranali tugmalar: 104 o'lcham (WxYxD): 410x16x117 mm <a href=\"https://www.kattabozor.uz/uz/web-offer/807058\">batafsil.</a>"
        ),
    )
    private lateinit var slidr: SlidrInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        item = intent.getSerializableExtra("item") as TopProductModel
        val items = productDetail[item.id - 1]
        binding.name.text = items.name
        slidr = Slidr.attach(this)
        slidr.unlock()
        binding.comment.text = Html.fromHtml(items.comment)
        binding.price.text = items.price
        binding.comment.movementMethod = LinkMovementMethod.getInstance()
        binding.carouselView.pageCount = items.image.count()
        binding.carouselView.setImageListener { position, imageView ->
            Glide.with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/smart-shop-77630.appspot.com/o/${items.image[position]}")
                .into(imageView)
        }

        binding.imgFavorite.setOnClickListener {
            PrefUtils.setFavorite(item)

            if (PrefUtils.checkFavorites(item)){
                binding.imgFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
            }else{
                binding.imgFavorite.setImageResource(R.drawable.favorite)
            }
        }
        if (PrefUtils.checkFavorites(item)){
            binding.imgFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        }else{
            binding.imgFavorite.setImageResource(R.drawable.favorite)
        }



    }
}