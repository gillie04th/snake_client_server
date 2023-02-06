import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginService {
    
    public static String login(ApiService apiService, HashMap data, ObjectMapper mapper){

        try {
            String res = apiService.post("/login", mapper.writeValueAsString(data));
            System.out.println(res);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    public static String logout(ApiService apiService, String data){
        return "";
    }
}
