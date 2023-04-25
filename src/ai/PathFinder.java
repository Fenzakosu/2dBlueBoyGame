package ai;

import java.util.ArrayList;

import main.GamePanel;

public class PathFinder {

	GamePanel gp;
	Node node[][];
	ArrayList<Node> openList = new ArrayList<>();
	public ArrayList<Node> pathList = new ArrayList<>();
	Node startNode, goalNode, currentNode;
	boolean goalReached = false;
	int step = 0;

	public PathFinder(GamePanel gp) {
		this.gp = gp;
		instantiateNodes();
	}

	public void instantiateNodes() {
		node = new Node[gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];

		int col = 0;
		int row = 0;

		while (col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {

			node[col][row] = new Node(col, row);

			col++;
			if (col == gp.MAX_WORLD_COL) {
				col = 0;
				row++;
			}

		}
	}

	public void resetNodes() {

		int col = 0;
		int row = 0;

		while (col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {

			// RESET OPEN, CHECKED AND SOLID STATE
			node[col][row].open = false;
			node[col][row].checked = false;
			node[col][row].solid = false;

			col++;
			if (col == gp.MAX_WORLD_COL) {
				col = 0;
				row++;
			}

		}

		// RESET OTHER SETTINGS
		openList.clear();
		pathList.clear();
		goalReached = false;
		step = 0;

	}

	public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
		resetNodes();

		// SET START AND GOAL NODE
		startNode = node[startCol][startRow];
		currentNode = startNode;
		goalNode = node[goalCol][goalRow];
		openList.add(currentNode);

		int col = 0;
		int row = 0;

		while (col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {

			// SET SOLID NODE
			int tileNum = gp.tileM.mapTileNums[gp.currentMap][col][row];
			if (gp.tileM.tiles[tileNum].collision == true) {
				node[col][row].solid = true;
			}
			// CHECK INTERACTIVE TILES
			for (int i = 0; i < gp.iTiles[1].length; i++) {
				if (gp.iTiles[gp.currentMap][i] != null
						&& gp.iTiles[gp.currentMap][i].isDestructible == true) {
					int itCol = gp.iTiles[gp.currentMap][i].worldX / gp.TILE_SIZE;
					int itRow = gp.iTiles[gp.currentMap][i].worldY / gp.TILE_SIZE;
					node[itCol][itRow].solid = true;
				}
			}
			// SET COST
			getCost(node[col][row]);

			col++;
			if (col == gp.MAX_WORLD_COL) {
				col = 0;
				row++;
			}

		}

	}

	public void getCost(Node node) {

		// G COST
		int xDistance = Math.abs(node.col - startNode.col);
		int yDistance = Math.abs(node.row - startNode.row);
		node.gCost = xDistance + yDistance;
		// H COST
		xDistance = Math.abs(node.col - goalNode.col);
		yDistance = Math.abs(node.row - goalNode.row);
		node.hCost = xDistance + yDistance;
		// F COST
		node.fCost = node.gCost + node.hCost;
	}

	public boolean search() {

		while (goalReached == false && step < 500) {

			int col = currentNode.col;
			int row = currentNode.row;

			// CHECK THE CURRENT NODE
			currentNode.checked = true;
			openList.remove(currentNode);

			// Open the UP node
			if (row - 1 >= 0) {
				openNode(node[col][row - 1]);
			}
			// Open the LEFT node
			if (col - 1 >= 0) {
				openNode(node[col - 1][row]);
			}
			// Open the DOWN node
			if (row + 1 < gp.MAX_WORLD_ROW) {
				openNode(node[col][row + 1]);
			}
			// Open the RIGHT node
			if (col + 1 < gp.MAX_WORLD_COL) {
				openNode(node[col + 1][row]);
			}

			// FIND THE BEST NODE
			int bestNodeIndex = 0;
			int bestNodefCost = 999;

			for (int i = 0; i < openList.size(); i++) {

				// CHECK IF THIS NODE'S F COST IS BETTER
				if (openList.get(i).fCost < bestNodefCost) {
					bestNodeIndex = i;
					bestNodefCost = openList.get(i).fCost;

				}
				// IF F COST IS EQUAL, CHECK THE G COST
				if (openList.get(i).fCost == bestNodefCost) {
					if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
						bestNodeIndex = i;
					}
				}
			}

			// IF THERE IS NO NODE IN THE OPENLIST, END THE LOOP
			if (openList.size() == 0) {
				break;
			}

			// AFTER THE LOOP, openList[bestNodeIndex] IS THE NEXT STEP (=
			// currentNode)
			currentNode = openList.get(bestNodeIndex);
			if (currentNode == goalNode) {
				goalReached = true;
				trackThePath();
			}
			step++;
		}
		return goalReached;
	}

	public void openNode(Node node) {
		if (node.open == false && node.checked == false && node.solid == false) {
			node.open = true;
			node.parent = currentNode;
			openList.add(node);
		}
	}

	public void trackThePath() {
		Node current = goalNode;

		while (current != startNode) {
			// ALWAYS ADDING TO THE FIRST SLOT , SO THE LAST
			// ADDED NODE IS IN THE [0]
			pathList.add(0, current);
			current = current.parent;
		}

	}
}
