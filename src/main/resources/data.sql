INSERT INTO users (id,
                   city,
                   country,
                   number,
                   postal_code,
                   street,
                   email,
                   first_name,
                   last_name,
                   role,
                   gender,
                   phone_number)
VALUES (uuid('28caa286-2949-46aa-b5d0-1a2a22b0e674'),
        NULL,
        NULL,
        NULL,
        NULL,
        NULL,
        'admin@admin.hu',
        'Admin',
        NULL,
        'ADMIN',
        0,
        '06301212012');

INSERT INTO users (id,
                   city,
                   country,
                   number,
                   postal_code,
                   street,
                   email,
                   first_name,
                   last_name,
                   role,
                   gender,
                   phone_number)
VALUES (uuid('d7a49b4c-9ded-4816-92ef-4dfc1f4286e7'),
        'Dabas',
        'Magyarország',
        '59',
        '2371',
        'Tabáni',
        'teszt@teszt.io',
        'Elek',
        'Teszt',
        'USER',
        0,
        '01210120112');

INSERT INTO brand (id, description, image, name) VALUES
                                                     (1, 'A Samsung főleg elektronikai alkatrészeket gyárt, például a lítiumion-akkumulátorokat, a félvezetőket, a chipeket, és a merevlemezeket, például az Apple-nek, a Sonynak, a HTC-nek és a Nokiának. Ez a világ legtöbb mobiltelefont és okostelefont gyártó cége, ami az eredeti Samsung Solstice-vel kezdte meg működését, és[10] később a Samsung Galaxy készülékcsalád népszerűségével folytatta.', 'files/482b9404-1fe8-4e34-83ea-1bb207f8c2da-Samsung_wordmark.svg.png', 'Samsung'),
                                                     (2, 'A Gorenje a legnagyobb szlovén háztartásigép-gyártó, 2018 óta kínai Hisense vállalat leányvállalata. Nagyobb háztartási gépeket gyárt. A kontinens piaci részesedésének 4%-a az övé, ezzel egyike a nyolc legnagyobb európai háztartásikészülék-gyártónak.', 'files/58f2d742-95d3-42a2-b2d4-1b3ec26a38f8-Gorenje_Logo.svg.png', 'Gorenje'),
                                                     (3, 'Az Apple Inc. amerikai multinacionális informatikai vállalat. Fogyasztói elektronikai termékeket és szoftvereket, ill. ezekhez kapcsolódó felhő-szolgáltatásokat fejleszt, tervez, gyárt és forgalmaz. Egyike Amerika öt nagy, meghatározó információ-technológiai vállalatának.', 'files/36302486-7523-4e18-b2a2-4880eb4df44f-Apple_logo_black.svg.png', 'Apple'),
                                                     (4, 'A Genius a számítógép és a mobil perifériák vezető nemzetközi márkája. A Genius márka a tajvani székhelyű KYE Systems Corp céget alapította 1985-ben, és bemutatta első egértermékét.', 'files/ccb0f70e-bbf4-44af-8a73-e5bb6bbddbc1-mk8fgfwdwhgntteznkjw.webp', 'Genius');

INSERT INTO category (id, name, parent_id) VALUES
                                               (1, 'Elektromos eszközök', NULL),
                                               (2, 'Kiegészítők', NULL),
                                               (3, 'Konyhai gépek', 1),
                                               (4, 'Konyhai nagygépek', 3),
                                               (5, 'Szórakoztató elektronika', 1),
                                               (6, 'Projektorok', 5),
                                               (7, 'Televíziók', 5),
                                               (8, 'Számítástechnika', 1),
                                               (9, 'Laptopok', 8),
                                               (10, 'Monitorok', 8),
                                               (11, 'Egerek', 8),
                                               (12, 'Billentyűzetek', 8),
                                               (13, 'Telefontokok', 2),
                                               (14, 'Laptop táska', 2),
                                               (15, 'Konyhai kisgépek', 3);

INSERT INTO product (id, active, count, description, discount_percentage, name, price, brand_id, category_id) VALUES
                                                                                                                  (1, TRUE, 8, 'Típus: Side-by-side hűtőszekrény Szín: Inox Energiaosztály: A++ Nettó térfogat: 394 l Total NoFrost Hűtőrész: Nettó térfogat: 265 l Gyorshűtés Automatikus leolvasztás Polcok száma: 3 Polcok anyaga: Üveg Polcok száma az ajtókban: 4 db Rekeszek száma: 2 CrispZone zöldségtartó rekesz páratartalom szabályozással FreshZone frissen tartó rekesz Félbehajtható tojástartó LED világítás Fagyasztórész: Nettó térfogat: 129 l NoFrost', 0, 'Side-by-side hűtőszekrény', 278999, 2, 4),
                                                                                                                  (2, TRUE, 7, 'Samsung MS23K3513AW/EO Mikrohullámú sütő Sütő űrtartalom: 23 L Teljesítmény: 800 W Energiafogyasztás (Mikrohullám): 1150 W Keep Warm és +30s funkciók Szabályozási mód: Érintőgom Szélesség: 489 mm Magasság: 275 mm Mélység: 374 mm Forgótálca mérete: 288 mm Energiaszintek száma: 6 Súly: 13,5 kg Szín: fehér', 10, 'Mikrohullámú sütő', 39990, 1, 15),
                                                                                                                  (3, TRUE, 9, 'PurColor Finomra hangolt színek az élénk, valósághű képért A PurColor lehetővé teszi, hogy tévéd a színek hatalmas skáláját jelenítse meg az optimális képteljesítmény érdekében, így mindig lenyűgöző színeket és részleteket láthatsz. 4K felskálázás Nézd kedvenc tartalmaidat 4K minőségben! Az erőteljes 4K felskálázás biztosítja, hogy a kedvenc tartalmaidat 4K felbontásban láthasd.', 0, '4K UHD Smart Televízió', 199900, 1, 7),
                                                                                                                  (4, TRUE, 4, 'Típus: Lézerprojektor Szín: Fehér Felbontás: 3840 x 2160 Fényforrás típusa: Egy csatornás lézerfény Fényforrás élettartama: 20000 óra Vetítési arány: 0.25 Képernyőméret: 90~120 Digitális Trapézkorrekció Szemkímélő üzemmód Kijelző típusa: DLP Fényerő (Ansi Lumen) 2200 (maximum) Zajszint (dB) 32dB (A) (Fényes mód, jellemző) Videó: Képjavító elektronika: UHD Processzor HDR 10+ (dekódolható) HLG Kontraszt 1000:1 (Natív) PurColor', 0, 'The Premiere LSP7T Smart 4K UHD Lézerprojektor', 499990, 1, 6),
                                                                                                                  (5, TRUE, 4, 'Tulajdonságok Szín: Asztroszürke Operációs rendszer: MacOS Kijelző Kijelző típusa: WQXGA LED Kijelző mérete: 13” Kijelző felbontása: 2560 x 1600 px Kijelző jellemzői: IPS Hardware Processzor: Apple® M1 processzor Processzor magok száma: 8 Processzor cache: 8 MB Memória mérete: 8 GB Háttértár: 256 GB Háttértár típusa: SSD Jellemzők Webkamera: Igen Optikai meghajtó: Nem Magyar nyelvű billentyűzetkiosztás Audio Beépített sztereó hangszórók Beépített mikrofon WiFi', 0, 'MacBook Air 2020 13 Retina asztroszürke', 399000, 3, 9),
(6, TRUE, 6, 'AMD FreeSync Szupergördülékeny szórakozási élmény. Az AMD Radeon FreeSync™ szinkronban tartja a monitor és a grafikus kártya képfrissítési sebességét, így csökkenti a képszakadásokat. Nézzen filmeket és játsszon játékokat megszakítások nélkül! Még a gyors jelenetek is egyenletesnek és simának tűnnek. Szemkímélő üzemmód és kevesebb képernyő villódzás Játsszon még tovább! A szemkímélő üzemmód minimalizálja a kék fényt, hogy a szemei pihentek és nyugodtak legyenek, amikor hosszabb ideig játszik. Az Odyssey G5 csökkenti az irritáló és fárasztó képernyő villódzást is, így hosszabb ideig koncentrálhat, kevesebb figyelemelterelés és szemfáradtság mellett.', 12, '24 Odyssey FHD 180Hz Gamer monitor', 56000, 1, 10),
                                                                                                                  (7, TRUE, 19, '1200dpi felbontás BlueEye érzékelő szimmetrikus, jobb- és balkezeseknek egyformán alkalmas AA elemtartó, mind a két féle elem használható Csatlakozó: USB Szín: Fekete Gombok száma: 3 4D Scroll: Nincs Vezeték nélküli: 2.4GHz', 0, 'NX-7000 BLACK Egér', 3450, 4, 11),
                                                                                                                  (8, TRUE, 14, 'Normál méretű, magyar kiosztású, gaming billentyűzet Ötletes szivárvány színű. háttér megvilágítás Garantált legalább 2 millió billentyűkattintás 10 multimédiás gyorsvezérlő gomb. 12 Funkció-gomb. Kiosztás: Magyar Méret: Normál Csatlakozó: USB Szín: Fekete', 0, 'SCORPION K215 Billentyűzet', 5999, 4, 12);


INSERT INTO product_images (product_id, image) VALUES
                                                   (1, 'files/26d281bb-d03b-457a-b741-9061ef4d3385-9458399805470_1tuocj8t.png'),
                                                   (1, 'files/471433ed-3745-4d3d-8289-ed5619af9f5b-9458400264222_f7tmuz3v.png'),
                                                   (1, 'files/a7b892ce-dc88-41b7-8d48-e20d02fb1f4f-9458401312798_x8v3sm4z.png'),
                                                   (2, 'files/43add585-14fa-4429-b526-68e6682cc9b4-9137162321950_4zdv0p1j.webp'),
                                                   (2, 'files/fed0bc51-5b45-4145-a0a2-a4fe2846c641-9137162387486_hvfkgllk.webp'),
                                                   (2, 'files/82d0397f-6bae-45e1-b38b-fcff86ac62ba-9137163239454_3jso7xoj.webp'),
                                                   (3, 'files/6956772a-c6d2-402a-8a2d-70e7c15176a4-1_wurrmvr4.webp'),
                                                   (3, 'files/47cb8d8a-a1f9-4dbe-b82c-b3349fc2f928-2_r6a8clja.webp'),
                                                   (3, 'files/0a872ff6-2c3d-453c-9b0a-a99551bfb7d4-3_2phu680o.webp'),
                                                   (4, 'files/6550fdee-41e1-4cd2-ac3c-46d0af04d5a9-9790821793822_dm4lmqhj.png'),
                                                   (4, 'files/e1bcea98-687c-4192-8262-c778fbabb78e-9790821859358_40xsz3k5.webp'),
                                                   (4, 'files/078a18e4-9e00-4b78-b036-8198a77bbec5-9790822842398_q2c46zmj.png'),
                                                   (4, 'files/cdc55a69-ada5-4980-a3cf-a725108736b0-9790824087582_fh14xuj5.png'),
                                                   (5, 'files/44ab7734-f66f-4425-b372-7e7b47772b68-9743057223710_cv1cbasa.webp'),
                                                   (5, 'files/b923ec9e-c44e-4c5d-990c-82ef0dfa6dda-9743057289246_xion49a6.webp'),
                                                   (5, 'files/4af53b2b-9f09-416f-b40c-916ab08c1320-9743058141214_af5zbmtx.webp'),
                                                   (6, 'files/1728da6b-8aa8-4978-8b25-66b8a3bf48ca-s1_7im0zgax.webp'),
                                                   (6, 'files/1ef8eb16-d8ad-4dbc-aadb-8730d552f8df-s4_5cmote6m.webp'),
                                                   (6, 'files/0a196572-d963-441b-ad31-2d58c7f29850-s5_apfxi671.webp'),
                                                   (7, 'files/fb59e67f-3af3-42b3-b34e-bf9593f10f97-8878071480350_qh8p9q5z.webp'),
                                                   (8, 'files/09d3691a-350d-4be9-aa88-707f35ad7527-8874773708830_r9u90sa9.webp');

INSERT INTO orders (id, billing_city, billing_country, billing_number, billing_postal_code, billing_street, date, shipping_city, shipping_country, shipping_number, shipping_postal_code, shipping_street, status, user_id) VALUES
                                                                                                                                                                                                                                (1, 'Dabas', 'Magyaroszág', '59', '2371', 'Tabáni', '2025-05-26 00:53:01.461932', 'Dabas', 'Magyaroszág', '59', '2371', 'Tabáni', 1, 'd7a49b4c-9ded-4816-92ef-4dfc1f4286e7'),
                                                                                                                                                                                                                                (2, 'Dabas', 'Magyaroszág', '59', '2371', 'Tabáni', '2025-05-26 00:53:39.273867', 'Dabas', 'Magyaroszág', '59', '2371', 'Tabáni', 1, 'd7a49b4c-9ded-4816-92ef-4dfc1f4286e7'),
                                                                                                                                                                                                                                (3, 'Dabas', 'Magyaroszág', '59', '2371', 'Tabáni', '2025-05-26 00:54:00.200775', 'Dabas', 'Magyaroszág', '59', '2371', 'Tabáni', 1, 'd7a49b4c-9ded-4816-92ef-4dfc1f4286e7');

INSERT INTO order_item (id, quantity, order_id, product_id) VALUES
                                                                (1, 1, 1, 4),
                                                                (2, 2, 1, 1),
                                                                (3, 3, 1, 2),
                                                                (4, 1, 2, 5),
                                                                (5, 1, 2, 7),
                                                                (6, 1, 2, 8),
                                                                (7, 1, 3, 3);


