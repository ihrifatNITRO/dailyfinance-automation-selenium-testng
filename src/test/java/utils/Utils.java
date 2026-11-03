package utils;

import com.github.javafaker.Faker;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    // For registration and login part
    Faker faker = new Faker();
    public String getFirstName(){
        return faker.name().firstName();
    }
    public String getLastName(){
        return faker.name().lastName();
    }
    public String getEmail(){
        String email = "ihrifat.rr+"+ UUID.randomUUID() +"@gmail.com";
        return email;
    }
    public String getPassword(){
        return faker.internet().password();
    }
    public String getPhoneNumber(){
        String ranNumber = faker.number().digits(8);
        String phoneNumber = "014"+ranNumber;
        return phoneNumber;
    }
    public String getAddress(){
        return faker.address().streetAddress();
    }
    public int getGender(){
        int gender = java.util.concurrent.ThreadLocalRandom.current().nextInt(0, 2);
        return gender;
    }

    // For add-cost part
    public String getItemName(){
        return faker.funnyName().name();
    }
    public double getAmount(){
        return faker.number().numberBetween(100, 999);
    }
    public String getPurchaseDate() {
        // Pick a random epoch day between 2020-01-01 and 2025-12-31
        long minDay = LocalDate.of(2020, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2025, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);

        // Convert back to date and format directly to "MMddyyyy"
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        return randomDate.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
    }
    public String getMonth(){
        int month = ThreadLocalRandom.current().nextInt(1, 13);
        return Month.of(month).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }
    public String getRemarks(){
        return faker.dune().quote();
    }
}
