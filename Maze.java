package demo1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Maze extends JPanel {
    private int Num, width, padding;
    private int LineWidth=1,Holewidth=3;
    public static final int rate = 332; //7:66*300       8:333*300     6:240*300      5:200*300    10:250*300   3:166*300   4.166*300
    public ArrayList<Node> BlockList=new ArrayList<>();
    public ArrayList<Node> Uar = new ArrayList<Node>(200);
    public int getCenterX(int x) {
        return padding + x * width + width / 2;
    }

    public int getCenterY(int y) {
        return padding + y * width + width / 2;
    }

    public Node[][][] getMaze() {
        return Maze;
    }

    public void setMaze(Node[][][] maze) {
        Maze = maze;
    }

    private Node[][][] Maze;

    public int getHolewidth() {
        return Holewidth;
    }

    public void setHolewidth(int holewidth) {
        Holewidth = holewidth;
    }

    public Maze(int n, int wid, int pad) {
        this.Num=n;this.width=wid;this.padding=pad;
        Maze = new Node[2][n][n];
        for (int i = 0; i < 200; i++)
            Uar.add(null);
        for(int k=0;k<2;k++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    Maze[k][i][j] = new Node(i, j, "0", 0, k);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int k=0;k<2;k++)
            for(int i=0;i<Num;i++)
                for(int j=0;j<Num;j++){
                    if(!getMaze()[k][i][j].nextNode.isEmpty()){
                        if (k == 0) g.setColor(new Color(255,0,99));
                        else g.setColor(new Color(72, 118, 255));
                        BasicStroke stokeLine = new BasicStroke(LineWidth * 2.0f);
                        Graphics2D g2 = (Graphics2D) g;
                        g2.setStroke(stokeLine);
                        Node cur=getMaze()[k][i][j];
                        for (int q = 0; q < cur.nextNode.size(); q++) {
                            g.drawLine(getCenterX(cur.getX()), getCenterY(cur.getY()),
                                    getCenterX(cur.nextNode.get(q).getX()), getCenterY(cur.nextNode.get(q).getY()));
                        }
                    }
                    if(getMaze()[k][i][j].isPin){
                        g.setColor(Color.YELLOW);
                        g.fillRect(getCenterX(i) - width / 4, getCenterY(j) - width / 4, width / 2, width / 2);
                    }
                    if (getMaze()[k][i][j].getFlag() == 3) {
                        g.setColor(Color.CYAN);
                        g.fillRect(getCenterX(i) - width / 4, getCenterY(j) - width / 4, width / 2, width / 2);
                    }
                    if(getMaze()[k][i][j].getFlag()==2){
                        g.setColor(Color.YELLOW);
                        Node cur = getMaze()[k][i][j];
                        g.fillRect(getCenterX(i) - width / 4, getCenterY(j)-width/4, width / 2, width / 2);
                    }
                    if (getMaze()[k][i][j].getFlag() == 5) {
                        g.setColor(new Color(255,162,99));
                        Node cur = getMaze()[k][i][j];
                        g.fillOval(getCenterX(cur.getX()) - width / 4, getCenterY(cur.getY())-width/4 , width/2, width/2);
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
