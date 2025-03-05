import java.io.*;

public class AccountManager {
    private static final String FILE_NAME = "accounts.txt";

    public static Account loadAccount(String accountNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] accountData = line.split(",");
                if (accountData[0].equals(accountNumber)) {
                    return new Account(accountData[0], accountData[1], Double.parseDouble(accountData[2]));
                }
            }
        } catch (IOException e) {
        }
        return null;
    }

    public static void saveAccount(Account account) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(account.getAccountNumber() + "," + account.getPin() + "," + account.getBalance() + "\n");
        } catch (IOException e) {
        }
    }
}
