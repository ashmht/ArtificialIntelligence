package edu.usc.ai.csci561.cf;

public class GameBoard {
	private char gameState[][] = new char[6][7];
	private String currentPlayer;
	private int xA;
	private int xB;
	private int yA;
	private int yB;

	public int getxA() {
		return xA;
	}

	public void setxA(int xA) {
		this.xA = xA;
	}

	public int getxB() {
		return xB;
	}

	public void setxB(int xB) {
		this.xB = xB;
	}

	public int getyA() {
		return yA;
	}

	public void setyA(int yA) {
		this.yA = yA;
	}

	public int getyB() {
		return yB;
	}

	public void setyB(int yB) {
		this.yB = yB;
	}

	/**
	 * @return the gameState
	 */
	public char[][] getGameState() {
		return gameState;
	}

	/**
	 * @param gameState
	 *            the gameState to set
	 */
	public void setGameState(char gameState[][]) {
		char gs[][] = new char[7][6];
		for (int i = 0; i < 7; ++i)
			for (int j = 0; j < 6; ++j) {
				gs[i][j] = gameState[i][j];
			}
		this.gameState = gs;
	}

	public void setGameState(int i, int j, char player) {
		this.gameState[i][j] = player;
	}

	/**
	 * @return the currentPlayer
	 */
	public String getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * @param currentPlayer
	 *            the currentPlayer to set
	 */
	public void setCurrentPlayer(String currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public boolean playerWins(char player) {
		// Horizontal
		for (int i = 0; i < 7; ++i) {
			for (int j = 0; j < 3; ++j) {
				if (gameState[i][j] == player && gameState[i][j + 1] == player
						&& gameState[i][j + 2] == player
						&& gameState[i][j + 3] == player) {
					return true;
				}
			}
		}

		// Vertical

		for (int i = 0; i < 6; ++i) {
			for (int j = 0; j < 4; ++j) {
				if (gameState[j][i] == player && gameState[j + 1][i] == player
						&& gameState[j + 2][i] == player
						&& gameState[j + 3][i] == player) {
					return true;
				}
			}
		}

		// Left Diagonal
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 4; ++j) {
				if (gameState[j][i] == player
						&& gameState[j + 1][i + 1] == player
						&& gameState[j + 2][i + 2] == player
						&& gameState[j + 3][i + 3] == player) {
					return true;
				}
			}
		}

		// Right Diagonal

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 4; ++j) {
				if (gameState[j + 3][i] == player
						&& gameState[j + 2][i + 1] == player
						&& gameState[j + 1][i + 2] == player
						&& gameState[j][i + 3] == player) {
					return true;
				}
			}
		}

		return false;

	}

	public boolean playerLoses(char player) {

		if (playerWins('b'))
			return true;
		else
			return false;

	}

	public boolean isGameDraw() {

		if (!playerWins('a') && !playerLoses('a')) {
			for (int i = 0; i < 7; ++i) {
				for (int j = 0; j < 6; ++j) {
					if (gameState[i][j] == ' ') {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

	public void initXY(char player) {

		// Set Xa;
		// Horizontal

		this.setxA(setX('a'));

		this.setyA(setY('a'));

		this.setxB(setX('b'));

		this.setyB(setY('b'));

	}

	private int setX(char player) {
		int XaCount = 0;
		for (int i = 0; i < 7; ++i) {
			for (int j = 0; j < 5; ++j) {

				// If two consecutive players are found
				if (gameState[i][j] == player && gameState[i][j + 1] == player) {

					// j > 0 to avoid Array out of exception for j-1
					// Checking previous to them is a blank space
					if (j > 0 && gameState[i][j - 1] == ' ') {
						// j < 5 to avoid Array out of exception for j+2
						// Checking next to them is not a player this becomes Ya
						// condition
						if (j < 4 && gameState[i][j + 2] != player) {
							XaCount++;
						} else if (j >= 4)
							XaCount++;
					}
					if (j < 4 && gameState[i][j + 2] == ' ') {
						if (j > 0 && gameState[i][j - 1] != player) {
							XaCount++;
						} else if (j <= 0)
							XaCount++;
					}
				}
			}
		}

		// Vertical
		for (int i = 0; i < 6; ++i) {
			for (int j = 0; j < 6; ++j) {

				// If two consecutive players are found
				if (gameState[j][i] == player && gameState[j + 1][i] == player) {

					if (j > 0 && gameState[j - 1][i] == ' ') {
						if (j < 5 && gameState[j + 2][i] != player) {
							XaCount++;
						} else if (j >= 5)
							XaCount++;
					}
					if (j < 5 && gameState[j + 2][i] == ' ') {
						if (j > 0 && gameState[j - 1][i] != player) {
							XaCount++;
						} else if (j <= 0)
							XaCount++;
					}
				}
			}
		}

		// Right Diagonal

		for (int i = 0; i < 5; ++i) {
			for (int j = 0; j < 6; ++j) {

				// If two consecutive diagonal players are found
				if (gameState[j][i] == player
						&& gameState[j + 1][i + 1] == player) {

					if ((j > 0 && i > 0) && gameState[j - 1][i - 1] == ' ') {
						if ((j < 5 && i < 4)
								&& gameState[j + 2][i + 2] != player) {
							XaCount++;
						} else if (j >= 5 || i >= 4)
							XaCount++;
					}
					if ((j < 5 && i < 4) && gameState[j + 2][i + 2] == ' ') {
						if (j > 0 && i > 0 && gameState[j - 1][i - 1] != player) {
							XaCount++;
						} else if (j <= 0 || i <= 0)
							XaCount++;
					}
				}
			}
		}

		// Left Diagonal

		for (int i = 1; i < 6; ++i) {
			for (int j = 0; j < 6; ++j) {

				// If two consecutive diagonal players are found
				if (gameState[j][i] == player
						&& gameState[j + 1][i - 1] == player) {

					if ((j > 0 && i < 5) && gameState[j - 1][i + 1] == ' ') {
						if ((j < 5 && i > 1)
								&& gameState[j + 2][i - 2] != player) {
							XaCount++;
						} else if (j >= 5 || i <= 1)
							XaCount++;
					}
					if ((j < 5 && i > 1) && gameState[j + 2][i - 2] == ' ') {
						if ((j > 1 && i < 5)
								&& gameState[j - 1][i + 1] != player) {
							XaCount++;
						} else if (j <= 1 || i >= 5)
							XaCount++;
					}
				}
			}
		}
		return XaCount;
	}

	private int setY(char player) {
		int YaCount = 0;
		// Set Ya

		// Horizontal
		for (int i = 0; i < 7; ++i) {
			for (int j = 0; j < 4; ++j) {

				// If two consecutive players are found
				if (gameState[i][j] == player && gameState[i][j + 1] == player
						&& gameState[i][j + 2] == player) {

					if (j > 0 && gameState[i][j - 1] == ' ') {
						YaCount++;
					}
					if (j < 3 && gameState[i][j + 3] == ' ') {
						YaCount++;
					}
				}
			}
		}

		// Vertical

		for (int i = 0; i < 6; ++i) {
			for (int j = 0; j < 5; ++j) {

				// If two consecutive players are found
				if (gameState[j][i] == player && gameState[j + 1][i] == player
						&& gameState[j + 2][i] == player) {

					if (j > 0 && gameState[j - 1][i] == ' ') {
						YaCount++;
					}
					if (j < 4 && gameState[j + 3][i] == ' ') {
						YaCount++;
					}
				}
			}
		}

		// Left Diagonal

		for (int i = 0; i < 4; ++i) {
			for (int j = 0; j < 5; ++j) {

				// If two consecutive players are found
				if (gameState[j][i] == player
						&& gameState[j + 1][i + 1] == player
						&& gameState[j + 2][i + 2] == player) {

					if ((j > 0 && i > 0) && gameState[j - 1][i - 1] == ' ') {
						YaCount++;
					}
					if ((j < 4 && i < 3) && gameState[j + 3][i + 3] == ' ') {
						YaCount++;
					}
				}
			}
		}

		// Right Diagonal

		for (int i = 2; i < 6; ++i) {
			for (int j = 0; j < 5; ++j) {

				// If two consecutive players are found
				if (gameState[j][i] == player
						&& gameState[j + 1][i - 1] == player
						&& gameState[j + 2][i - 2] == player) {

					if ((j < 4 && i > 2) && gameState[j + 3][i - 3] == ' ') {
						YaCount++;
					}
					if ((j > 0 && i < 5) && gameState[j - 1][i + 1] == ' ') {
						YaCount++;
					}
				}
			}
		}

		return YaCount;
	}
}
