import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Section_2 {

    private static final Pattern PATTERN = Pattern.compile("(\\d+) (\\w+)");

    public static void main(String[] args) throws Exception {
        String filePath = "C:\\Users\\Fireb\\Downloads\\aoc_2\\C371\\src\\Day_2_2.txt";
        String input = readFileToString(filePath);

        int sum = Arrays.stream(input.split("\n"))
                .map(line -> line.split(":"))
                .mapToInt(arr -> {
                    int[] maxCounts = Arrays.stream(arr[1].split(";"))
                            .flatMap(rounds -> Arrays.stream(rounds.split(",")))
                            .map(PATTERN::matcher)
                            .filter(Matcher::find)
                            .collect(() -> new int[3], (acc, m) -> {
                                int quantity = Integer.parseInt(m.group(1));
                                String color = m.group(2);
                                switch (color) {
                                    case "blue":
                                        acc[0] = Math.max(acc[0], quantity);
                                        break;
                                    case "red":
                                        acc[1] = Math.max(acc[1], quantity);
                                        break;
                                    case "green":
                                        acc[2] = Math.max(acc[2], quantity);
                                        break;
                                }
                            }, (left, right) -> {
                                left[0] = Math.max(left[0], right[0]);
                                left[1] = Math.max(left[1], right[1]);
                                left[2] = Math.max(left[2], right[2]);
                            });

                    return maxCounts[0] * maxCounts[1] * maxCounts[2];
                })
                .sum();

        System.out.println("Total sum: " + sum);
    }

    private static String readFileToString(String filePath) throws Exception {
        Path path = Paths.get(filePath);
        byte[] fileBytes = Files.readAllBytes(path);
        return new String(fileBytes);
    }
}
