package Assesment;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ExcelPermutationGenerator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter base username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter base password: ");
        String basePassword = scanner.nextLine().trim();

        System.out.print("Enter number of combinations to generate: ");
        int limit = scanner.nextInt();

        // Generate permutations
        Set<String> usernamePerms = getPermutations(username.toLowerCase(), limit);
        Set<String> passwordPerms = getPermutations(basePassword, limit);

        // Make sure both sets have the same size
        List<String> emailList = new ArrayList<>();
        List<String> passwordList = new ArrayList<>();

        Iterator<String> emailIter = usernamePerms.iterator();
        Iterator<String> passIter = passwordPerms.iterator();

        for (int i = 0; i < limit && emailIter.hasNext() && passIter.hasNext(); i++) {
            emailList.add(emailIter.next() + "@gmail.com");
            passwordList.add(passIter.next());
        }

        // Output path
        String folderPath = "Resources";
        String filePath = folderPath + "/email_passwords.xlsx";

        File folder = new File(folderPath);
        if (!folder.exists()) folder.mkdirs();

        boolean success = writeToExcel(emailList, passwordList, filePath);

        if (success) {
            System.out.println("✅ Excel file created: " + filePath);
        } else {
            System.out.println("❌ Failed to write Excel file.");
        }

        scanner.close();
    }

    // Generate up to 'limit' unique permutations of a string
    public static Set<String> getPermutations(String input, int limit) {
        Set<String> result = new HashSet<>();
        permuteHelper("", input, result, limit);
        return result;
    }

    private static void permuteHelper(String prefix, String remaining, Set<String> result, int limit) {
        if (result.size() >= limit) return;

        int n = remaining.length();
        if (n == 0) {
            result.add(prefix);
        } else {
            for (int i = 0; i < n; i++) {
                permuteHelper(
                        prefix + remaining.charAt(i),
                        remaining.substring(0, i) + remaining.substring(i + 1),
                        result,
                        limit
                );
            }
        }
    }

    public static boolean writeToExcel(List<String> emails, List<String> passwords, String filePath) {
        if (emails.size() != passwords.size()) {
            System.out.println("Email and password count mismatch.");
            return false;
        }

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Credentials");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Email");
            header.createCell(1).setCellValue("Password");

            for (int i = 0; i < emails.size(); i++) {
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(emails.get(i));
                row.createCell(1).setCellValue(passwords.get(i));
            }

            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }

            return true;
        } catch (IOException e) {
            System.out.println("❌ Error writing Excel: " + e.getMessage());
            return false;
        }
    }
}
