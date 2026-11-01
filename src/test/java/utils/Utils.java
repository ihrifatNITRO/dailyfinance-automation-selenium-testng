package utils;

import com.github.javafaker.Faker;

import java.util.UUID;

public class Utils {

    // For registration part
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

}
