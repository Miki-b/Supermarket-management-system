package logAndReg;

import java.io.*;
import java.util.HashMap;

public class IDAndPasswords {
    private HashMap<String, String> adminCredentials = new HashMap<>();
    private HashMap<String, String> userCredentials = new HashMap<>();
    private static final String ADMIN_FILE = "src/files/admin_credentials.txt";
    private static final String USER_FILE = "src/files/user_credentials.txt";

    public IDAndPasswords() {
        initializeWithDefaults();
        loadCredentialsFromFile(ADMIN_FILE, adminCredentials);
        loadCredentialsFromFile(USER_FILE, userCredentials);
    }

    public HashMap<String, String> getAdminCredentials() {
        return adminCredentials;
    }

    public HashMap<String, String> getUserCredentials() {
        return userCredentials;
    }

    public void modifyAdminCredentials(String oldUsername, String newUsername, String newPassword) {
        if (adminCredentials.containsKey(oldUsername)) {
            adminCredentials.remove(oldUsername);
            adminCredentials.put(newUsername, newPassword);
            saveAdminCredentialsToFile();
        } else {
            System.out.println("Admin username not found.");
        }
    }

    public void modifyUserCredentials(String userId, String newUsername, String newPassword) {
        if (userCredentials.containsKey(userId)) {
            userCredentials.put(userId, newPassword);
            saveUserCredentialsToFile();
        } else {
            System.out.println("User ID not found.");
        }
    }

    public void saveAdminCredentialsToFile() {
        saveCredentialsToFile(ADMIN_FILE, adminCredentials);
    }

    public void saveUserCredentialsToFile() {
        saveCredentialsToFile(USER_FILE, userCredentials);
    }

    private void loadCredentialsFromFile(String filename, HashMap<String, String> credentials) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    credentials.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveCredentialsToFile(String filename, HashMap<String, String> credentials) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (HashMap.Entry<String, String> entry : credentials.entrySet()) {
                bw.write(entry.getKey() + ":" + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeWithDefaults() {
        initializeFileWithDefaults(ADMIN_FILE, adminCredentials, "admin", "admin");
        initializeFileWithDefaults(USER_FILE, userCredentials, "user", "user");
    }

    private void initializeFileWithDefaults(String filename, HashMap<String, String> credentials, String defaultUser, String defaultPass) {
        File file = new File(filename);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs(); // Create directories if they don't exist
                file.createNewFile();
                credentials.put(defaultUser, defaultPass);
                saveCredentialsToFile(filename, credentials);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
