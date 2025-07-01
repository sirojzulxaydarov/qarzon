# Qarzon - Qarz Daftari Loyihasi

**Qarzon** - bu Do‘kon va Do'konlar egalari uchun qarzlar va to‘lovlarni yurituvchi backend xizmati.

---

## 📌 Loyihaning asosiy vazifalari
- ✅ Foydalanuvchilar (Admin, StoreOwner) bilan ishlash
- ✅ Har bir foydalanuvchiga tegishli do‘konlar (Store) ro‘yxatini yuritish
- ✅ Har bir do‘kon uchun mijozlar (Customer) ro‘yxatini shakllantirish
- ✅ Har bir mijoz uchun qarzlar (Loan) va to‘lovlar (Payment) ni qayd qilish
- ✅ Qarzdorliklarni avtomatik hisoblash va statistik hisobot berish
- ✅ Mijozlar ma'lumotlarini Excel faylga export qilish
- ✅ REST API orqali barcha ma'lumotlarni boshqarish
- ✅ JWT orqali xavfsizlikni boshqarish

---

## ⚙ Texnologiyalar
- Java 17
- Spring Boot
- Spring Security + JWT
- Spring Data JPA (Hibernate)
- Lombok
- PostgreSQL
- Maven
- RESTful API
---

## 🚀 Loyihani ishga tushirish

### 1. Clone qilish (application.properties da PostgreSQL Configni sozlab oling)

```bash
git clone https://github.com/sirojzulxaydarov/qarzon.git
cd qarzon
```

### 2. PostgreSQL’da database yaratish

```sql
CREATE DATABASE qarzon;
```

### 3. `application.properties` faylida username va passwordingizni to'g'irlab oling

### 4. Loyihani ishga tushurish

```bash
./mvnw spring-boot:run
```

### 5. Swagger orqali API’larni ishlatish

http://localhost:8080/swagger-ui/index.html
