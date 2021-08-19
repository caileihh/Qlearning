package demo1;

public class Maze {
    public Node[][] getMaze() {
        return Maze;
    }

    public void setMaze(Node[][] maze) {
        Maze = maze;
    }

    private Node[][] Maze;

    public Maze(int n) {
        Maze=new Node[n][n];
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
                Maze[i][j]=new Node(i,j,"0",0,0);
    }
}
