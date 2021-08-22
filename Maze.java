package demo1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Maze extends JPanel {
    private int Num, width, padding;
    private final int LineWidth=1;
    public ArrayList<Node> BlockList=new ArrayList<>();
    public int getCenterX(int x) {
        return padding + x * width + width / 2;
    }

    public int getCenterY(int y) {
        return padding + y * width + width / 2;
    }

    public Node[][] getMaze() {
        return Maze;
    }

    public void setMaze(Node[][] maze) {
        Maze = maze;
    }

    private Node[][] Maze;

    public Maze(int n,int wid,int pad) {
        this.Num=n;this.width=wid;this.padding=pad;
        Maze = new Node[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                Maze[i][j] = new Node(i, j, "0", 0, 0);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(int i=0;i<Num;i++)
            for(int j=0;j<Num;j++){
                if(!getMaze()[i][j].nextNode.isEmpty()){
                    g.setColor(Color.RED);
                    BasicStroke stokeLine = new BasicStroke(LineWidth * 2.0f);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setStroke(stokeLine);
                    Node cur=getMaze()[i][j];
                    for (int q = 0; q < cur.nextNode.size(); q++) {
                        g.drawLine(getCenterX(cur.getX()), getCenterY(cur.getY()),
                                getCenterX(cur.nextNode.get(q).getX()), getCenterY(cur.nextNode.get(q).getY()));
                    }
                }
                if(getMaze()[i][j].getFlag()==2){
                    g.setColor(Color.GRAY);
                    Node cur = getMaze()[i][j];
                    g.fillRect(getCenterX(cur.getX()) - width / 2, getCenterY(cur.getY()) - width / 2, width, width);
                }
            }
        g.setColor(Color.GRAY);
        for(int i=0;i<BlockList.size();i++){
            Node cur=BlockList.get(i);
            int x=cur.getX(),y=cur.getY();
            g.fillRect(getCenterX(x),getCenterY(y),width,width);
        }
    }
}
