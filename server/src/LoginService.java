public class LoginService {
    
    public static String login(ApiService apiService, String data){
        apiService.get("/login", data);
        return "";
    }

    public static String logout(ApiService apiService, String data){
        return "";
    }
}
