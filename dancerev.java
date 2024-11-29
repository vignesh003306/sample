import java.util.Scanner;

public class dancerev {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read number of instructions
        int m = Integer.parseInt(scanner.nextLine().trim());

        // Read dance instructions
        String[] instructions = new String[m];
        for (int i = 0; i < m; i++) {
            instructions[i] = scanner.nextLine().trim();
        }

        // Compute and print the minimum moves without a trailing newline
        System.out.print(minimumMoves(instructions));
    }

    public static int minimumMoves(String[] instruct) {
        // Directions array
        String[] directions = {"up", "down", "left", "right"};
        int s = instruct.length;

        // 3D DP array initialized to a large value (inf)
        int[][][] dp = new int[s + 1][4][4];
        for (int i = 0; i <= s; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    dp[i][j][k] = Integer.MAX_VALUE;
                }
            }
        }

        // Base case: No moves needed at the start
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                dp[0][i][j] = 0;
            }
        }

        // Fill the DP table
        for (int k = 1; k <= s; k++) {
            int instrIdx = findDirectionIndex(instruct[k - 1], directions);
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (dp[k - 1][i][j] != Integer.MAX_VALUE) {
                        // If one of the controllers matches the instruction, no move is needed
                        if (instrIdx == i || instrIdx == j) {
                            dp[k][i][j] = Math.min(dp[k][i][j], dp[k - 1][i][j]);
                        } else {
                            // Otherwise, one move is required to adjust either controller
                            dp[k][i][j] = Math.min(dp[k][i][j], dp[k - 1][instrIdx][j] + 1);
                            dp[k][i][j] = Math.min(dp[k][i][j], dp[k - 1][i][instrIdx] + 1);
                        }
                    }
                }
            }
        }

        // Find the minimum moves required
        int minimumMoves = Integer.MAX_VALUE;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                minimumMoves = Math.min(minimumMoves, dp[s][i][j]);
            }
        }

        return minimumMoves;
    }

    // Helper method to find index of the instruction in directions array
    public static int findDirectionIndex(String instruction, String[] directions) {
        for (int i = 0; i < directions.length; i++) {
            if (directions[i].equals(instruction)) {
                return i;
            }
        }
        return -1; // This shouldn't happen as instructions are guaranteed to match one of the directions
    }
}