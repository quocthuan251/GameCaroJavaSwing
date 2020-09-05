package main;

import java.awt.Point;

public class Process {
	// khai báo lưu thông tin sử dụng bộ nhớ
	 private static final long MEGABYTE = 1024L * 1024L;

	    public static long bytesToMegabytes(long bytes) {
	        return bytes / MEGABYTE;
	    }
	
	//
	private int matrix[][];
	private int[] goodPoint = { 1, 1 };
	private int dem = 0;
	private int win = 0;
//	private int[][] listScore = { { 9, 186, 842, 2308 }, { 1, 9, 85, 769 }, { 0, 0, 0, 0 } };
	private int[][] listScore = { { 4, 28, 436, 2508 }, { 1, 9, 85, 769 }, { 0, 0, 0, 0 } };
	private int alpha = 0;
	private int beta = 100000;
	
	public Process() {
		matrix = new int[Graphics.row + 2][Graphics.col + 2];
	}

	public boolean updateMatrix(boolean useCross, Point point) {
		// thời gian chạy:
	    long startTime = System.currentTimeMillis();
	    //
		int row = point.x + 1;
		int col = point.y + 1;
		short player = (short) (useCross ? 2 : 1);
		System.out.println("nguoi dang choi: " + player);
		if (matrix[row][col] == 0) {
			matrix[row][col] = player;
			dem++;
		} else {
			System.out.println("error");
			return false;
		}
		for (int i = 1; i < Graphics.row - 1; i++) {
			for (int j = 1; j < Graphics.col - 1; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
		win = checkWin(row, col);
//		attack(matrix, row, col);
//		defense(matrix, row, col);
//		System.out.println("-------------------------");
//		System.out.println("Heuristic: " +Heuristic(matrix, row, col));
		if(dem >3) {
		if (player == 2) {
			System.out.println("****Đang tính toán****");
			int test = minimax(3, matrix, true, row, col);
			javaMemoryMiniMax();
			javaTimeMiniMax(startTime);
			System.out.println("@@diem minimax: " + test);
			System.out.println("vị trí đánh tốt nhất: hàng: " + goodPoint[0] + " cột: " + goodPoint[1]);
			goodPoint[0] = 1;
			goodPoint[1] = 1;
			alpha =0;
			beta = 100000;

		}
		}
		return true;
	}

	private int Heuristic(int[][] board, int row, int col) {
		if (attack(board, row, col) >= defense(board, row, col)) {
			return attack(board, row, col);
		}
		return defense(board, row, col);
	}
	private int minimax(int depth, int[][] board, boolean minimax, int row1, int col1) {
		
		int result = 0;
		if (depth == 0) {
			return Heuristic(board, row1, col1);
		}
		// tính cho max
		if (minimax == true) {
			
			int temp = -1000;
			for (int i = 1; i < board.length - 1; i++) {
				for (int j = 1; j < board.length - 1; j++) {
					if (board[i][j] == 0) {
						int[][] newboard = copyBoard(board);
						newboard[i][j] = 1;
						int value = minimax(depth - 1, newboard, false, i, j);
						if (temp < value) {
							temp = value;
							goodPoint[0] = i;
							goodPoint[1] = j;
							alpha = value;
						}
						//beta first = 100 000
						if(alpha >= beta) {
							break;
						}
					}
				}
			}
			beta = temp;
			return temp;
		}
		// tính cho min
		if (minimax == false) {
			int temp = 100000;
			for (int i = 1; i < board.length - 1; i++) {
				for (int j = 1; j < board.length - 1; j++)
					if (board[i][j] == 0) {
						int[][] newboard = copyBoard(board);
						newboard[i][j] = 2;
						int value = minimax(depth - 1, newboard, true, i, j);
						if (temp > value) {
							temp = value;
							beta = value;
						}
						if(alpha >= beta) {
							break;
						}
						
					}
			}
			return temp;
		}
		return result;
	}

///////////////////////////////////////	
	private int checkWin(int row, int col) {

		int[][] rc = { { 0, -1, 0, 1 }, { -1, 0, 1, 0 }, { 1, -1, -1, 1 }, { -1, -1, 1, 1 } };
		int i = row, j = col;
		// duyệt tiếp 4 nút lân cận từ vị trí vừa đánh
		for (int direction = 0; direction < 4; direction++) {
			int count = 0;
			// in ra số lần duyệt, nút vừa đánh
			// System.out.println("[" + direction + "]-" + "[" + row + "," + col + "] ");

			i = row;
			j = col;
			while (i > 0 && i < matrix.length && j > 0 && j < matrix.length && matrix[i][j] == matrix[row][col]) {
				count++;
				if (count == 5) {
					return matrix[row][col];
				}
				// System.out.print("\t[" + i + "," + j + "] ");
				i += rc[direction][0];
				j += rc[direction][1];
				// System.out.println("--->[" + i + "," + j + "] ");
			}
			// System.out.println("\tcount1 : " + count);

			count--;
			i = row;
			j = col;
			while (i > 0 && i < matrix.length && j > 0 && j < matrix.length && matrix[i][j] == matrix[row][col]) {
				count++;
				if (count == 5) {
					return matrix[row][col];
				}
				// System.out.print("\t[" + i + "," + j + "] ");
				i += rc[direction][2];
				j += rc[direction][3];
				// System.out.println("--->[" + i + "," + j + "] ");
			}
			// System.out.println("\tcount : " + count);
		}

		return 0;
	}

	private int attack(int[][] board, int row, int col) {
		int result = 0;
		// ngang, doc, cheo /, chéo \
		int[][] rc = { { 0, -1, 0, 1 }, { -1, 0, 1, 0 }, { 1, -1, -1, 1 }, { -1, -1, 1, 1 } };
		int i = row, j = col;
		for (int direction = 0; direction < 4; direction++) {
			int score = 0;
			int count = 0;
			int dot = 0;
			i = row;
			j = col;
			while (i > 0 && i < board.length && j > 0 && j < board.length && board[i][j] == board[row][col]) {
				count++;
				if (count > 4) {
					//System.out.println("****điểm tấn công: 55555****");
					return 55555;
				}
				i += rc[direction][0];
				j += rc[direction][1];
				if (board[i][j] != board[row][col] && board[i][j] != 0) {
					dot++;
				}
			}
			count--;

			i = row;
			j = col;
			while (i > 0 && i < board.length && j > 0 && j < board.length && board[i][j] == board[row][col]) {
				count++;
				if (count > 4) {
					//System.out.println("****điểm tấn công: 55555****");
					return 55555;
				}
				// System.out.print("\t[" + i + "," + j + "] ");
				i += rc[direction][2];
				j += rc[direction][3];
				if (board[i][j] != board[row][col] && board[i][j] != 0) {
					dot++;
				}
			}
			score = listScore[dot][count - 1];
			result += score;
//			System.out.println("lần in:  "+ direction);
//			System.out.println("\tcount : " + count);
//			System.out.println("\tdot2 : " + dot);
//			System.out.println("\tddem : " + score);
		}
//		System.out.println("tổng điểm tấn công : " + result);
		return result;
	}

	private int defense(int[][] board, int row, int col) {
		int matrixCopy[][] = new int[board.length][board.length];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				matrixCopy[i][j] = board[i][j];
			}
		}
		int player = matrixCopy[row][col];
		if (player != 0) {
			if (player == 2) {
				matrixCopy[row][col] = 1;
			}
			if (player == 1) {
				matrixCopy[row][col] = 2;
			}
		}
		int result = 0;
		int[][] rc = { { 0, -1, 0, 1 }, { -1, 0, 1, 0 }, { 1, -1, -1, 1 }, { -1, -1, 1, 1 } };
		int i = row, j = col;
		for (int direction = 0; direction < 4; direction++) {
			int score = 0;
			int count = 0;
			int dot = 0;
			i = row;
			j = col;
			while (i > 0 && i < matrixCopy.length && j > 0 && j < matrixCopy.length
					&& matrixCopy[i][j] == matrixCopy[row][col]) {
				count++;
				if (count == 5) {
				//	System.out.println("****điểm phòng thủ: 16000****");
					return 16000;
				}
				i += rc[direction][0];
				j += rc[direction][1];
				if (matrixCopy[i][j] != matrixCopy[row][col] && matrixCopy[i][j] != 0) {
					dot++;
				}
			}
			count--;
			i = row;
			j = col;

			while (i > 0 && i < matrixCopy.length && j > 0 && j < matrixCopy.length
					&& matrixCopy[i][j] == matrixCopy[row][col]) {
				count++;
				if (count == 5) {
				//	System.out.println("****điểm phòng thủ: 16000****");
					return 16000;
				}
				// System.out.print("\t[" + i + "," + j + "] ");
				i += rc[direction][2];
				j += rc[direction][3];
				if (matrixCopy[i][j] != matrixCopy[row][col] && matrixCopy[i][j] != 0) {
					dot++;
				}
				// System.out.println("--->[" + i + "," + j + "] ");
			}
			score = listScore[dot][count - 1];
			result += score;

		}

//		System.out.println("tổng điểm phong thu : " + result);
		return result;
	}

	//////////
	public int[][] copyBoard(int[][] board) {
		int boardCopy[][] = new int[board.length][board.length];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				boardCopy[i][j] = board[i][j];
			}
		}
		return boardCopy;
	}

	public void printBoard(int[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}

	///
	protected void undoMatrix(Point point) {
		int row = point.x + 1;
		int col = point.y + 1;
		matrix[row][col] = 0;
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}
	// thống kê sử dụng bộ nhớ khi run minimax
	public void javaMemoryMiniMax() {
		Runtime runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Tính toán bộ nhớ đã sử dụng
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("số bytes bộ nhớ đã sử dụng: " + memory);
//        System.out.println("số megabytes bộ nhớ đã sử dụng: "
//                + bytesToMegabytes(memory));
	}
	// tính thời gian run minimax
	public void javaTimeMiniMax(long startTime) {
		long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("thời gian chạy: "+elapsedTime * 0.001 +" giây" );
	}

}
