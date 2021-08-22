package demo1;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Qlearning {

    private static int EndNum = 99;
    private static int StartNum = 0;
    public static final int mapLength = 50, high = 100, low = 1, mid = 10,width = 1500, padding = 20;
    public static int[][] graph = new int[mapLength * mapLength][8];
    public static double[][] Q = new double[mapLength * mapLength][8];
    public static int[][] indexTable = new int[mapLength * mapLength][8];
    public static ArrayList<Node> pointArrayList = new ArrayList<>();
    public static Maze maze = new Maze(mapLength,(width - padding - padding) / mapLength,padding);
    public static ArrayList<Integer> visitedPoint = new ArrayList<>();


    public static void main(String[] args) {

//        final int width = 1500, padding = 20, LX = 200, LY = 100;
//        Map map = new Map(mapLength, (width - padding - padding) / mapLength, padding);
        Long startTime = System.currentTimeMillis();
        Init();
        ResetGraph();

//        setStartNum(getNum(0,4));setEndNum(getNum(30,34));RunQlearning();
//        ResetGraph();setStartNum(getNum(4,4));setEndNum(getNum(0,0));RunQlearning();


        for(int i=19;i<=26;i++)
            for(int j=30;j<=36;j++)
            {
                setBlock(pointArrayList.indexOf(maze.getMaze()[i][j]));
            }
        setStartNum(getNum(10,10));setEndNum(getNum(19,30));RunQlearning();
        ResetGraph();setStartNum(getNum(5,30));setEndNum(getNum(19,31));RunQlearning();
        ResetGraph();setStartNum(getNum(5,35));setEndNum(getNum(19,32));RunQlearning();
        ResetGraph();setStartNum(getNum(15,10));setEndNum(getNum(20,30));RunQlearning();
        ResetGraph();setStartNum(getNum(15,10));setEndNum(getNum(21,30));RunQlearning();
        ResetGraph();setStartNum(getNum(15,10));setEndNum(getNum(22,30));RunQlearning();
//        ResetGraph();setStartNum(getNum(15,10));setEndNum(getNum(23,30));RunQlearning();
//        ResetGraph();setStartNum(getNum(30,15));setEndNum(getNum(17,10));RunQlearning();
        ResetGraph();setStartNum(getNum(30,40));setEndNum(getNum(26,35));RunQlearning();
        ResetGraph();setStartNum(getNum(35,10));setEndNum(getNum(30,10));RunQlearning();
        ResetGraph();setStartNum(getNum(35,15));setEndNum(getNum(30,15));RunQlearning();
        ResetGraph();setStartNum(getNum(35,40));setEndNum(getNum(26,33));RunQlearning();
        ResetGraph();setStartNum(getNum(45,20));setEndNum(getNum(35,15));RunQlearning();
        ResetGraph();setStartNum(getNum(45,25));setEndNum(getNum(26,30));RunQlearning();
        ResetGraph();setStartNum(getNum(45,30));setEndNum(getNum(26,32));RunQlearning();
        ResetGraph();setStartNum(getNum(30,45));setEndNum(getNum(21,36));RunQlearning();
//        ResetGraph();setStartNum(getNum(35,45));setEndNum(getNum(30,45));RunQlearning();
        ResetGraph();setStartNum(getNum(45,35));setEndNum(getNum(35,45));RunQlearning();
        ResetGraph();setStartNum(getNum(30,10));setEndNum(getNum(20,36));RunQlearning();
//        ResetGraph();setStartNum(getNum(35,10));setEndNum(getNum(45,35));RunQlearning();



        Long endTime = System.currentTimeMillis();
        double time=((double)(endTime - startTime))/1000;
        System.out.println("花费时间" + (time) + "s");
        Draw();
//        int line = 0;
//        for (int[] temp : graph) {
//            System.out.println(line + ":" + Arrays.toString(temp));
//            line++;
//        }

    }

    private static int getNum(int i,int j){
        return pointArrayList.indexOf(maze.getMaze()[i][j]);
    }

    private static void Draw(){
        JPanel p = maze;
        p.setBackground(Color.BLACK);
        p.setBounds(0, 0, 3000, 3000);
        p.setPreferredSize(new Dimension(3000, 3000));
        JFrame frame = new JFrame("Qlearning");
        JScrollPane jScrollPane = new JScrollPane(p);
        jScrollPane.setBounds(13, 10, 350, 450);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        frame.getContentPane().add(jScrollPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, width + padding);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }


    private static void Init() {
        for (int i = 0; i < mapLength; i++)
            for (int j = 0; j < mapLength; j++) {
                Node temp = maze.getMaze()[j][i];
                pointArrayList.add(temp);
            }

        for (int[] temp : indexTable)
            Arrays.fill(temp, -low);

        for (int i = 0; i < pointArrayList.size(); i++) {
            Node curP = pointArrayList.get(i);
            int px = curP.getX(), py = curP.getY();
            if (px == 0 || py == 0 || px == mapLength - 1 || py == mapLength - 1) {
                if (px == 0) {
                    if (py > 0 && py < mapLength - 1) {
                        int flagNum = 3;
                        for (int x = 1; x < 3; x++)
                            for (int y = 0; y < 3; y++) {
                                if (px + x - 1 == px && py + y - 1 == py) continue;
                                indexTable[i][flagNum] = pointArrayList.indexOf(maze.getMaze()[px + x - 1][py + y - 1]);
                                flagNum++;
                            }
                    } else if (py == 0) {
                        indexTable[0][4] = mapLength;
                        indexTable[0][6] = 1;
                        indexTable[0][7] = mapLength + 1;
                    } else {
                        indexTable[mapLength * (mapLength - 1)][3] = mapLength * (mapLength - 2);
                        indexTable[mapLength * (mapLength - 1)][5] = mapLength * (mapLength - 2) + 1;
                        indexTable[mapLength * (mapLength - 1)][6] = mapLength * (mapLength - 1) + 1;
                    }
                }
                if (px == mapLength - 1) {
                    if (py > 0 && py < mapLength - 1) {
                        int flagNum = 0;
                        for (int x = 0; x < 2; x++)
                            for (int y = 0; y < 3; y++) {
                                if (px + x - 1 == px && py + y - 1 == py) continue;
                                indexTable[i][flagNum] = pointArrayList.indexOf(maze.getMaze()[px + x - 1][py + y - 1]);
                                flagNum++;
                            }
                    } else if (py == 0) {
                        indexTable[mapLength - 1][1] = mapLength - 2;
                        indexTable[mapLength - 1][2] = 2 * mapLength - 2;
                        indexTable[mapLength - 1][4] = 2 * mapLength - 1;
                    } else {
                        indexTable[mapLength * mapLength - 1][1] = mapLength * mapLength - 2;
                        indexTable[mapLength * mapLength - 1][0] = mapLength * (mapLength - 1) - 2;
                        indexTable[mapLength * mapLength - 1][3] = mapLength * (mapLength - 1) - 1;
                    }
                }
                if (py == 0) {
                    if (px > 0 && px < mapLength - 1) {
                        int flagNum = 0;
                        for (int x = 0; x < 3; x++)
                            for (int y = 1; y < 3; y++) {
                                while (flagNum == 0 || flagNum == 3 || flagNum == 5) flagNum++;
                                if (px + x - 1 == px && py + y - 1 == py) continue;
                                indexTable[i][flagNum] = pointArrayList.indexOf(maze.getMaze()[px + x - 1][py + y - 1]);
                                flagNum++;
                            }
                    }
                }
                if (py == mapLength - 1) {
                    if (px > 0 && px < mapLength - 1) {
                        int flagNum = 0;
                        for (int x = 0; x < 3; x++)
                            for (int y = 0; y < 2; y++) {
                                while (flagNum == 2 || flagNum == 4 || flagNum == 7) flagNum++;
                                if (px + x - 1 == px && py + y - 1 == py) continue;
                                indexTable[i][flagNum] = pointArrayList.indexOf(maze.getMaze()[px + x - 1][py + y - 1]);
                                flagNum++;
                            }
                    }
                }
            } else {
                int flagNum = 0;
                for (int x = 0; x < 3; x++)
                    for (int y = 0; y < 3; y++) {
                        if (px + x - 1 == px && py + y - 1 == py) continue;
                        indexTable[i][flagNum] = pointArrayList.indexOf(maze.getMaze()[px + x - 1][py + y - 1]);
                        flagNum++;
                    }
            }
        }
        //0 1 2  :n*8
        //3   4
        //5 6 7


    }

    private static void ResetGraph() {
        for (int[] temp : graph)
            Arrays.fill(temp, -1);
        for (int j = 0; j < mapLength * mapLength; j++)
            for (int i = 0; i < 8; i++) {
                if (indexTable[j][i] == -1) {
                    if (i == 1 || i == 3 || i == 4 || i == 6) {
                        graph[j][i] = -high;
                        Q[j][i] = -high;
                    } else {
                        graph[j][i] = (int) (-high * 1.4);
                        Q[j][i] = (int) (-high * 1.4);
                    }
                } else {
                    if (i == 1 || i == 3 || i == 4 || i == 6) {
                        graph[j][i] = -mid;
                    } else graph[j][i] = (int) (-mid * 1.2);
                }
            }
        updateCost();
    }

    private static void setBlock(int Number){
        pointArrayList.get(Number).setFlag(2);
        setReward(Number,-high);
    }

    private static void RunQlearning() {
        double epsilon = 0.8;
        double alpha = 0.2;
        double gamma = 0.9;
        int MAX_EPISODES = 100000; // 一般都通过设置最大迭代次数来控制训练轮数
        System.out.println(getStartNum()+"-"+getEndNum());
        for (int episode = 0; episode < MAX_EPISODES; ++episode) {
//            System.out.println("第" + episode + "轮训练...");
            int index = StartNum;
            while (index != EndNum) { // 到达目标状态，结束循环，进行下一轮训练
                int next;
                if (Math.random() < epsilon) next = max(Q[index]); // 通过 Q 表选择动作
                else next = randomNext(indexTable[index]); // 随机选择可行动作

                int reward = graph[index][next]; // 奖励
                Q[index][next] = (1 - alpha) * Q[index][next] + alpha * (reward + gamma * maxNextQ(Q[indexTable[index][next]]));
//                Q[index][next] =reward+gamma*maxNextQ(Q[indexTable[index][next]]);
                index = indexTable[index][next]; // 更新状态
            }
        }
//        int line = 0;
//        for (double[] temp : Q) {
//            System.out.println(line + ":" + Arrays.toString(temp));
//            line++;
//        }
        int next = StartNum;
        visitedPoint.add(next);
        while (next != EndNum) {
            int now=next;
            System.out.print(next + "->");
            next = indexTable[next][getMaxFlag(Q[next])];
            visitedPoint.add(next);
            pointArrayList.get(now).nextNode.add(pointArrayList.get(next));
        }
        System.out.println(EndNum);
    }

    private static void updateCost() {
        for (int i = 0; i < mapLength * mapLength; i++) {
            int BlockNum = 0, block[] = new int[8];
            for (int j = 0; j < 8; j++) {
                if (visitedPoint.contains(indexTable[i][j])) {
                    graph[i][j] = -high;
                    if (j == 1 || j == 3 || j == 4 || j == 6) {
                        BlockNum++;
                        block[j] = 1;
                    }
                }
            }
            if (BlockNum <= 1) continue;
            else {
                if (block[1] == 1 && block[4] == 1) graph[i][2] = (int) (-high * 1.4);
                if (block[1] == 1 && block[3] == 1) graph[i][0] = (int) (-high * 1.4);
                if (block[3] == 1 && block[6] == 1) graph[i][5] = (int) (-high * 1.4);
                if (block[4] == 1 && block[6] == 1) graph[i][7] = (int) (-high * 1.4);
            }
        }
    }


    private static void setReward(int pNumber, int Reward) {
        for (int i = 0; i < mapLength * mapLength; i++)
            for (int j = 0; j < 8; j++) {
                if (indexTable[i][j] == pNumber)
                    graph[i][j] = Reward;
            }
    }

    private static int randomNext(int[] is) { // 蓄水池抽样，等概率选择流式数据
        int next = 0, n = 1;
        for (int i = 0; i < is.length; ++i) {
            if (is[i] >= 0 && Math.random() < 1.0 / n++) next = i;
        }
        return next;
    }

    private static int max(double[] is) {
        int max = 0;
        for (int i = 1; i < is.length; ++i) {
            if (is[i] > is[max]) max = i;
        }
        return max;
    }

    private static double maxNextQ(double[] is) {
        double max = is[0];
        for (int i = 1; i < is.length; ++i) {
            if (is[i] > max) max = is[i];
        }
        return max;
    }

    private static int getMaxFlag(double[] matrix) {
        int flag = 0;
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i] > matrix[flag]) flag = i;
        }
        return flag;
    }

    public static int getEndNum() {
        return EndNum;
    }

    public static void setEndNum(int endNum) {
        EndNum = endNum;
        setReward(endNum, high);
        pointArrayList.get(endNum).setFlag(2);
    }

    public static int getStartNum() {
        return StartNum;
    }

    public static void setStartNum(int startNum) {
        StartNum = startNum;
        pointArrayList.get(startNum).setFlag(2);
    }
}

