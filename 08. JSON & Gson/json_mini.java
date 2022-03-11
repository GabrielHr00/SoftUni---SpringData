public class json_mini{

    public static void main(String[] args) {
        LoginData loginData = new LoginData("pencho", "1234");

        Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create();

        String result = gson.toJson(loginData);
        System.out.println(result);
    }

    static class LoginData{

        @Expose
        private String username;

        @Expose
        private String password;


        public LoginData(String username, String password){
            this.username = username;
            this.password = password;
        }
    }
}


// dependecies

// <dependency>
// <groupId>com.google.code.gson</groupId>
// <artifactId>gson</artifactId>
// </dependency>