package repository.json;

import com.google.gson.*;
import model.*;
import repository.iUserRepository;

import java.nio.file.*;
import java.util.*;
import java.nio.file.StandardOpenOption;

public class JsonUserRepository implements iUserRepository{

    private final Path filePath = Paths.get("data/users.json");
    private final Gson gson = new Gson();

    public JsonUserRepository(){
        fileCheck();
    }

    private void fileCheck(){

        try{
            Path parent = filePath.getParent();
            if(parent != null && Files.notExists(parent)){
                Files.createDirectories(parent);
            }
            if(Files.notExists(filePath)){
                Files.writeString(filePath, "[]", StandardOpenOption.CREATE);
            }
        }
        catch(Exception e){
            throw new RuntimeException("Failed to check file", e);
        }
    }
    @Override
    public List<User> findAll(){
        try{
            String json = Files.readString(filePath);
            JsonElement root = JsonParser.parseString(json);
            JsonArray jarray = root.getAsJsonArray();

            List<User> users = new ArrayList<>();

            for(JsonElement el : jarray){
                JsonObject jobject = el.getAsJsonObject();

                String username = jobject.get("username").getAsString();
                String hashedPassword = jobject.get("hashedPassword").getAsString();
                Role role = Role.valueOf(jobject.get("role").getAsString());

                if(role == Role.ADMIN){
                    users.add(new Admin(username, hashedPassword));
                    
                }
                else{
                    String userId = jobject.get("userId").getAsString();
                    long balance = jobject.has("balance") ? jobject.get("balance").getAsLong() : 0L;
                    users.add(new Customer(userId, username, hashedPassword,  balance));
                }
            }
            return users;

        }
        catch(Exception e){
            throw new RuntimeException("Failed to read users.jason", e);
        }
    }

    @Override
    public Optional<User> findByUsername(String username){
        return findAll().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public void save(User user){ // add or update user
        List<User> users = findAll();

        boolean updated = false;
        for(int i = 0 ; i < users.size(); i++){
            if(users.get(i).getUsername().equals(user.getUsername())){
            users.set(i, user);
            updated = true;
            break;

        }
    }
    if ((!updated)) {
        users.add(user);
    }
    saveAll(users);

}
@Override
public void deleteByUsername(String username){
    List<User> users = findAll();
    users.removeIf(u -> u.getUsername().equals(username));
    saveAll(users);
}

@Override
public void saveAll(List<User> users){
    try{
        JsonArray jArr = new JsonArray();

        for(User u : users){

            JsonObject jobj = new JsonObject();
            
            jobj.addProperty("username", u.getUsername());
            jobj.addProperty("hashedPassword", u.getHashedPasswordForRepo());
            jobj.addProperty("role", u.getRole().name());

            if(u instanceof Customer c){
                jobj.addProperty("userId", c.getUserId());
                jobj.addProperty("balance", c.getBalance());
            }
            jArr.add(jobj);
        }
        Files.writeString(filePath, gson.toJson(jArr), StandardOpenOption.TRUNCATE_EXISTING,
                            StandardOpenOption.CREATE);

    }catch (Exception e){
        throw new RuntimeException("Failed to write users.jason", e);
    }
}

}
