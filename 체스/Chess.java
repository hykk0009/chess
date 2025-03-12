import java.util.Scanner;

public class Chess {
    private String[][] board = new String[8][8];
    private boolean isWhiteTurn = true;

    public Chess() {
        initializeBoard();
    }

    // 보드 초기화
    private void initializeBoard() {
        board[0] = new String[]{"R", "N", "B", "Q", "K", "B", "N", "R"};
        for (int i = 0; i < 8; i++) board[1][i] = "P";
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) board[i][j] = ".";
        }
        for (int i = 0; i < 8; i++) board[6][i] = "p";
        board[7] = new String[]{"r", "n", "b", "q", "k", "b", "n", "r"};
    }

    // 보드 출력
    public void printBoard() {
        for (int i = 0; i < 8; i++) {
            System.out.print(8 - i + " ");
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    // 체스 표기법(e2)을 배열 인덱스로 변환
    private int[] parsePosition(String pos) {
        int col = pos.charAt(0) - 'a'; // 'a' = 0, 'b' = 1, ..., 'h' = 7
        int row = 8 - (pos.charAt(1) - '0'); // '1' = 7, '2' = 6, ..., '8' = 0
        return new int[]{row, col};
    }

    // 기물 이동 처리
    private boolean movePiece(String start, String end) {
        int[] startPos = parsePosition(start);
        int[] endPos = parsePosition(end);
        int startRow = startPos[0], startCol = startPos[1];
        int endRow = endPos[0], endCol = endPos[1];

        // 보드 범위 체크
        if (startRow < 0 || startRow > 7 || startCol < 0 || startCol > 7 ||
                endRow < 0 || endRow > 7 || endCol < 0 || endCol > 7) {
            System.out.println("보드 범위를 벗어났습니다!");
            return false;
        }

        String piece = board[startRow][startCol];
        if (piece.equals(".")) {
            System.out.println("이동할 기물이 없습니다!");
            return false;
        }

        // 턴 체크 (소문자 = 백, 대문자 = 흑)
        boolean isWhitePiece = piece.equals(piece.toLowerCase());
        if (isWhiteTurn != isWhitePiece) {
            System.out.println("지금은 " + (isWhiteTurn ? "백" : "흑") + "의 차례입니다!");
            return false;
        }

        // 이동 수행
        board[endRow][endCol] = piece;
        board[startRow][startCol] = ".";
        return true;
    }

    // 게임 시작
    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printBoard();
            System.out.println((isWhiteTurn ? "White" : "Black") + "의 차례입니다. 이동을 입력하세요 (예: e2 e4):");
            String input = scanner.nextLine();
            String[] moves = input.split(" ");

            if (moves.length != 2) {
                System.out.println("잘못된 입력입니다! 예: e2 e4");
                continue;
            }

            String start = moves[0];
            String end = moves[1];
            if (movePiece(start, end)) {
                isWhiteTurn = !isWhiteTurn; // 턴 변경
            }
        }
    }

    public static void main(String[] args) {
        Chess game = new Chess();
        game.start();
    }
}