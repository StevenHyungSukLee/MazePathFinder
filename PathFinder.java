/*
THIS CODE WAS MY OWN WORK , IT WAS WRITTEN WITHOUT CONSULTING ANY
SOURCES OUTSIDE OF THOSE APPROVED BY THE INSTRUCTOR. HyungSuk Lee
*/
/**
 * Starter code for the Maze path finder problem.
 */
import java.io.*;
import java.util.Scanner;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

/*
 * Recursive class to represent a position in a path
 */
class Position{
	public int i;     //row
	public int j;     //column
	public char val;  //1, 0, or 'X'
	
	// reference to the previous position (parent) that leads to this position on a path
	Position parent;
	
	Position(int x, int y, char v){
		i=x; j = y; val=v;
	}
	
	Position(int x, int y, char v, Position p){
		i=x; j = y; val=v;
		parent=p;
	}
	
}


public class PathFinder{
	public static void main(String[] args) throws IOException {
		if(args.length<1){
			System.err.println("***Usage: java PathFinder maze_file");
			System.exit(-1);
		}
		
		char [][] maze;
		maze = readMaze(args[0]);
		printMaze(maze);
		Position [] path = stackSearch(maze);
		System.out.println("stackSearch Solution:");
		printPath(path);
		printMaze(maze);
		
		char [][] maze2 = readMaze(args[0]);
		path = queueSearch(maze2);
		System.out.println("queueSearch Solution:");
		printPath(path);
		printMaze(maze2);
	}
	
	
	public static Position [] stackSearch(char [] [] maze){
		// todo: your path finding algorithm here using the stack to manage search list
		// your algorithm should mark the path in the maze, and return array of Position 
		// objects coressponding to path, or null if no path found
		Stack<Position> s = new Stack<Position>(); //create a new stack for storing the coordinate values of the right path
		Stack<Position> a = new Stack<Position>();
		int x = 0;//x indicates rows
		int y = 0;//y indicates columns
		char v = '0';
		Position par = null;
		ArrayDeque<Position> list = new ArrayDeque<>();
		Position curPosition = new Position(x,y,v,par);
		Position newPosition;
		Position start = new Position(0,0,'1', par);


		s.push(start);//start point which is (0,0)

		while(!s.isEmpty()){
	
			x = s.peek().i; //x and y would be the top element of the stack.
			y = s.peek().j; //So, x,y represents the current position's row and column
			curPosition = s.pop();
			list.push(curPosition);//push current position to the linked list

			if(x == maze.length-1 && y == maze[0].length-1){ //if row and column reaches to the exit, stop the loop and contruct the path 
				maze[x][y] = 'X'; //change the character of the exit to 'X' which represents the right path

				while(curPosition.i != 0 || curPosition.j != 0){//when row and column are (0,0) finished to backtracking
					curPosition.val = 'X';
					a.push(curPosition); //store the value of the path in another stack
					curPosition = curPosition.parent;//By using the 'parent', we can backtrack the right path from the exit to the entrance
				}
				curPosition.val = 'X';
				a.push(curPosition);//store the entrance value
			break;

			}else{//if we cannot reach the exit yet, exploring the next valid position and move to there
					//When we move to the next position, set parent to current position so that we can backtracking later

				maze[x][y] = 'v';//checking 'visited' sign so that we will not explore the same path
				
				//down
				if((x+1) <= maze.length-1 && y >=0 && maze[x+1][y] == '0'){
					
					newPosition = new Position(x+1,y,'0',curPosition);
					s.push(newPosition);
					
					
				}
				//right
				if(x >= 0 && (y+1) <= maze[0].length-1 && maze[x][y+1] == '0'){
					
					newPosition = new Position(x,y+1,'0',curPosition);
					s.push(newPosition);
					
				}
				//up
				if ((x-1)>=0 && y >=0 && maze[x-1][y] == '0'){
					newPosition = new Position(x-1,y,'0',curPosition);
					s.push(newPosition);
					
					
					
				}
				//left
				if(x>=0 && (y-1) >=0 && maze[x][y-1] == '0'){
					newPosition = new Position(x,y-1,'0',curPosition);
					s.push(newPosition);
					
					
					
				}
			}
		}

		if(s.isEmpty()){//if stack is empty, then there is no solution
			return null;
		}

		Position [] path = new Position[a.size()];
	
		for(int i = 0; i < path.length; i++){//storing the solved path to the array 'path' from stack		
			path[i] = a.pop();				
		}

		
		for(int i = 0; i < path.length; i++){//marking 'X' through the path
			maze[path[i].i][path[i].j] = 'X';
		}

		for(int i=0; i<maze.length;i++){//remove the visited sign so we can only show the right path in maze
			for(int j=0; j<maze[0].length;j++){
				if(maze[i][j] == 'v'){
					maze[i][j] = '0';
				}
			}
		}
		return path;
	}
	
	public static Position [] queueSearch(char [] [] maze){
		// todo: your path finding algorithm here using the queue to manage search list
		// your algorithm should mark the path in the maze, and return array of Position 
		// objects coressponding to path, or null if no path found

		//for queueSearch, simply replace push() and pop() to add() and remove().
		//Same algorithm with the stackSearch
		Queue<Position> s = new ArrayDeque<>(); //create a new stack for storing the coordinate values of the right path
		Queue<Position> a = new ArrayDeque<>();
		int x = 0;//x indicates rows
		int y = 0;//y indicates columns
		char v = '0';
		Position par = null;
		ArrayDeque<Position> list = new ArrayDeque<>();
		Position curPosition = new Position(x,y,v,par);
		Position newPosition;
		Position start = new Position(0,0,'1', par);


		s.add(start);//start point which is (0,0)

		while(!s.isEmpty()){
	
			x = s.peek().i;
			y = s.peek().j;
			curPosition = s.remove();
			list.push(curPosition);

			if(x == maze.length-1 && y == maze[0].length-1){
				maze[x][y] = 'X';
				while(curPosition.i != 0 || curPosition.j != 0){
					curPosition.val = 'X';
					a.add(curPosition);
					curPosition = curPosition.parent;
				}
				curPosition.val = 'X';
				a.add(curPosition);
				
			break;
			}else{

				maze[x][y] = 'v';

				if((x+1) <= maze.length-1 && y >=0 && maze[x+1][y] == '0'){
					
					newPosition = new Position(x+1,y,'0',curPosition);
					s.add(newPosition);
					
				}
				if(x >= 0 && (y+1) <= maze[0].length-1 && maze[x][y+1] == '0'){
					
					newPosition = new Position(x,y+1,'0',curPosition);
					s.add(newPosition);
					
				}
				if ((x-1)>=0 && y >=0 && maze[x-1][y] == '0'){
					newPosition = new Position(x-1,y,'0',curPosition);
					s.add(newPosition);
					
				}
				if(x>=0 && (y-1) >=0 && maze[x][y-1] == '0'){
					newPosition = new Position(x,y-1,'0',curPosition);
					s.add(newPosition);
					
				}
			}
		}

		if(s.isEmpty()){
			return null;
		}

		Position [] path = new Position[a.size()];
	
		for(int i = path.length-1; i >= 0; i--){		
			path[i] = a.remove();
					
		}

		
		for(int i = 0; i < path.length; i++){
			maze[path[i].i][path[i].j] = 'X';
		}

		for(int i=0; i<maze.length;i++){
			for(int j=0; j<maze[0].length;j++){
				if(maze[i][j] == 'v'){
					maze[i][j] = '0';
				}
			}
		}
		
	
		return path;
		
	}
	
	public static void printPath(Position [] path){
		// todo: print the path to the stdout
		if(path == null){ //When there is no solution, leave the 'visited' sign so that we can see we've explored all possible ways.
							//Print a sentence that "There is no solution"
			System.out.println("There is no solution.");
		}else{
			System.out.println("{");
			for (int k = 0; k < path.length;  k++) {
				
				System.out.println("["+ path[k].i +"]"+ "," +"["+ path[k].j +"]");
				}
		}
		System.out.println("}");
		}
		
	
	
	/**
	 * Reads maze file in format:
	 * N  -- size of maze
	 * 0 1 0 1 0 1 -- space-separated 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static char [][] readMaze(String filename) throws IOException{
		char [][] maze;
		Scanner scanner;
		try{
			scanner = new Scanner(new FileInputStream(filename));
		}
		catch(IOException ex){
			System.err.println("*** Invalid filename: " + filename);
			return null;
		}
		
		int N = scanner.nextInt();
		scanner.nextLine();
		maze = new char[N][N];
		int i=0;
		while(i < N && scanner.hasNext()){
			String line =  scanner.nextLine();
			String [] tokens = line.split("\\s+");
			int j = 0;
			for (; j< tokens.length; j++){
				maze[i][j] = tokens[j].charAt(0);
			}
			if(j!=N){
				System.err.println("*** Invalid line: " + i + " has wrong # columns: " + j);
				return null;
			}
			i++;
		}
		if(i!=N){
			System.err.println("*** Invalid file: has wrong number of rows: " + i);
			return null;
		}
		return maze;
	}
	
	public static void printMaze(char[][] maze){
		
		if(maze==null || maze[0] == null){
			System.err.println("*** Invalid maze array");
			return;
		}
		
		for(int i=0; i< maze.length; i++){
			for(int j = 0; j< maze[0].length; j++){
				System.out.print(maze[i][j] + " ");	
			}
			System.out.println();
		}
		
		System.out.println();
	}

}
