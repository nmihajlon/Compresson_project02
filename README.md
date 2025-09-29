# Kompresija i zaštita podataka – Projektni zadatak 2

Ovaj projekat implementira LDPC kod, algoritam dekodiranja pomoću sindroma i Gallager B algoritam. Projekat demonstrira generisanje LDPC matrice, izračunavanje sindroma i korektora, određivanje kodnog rastojanja, kao i simulaciju Gallager B algoritma.

---

## Funkcionalnosti

1. **Generisanje LDPC matrice**
   - Parametri LDPC koda:  
     - n = 15 (dužina koda)  
     - n − k = 9 (broj paritetskih bitova)  
     - wr = 5 (broj jedinica po redu)  
     - wc = 3 (broj jedinica po koloni)
   - Matrica H se konstruše korišćenjem generatora pseudoslučajnih brojeva sa fiksiranim seed-om (jednakim broju indeksa studenta).

2. **Tabela sindroma i korektora**
   - Na osnovu generisane matrice H, kreira se tabela sindroma i korektora.
   - Određuje se kodno rastojanje koda.

3. **Gallager B algoritam**
   - Implementacija Gallager B dekodiranja sa pragovima odlučivanja: th0 = th1 = 0.5.
   - Određuje n-torku greške e sa najmanje jedinica koja nije ispravljena.
   - Upoređuje se sa kodnim rastojanjem odredjenim prethodno.

---
