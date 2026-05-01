import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.*;
import java.util.stream.*;

/**
 * CODETECH SOLUTION INTERNSHIP TASK 1
 * 
 * FILE HANDLING UTILITY
 * A Java program demonstrating common file operations with clear documentation.
 * 
 * Features:
 * 1. File creation
 * 2. Reading files
 * 3. Writing/updating files
 * 4. Deleting files
 * 5. File information
 * 6. Directory operations
 * 7. File searching
 */
public class FileHandlingUtility {

    /**
     * Creates a new file with optional content
     * @param filePath Path of the file to create
     * @param content Optional content to write (can be null)
     * @return true if successful, false otherwise
     */
    public static boolean createFile(String filePath, String content) {
        try {
            File file = new File(filePath);
            if (file.createNewFile()) {
                if (content != null && !content.isEmpty()) {
                    Files.write(Paths.get(filePath), content.getBytes());
                }
                System.out.println("File created successfully: " + filePath);
                return true;
            } else {
                System.out.println("File already exists: " + filePath);
                return false;
            }
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Reads content from a file
     * @param filePath Path of the file to read
     * @return File content as String or null if error occurs
     */
    public static String readFile(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null;
        }
    }

    /**
     * Appends content to an existing file
     * @param filePath Path of the file to append to
     * @param content Content to append
     * @return true if successful, false otherwise
     */
    public static boolean appendToFile(String filePath, String content) {
        try {
            Files.write(Paths.get(filePath), content.getBytes(), StandardOpenOption.APPEND);
            System.out.println("Content appended to: " + filePath);
            return true;
        } catch (IOException e) {
            System.err.println("Error appending to file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes a file
     * @param filePath Path of the file to delete
     * @return true if successful, false otherwise
     */
    public static boolean deleteFile(String filePath) {
        try {
            boolean deleted = Files.deleteIfExists(Paths.get(filePath));
            if (deleted) {
                System.out.println("File deleted: " + filePath);
            } else {
                System.out.println("File not found: " + filePath);
            }
            return deleted;
        } catch (IOException e) {
            System.err.println("Error deleting file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets file information
     * @param filePath Path of the file to inspect
     * @return Map containing file information or null if error occurs
     */
    public static Map<String, Object> getFileInfo(String filePath) {
        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                return null;
            }

            BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
            Map<String, Object> fileInfo = new LinkedHashMap<>();
            fileInfo.put("path", path.toAbsolutePath().toString());
            fileInfo.put("size", attrs.size());
            fileInfo.put("lastModified", new Date(attrs.lastModifiedTime().toMillis()));
            fileInfo.put("creationTime", new Date(attrs.creationTime().toMillis()));
            fileInfo.put("isDirectory", attrs.isDirectory());
            fileInfo.put("isRegularFile", attrs.isRegularFile());
            
            return fileInfo;
        } catch (IOException e) {
            System.err.println("Error getting file info: " + e.getMessage());
            return null;
        }
    }

    /**
     * Creates a new directory
     * @param dirPath Path of the directory to create
     * @return true if successful, false otherwise
     */
    public static boolean createDirectory(String dirPath) {
        try {
            Files.createDirectories(Paths.get(dirPath));
            System.out.println("Directory created: " + dirPath);
            return true;
        } catch (IOException e) {
            System.err.println("Error creating directory: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lists contents of a directory
     * @param dirPath Path of the directory to list
     * @return List of file/directory names or null if error occurs
     */
    public static List<String> listDirectory(String dirPath) {
        try {
            return Files.list(Paths.get(dirPath))
                       .map(Path::getFileName)
                       .map(Path::toString)
                       .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error listing directory: " + e.getMessage());
            return null;
        }
    }

    /**
     * Searches for files matching criteria
     * @param directory Directory to search in
     * @param extension File extension to filter by (can be null)
     * @param contains String that must be in filename (can be null)
     * @return List of matching file paths
     */
    public static List<String> searchFiles(String directory, String extension, String contains) {
        try {
            return Files.walk(Paths.get(directory))
                       .filter(Files::isRegularFile)
                       .filter(path -> extension == null || path.toString().endsWith(extension))
                       .filter(path -> contains == null || path.getFileName().toString().contains(contains))
                       .map(Path::toString)
                       .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error searching files: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Copies a file
     * @param src Source file path
     * @param dest Destination file path
     * @return true if successful, false otherwise
     */
    public static boolean copyFile(String src, String dest) {
        try {
            Files.copy(Paths.get(src), Paths.get(dest), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied from " + src + " to " + dest);
            return true;
        } catch (IOException e) {
            System.err.println("Error copying file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Moves a file
     * @param src Source file path
     * @param dest Destination file path
     * @return true if successful, false otherwise
     */
    public static boolean moveFile(String src, String dest) {
        try {
            Files.move(Paths.get(src), Paths.get(dest), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File moved from " + src + " to " + dest);
            return true;
        } catch (IOException e) {
            System.err.println("Error moving file: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println("CODETECH SOLUTION INTERNSHIP TASK 1 FILE HANDLING UTILITY \nA Java program demonstrating common file operations with clear documentation.");
        
        // Create and write to a file
        String testFile = "Sample.txt";
        createFile(testFile, "Hi ! This is Aayesha Shaik\n");
        
        // Append to the file
        appendToFile(testFile, "A Software Engineer who is eager to learn new technology.\n");
        
        // Read the file
        String content = readFile(testFile);
        System.out.println("\nFile content:");
        System.out.println(content);
        
        // Get file info
        Map<String, Object> info = getFileInfo(testFile);
        System.out.println("\nFile information:");
        info.forEach((key, value) -> System.out.println(key + ": " + value));
        
        // Create a directory
        String testDir = "test_directory";
        createDirectory(testDir);
        
        // Copy file to new directory
        copyFile(testFile, testDir + "/example_copy.txt");
        
        // List directory contents
        System.out.println("\nDirectory contents:");
        listDirectory(testDir).forEach(System.out::println);
        
        // Search for files
        System.out.println("\nSearch results:");
        searchFiles(".", ".txt", null).forEach(System.out::println);
        
        // Clean up
        deleteFile(testFile);
        try {
            Files.walk(Paths.get(testDir))
                 .sorted(Comparator.reverseOrder())
                 .map(Path::toFile)
                 .forEach(File::delete);
            System.out.println("\nCleanup complete.");
        } catch (IOException e) {
            System.err.println("Error during cleanup: " + e.getMessage());
        }
    }
}