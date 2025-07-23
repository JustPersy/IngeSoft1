import com.scalia.utils.ValidationUtils;

public class TestPasswordDebug {
    public static void main(String[] args) {
        String password = "123456";
        boolean result = ValidationUtils.isValidPassword(password);
        System.out.println("Password: '" + password + "'");
        System.out.println("Length: " + password.length());
        System.out.println("Result: " + result);
        System.out.println("password.length() >= 6: " + (password.length() >= 6));
        System.out.println("password == null: " + (password == null));
    }
}
