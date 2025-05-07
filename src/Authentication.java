import java.io.*;
import java.util.Scanner;

public class Authentication {
    private final String userDataPath = "datas/user_data.txt";

    private final String username;
    private final String password;

    public Authentication(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username;}
    public String getPassword() { return password;}

    public void login(String username, String password) {
        boolean userFound = checkUser(username, password);

        if (userFound){
            System.out.println("Login successful!");
        } else {
            System.out.println("Login failed!");
        }
    }

    public void register(String username, String password){
        boolean existedUsername = checkUsername(username);

        if (!existedUsername){
            try{
                FileWriter dataWriter = new FileWriter(userDataPath, true);
                dataWriter.write(username + " , " + password + "\n");
                dataWriter.close();
            } catch (IOException e) {
                System.out.println("Error occurred!");
            }
        } else {
            System.out.println("Username already exists!");
        }
    }

    public boolean checkUser(String username, String password){
        boolean userFound = false;

        try{
            File dataFile = new File(userDataPath);
            if (dataFile.exists()) {
                Scanner scanner = new Scanner(dataFile);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] user = line.split(" , ");
                    if (user[0].equalsIgnoreCase(username) && user[1].equalsIgnoreCase(password)) {
                        userFound = true;
                        break;
                    }
                }
            }
        } catch (IOException e){
            System.out.println("File not found!");
        }

        return userFound;
    }

    public boolean checkUsername(String username) {
        boolean existingUsername = false;

        try {
            File dataFile = new File(userDataPath);
            if (dataFile.exists()) {
                Scanner scanner = new Scanner(dataFile);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] user = line.split(" , ");
                    if (user[0].equalsIgnoreCase(username)) {
                        existingUsername = true;
                        break;
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error Occurred!");
        }

        return existingUsername;
    }
}
