package TestDataObjects;

import java.util.HashMap;

public class TestDataSetUp {

    //Returns a map of Key-string/Value-integer where the integer is the expected number of hits when the string is searched.
    public static HashMap<String, Integer> loadSearchStrings(){
        HashMap<String, Integer> testSearchStrings = new HashMap<>();
        testSearchStrings.put("bear", 3);
        testSearchStrings.put("t-shirt", 1);
        return testSearchStrings;
    }

    public static HashMap<String, TestUser> loadTestUsers(){

            HashMap<String, TestUser> testUsers = new HashMap<>();

            TestUser testUser1 = new TestUser();
            testUser1.firstName = "Göta";
            testUser1.lastName = "Pärlås";
            testUser1.email = System.currentTimeMillis() + "GotaParslas@test.com";
            testUser1.password = "Göta_Pärlås!123";
            testUser1.address = "Götavägen 35B";
            testUser1.postcode = "99999";
            testUser1.city = "Pärlstaden";
            testUsers.put("theSwede", testUser1);

            TestUser testUser2 = new TestUser();
            testUser2.firstName = "Alexander-Zachaeus";
            testUser2.lastName = "Wolfeschlegelsteinhausenbergerdorff";
            testUser2.email = System.currentTimeMillis() + "AZW@test.com";
            testUser2.password = "Alexander-Zachaeus_Wolfeschlegelsteinhausenbergerdorff!123";
            testUser2.address = "Strawberrycheesecakewithlotsofcherrysroad 57891234";
            testUser2.postcode = "55555";
            testUser2.city = "Llanfairpwllgwyngyllgogerychwyrndrobwllllantysiliogogogoch";
            testUsers.put("theLongie", testUser2);

            TestUser testUser3 = new TestUser();
            testUser3.firstName = "Ed";
            testUser3.lastName = "Pi";
            testUser3.email = System.currentTimeMillis() + "e@t.c";
            testUser3.password = "12345";
            testUser3.address = "Pi";
            testUser3.postcode = "10000";
            testUser3.city = "Ed";
            testUsers.put("theShortie", testUser3);

            return testUsers;

    }

}
