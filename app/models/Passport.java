package models;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import enums.PassportTypeEnum;
import net.vz.mongodb.jackson.*;
import org.codehaus.jackson.annotate.JsonProperty;
import utils.DataUtil;

/**
 * User: Charles
 * Date: 5/4/13
 */

@MongoCollection(name = "passports")
public class Passport {

    private String id;

    @ObjectId
    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    @ObjectId
    @JsonProperty("_id")
    public void setId(String id) {
        this.id = id;
    }

    public String passportName;

    public String userId;

    public Integer childAge;

    public PassportTypeEnum type;

    public BasicDBObject cardsComplete;

    public Passport() {   }

    public Passport(String passportName, String userId, PassportTypeEnum type) {
        this.passportName = passportName;
        this.userId = userId;
        this.type = type;
    }

    public String getPassportName() {
        return passportName;
    }

    public void setPassportName(String passportName) {
        this.passportName = passportName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public PassportTypeEnum getType() {
        return type;
    }

    public void setType(PassportTypeEnum type) {
        this.type = type;
    }

    public Integer getChildAge() {
        return childAge;
    }

    public void setChildAge(Integer childAge) {
        this.childAge = childAge;
    }

    public static Boolean isPassportForUser(String userId) {
        try {
            JacksonDBCollection<Passport, String> collection = DataUtil.getCollection("passports", Passport.class);

            DBCursor cursorDoc = collection.find(DBQuery.is("userId", userId));

            return (cursorDoc.hasNext());
        } catch (MongoException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Boolean isPassportNameUniqueForUser(String userId, String passportName) {
        try {
            JacksonDBCollection<Passport, String> collection = DataUtil.getCollection("passports", Passport.class);

            DBCursor cursorDoc = collection.find(DBQuery.is("userId", userId));

            while(cursorDoc.hasNext()) {
                Passport passport = (Passport)cursorDoc.next();
                if(passport.getPassportName().toLowerCase().equals(passportName.toLowerCase())) {
                    return false;
                }
            }

            return true;
        } catch (MongoException e) {
            e.printStackTrace();
            return null;
        }
    }


}