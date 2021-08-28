package demo1;


import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Qlearning {

    private static int EndNum = 99;
    private static int StartNum = 0;
    public static final int mapLength = 150, high = 1000000,  mid = 10,width = 1500, padding = 20;
    public static int[][] graph = new int[mapLength * mapLength*2][10];
    public static double[][] Q = new double[mapLength * mapLength*2][10];
    public static int[][] indexTable = new int[mapLength * mapLength*2][10];
    public static ArrayList<Node> pointArrayList = new ArrayList<>();
    public static Maze maze = new Maze(mapLength,(width - padding - padding) / mapLength,padding);
    public static ArrayList<Integer> visitedPoint = new ArrayList<>();
    public static String id="8";
    static final int yuanx = 118125, yuany = 59000;//8:118345*59823    6:129565*71980   7:138125*97928    5:118700*88700   10:117*73    3:126*87    4.124*80
    public static final int MAX_EPISODES = 100000*4;
    public static ArrayList<Integer> nextList=new ArrayList<>();


    public static void main(String[] args) {

//        final int width = 1500, padding = 20, LX = 200, LY = 100;
//        Map map = new Map(mapLength, (width - padding - padding) / mapLength, padding);
        Long startTime = System.currentTimeMillis();
        Init();ResetGraph();
        read(maze);
//        ResetGraph();
//        for(int i=19;i<=26;i++)
//            for(int j=30;j<=36;j++)
//            {
//                setBlock(pointArrayList.indexOf(maze.getMaze()[0][i][j]));
//            }
//        setStartNum(getNum(0,10,10));setEndNum(getNum(0,19,30));RunQlearning();
//        ResetGraph();setStartNum(getNum(0,5,30));setEndNum(getNum(0,19,31));RunQlearning();
//        ResetGraph();setStartNum(getNum(0,5,35));setEndNum(getNum(0,19,32));RunQlearning();
//        ResetGraph();setStartNum(getNum(0,15,10));setEndNum(getNum(0,20,30));RunQlearning();
//        ResetGraph();setStartNum(getNum(0,15,10));setEndNum(getNum(0,21,30));RunQlearning();
//        ResetGraph();setStartNum(getNum(0,15,10));setEndNum(getNum(0,22,30));RunQlearning();
////        ResetGraph();setStartNum(getNum(15,10));setEndNum(getNum(23,30));RunQlearning();
////        ResetGraph();setStartNum(getNum(30,15));setEndNum(getNum(17,10));RunQlearning();
//        ResetGraph();setStartNum(getNum(0,30,40));setEndNum(getNum(0,26,35));RunQlearning();
//        ResetGraph();setStartNum(getNum(0,35,10));setEndNum(getNum(0,30,10));RunQlearning();
//        ResetGraph();setStartNum(getNum(0,35,15));setEndNum(getNum(0,30,15));RunQlearning();
//        ResetGraph();setStartNum(getNum(0,35,40));setEndNum(getNum(0,26,33));RunQlearning();
//        ResetGraph();setStartNum(getNum(0,45,20));setEndNum(getNum(0,35,15));RunQlearning();
//        ResetGraph();setStartNum(getNum(0,45,25));setEndNum(getNum(0,26,30));RunQlearning();
//        ResetGraph();setStartNum(getNum(0,45,30));setEndNum(getNum(0,26,32));RunQlearning();
//        ResetGraph();setStartNum(getNum(0,30,45));setEndNum(getNum(0,21,36));RunQlearning();
////        ResetGraph();setStartNum(getNum(35,45));setEndNum(getNum(30,45));RunQlearning();
//        ResetGraph();setStartNum(getNum(0,45,35));setEndNum(getNum(0,35,45));RunQlearning();
//        ResetGraph();setStartNum(getNum(0,30,10));setEndNum(getNum(0,20,36));RunQlearning();
//        ResetGraph();setStartNum(getNum(0,35,10));setEndNum(getNum(0,45,35));RunQlearning();



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

    private static int getNum(int k,int i,int j){
        return pointArrayList.indexOf(maze.getMaze()[k][i][j]);
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
        for(int k=0;k<2;k++)
            for (int i = 0; i < mapLength; i++)
                for (int j = 0; j < mapLength; j++) {
                    pointArrayList.add(maze.getMaze()[k][j][i]);
                }

        for (int[] temp : indexTable)
            Arrays.fill(temp, -1);

        for (int i = 0; i < pointArrayList.size(); i++) {
            Node curP = pointArrayList.get(i);
            int px = curP.getX(), py = curP.getY(),pk=curP.getBack();
            if (px == 0 || py == 0 || px == mapLength - 1 || py == mapLength - 1) {
                if (px == 0) {
                    if (py > 0 && py < mapLength - 1) {
                        int flagNum = 3;
                        for (int x = 1; x < 3; x++)
                            for (int y = 0; y < 3; y++) {
                                if (px + x - 1 == px && py + y - 1 == py) continue;
                                indexTable[i][flagNum] = pointArrayList.indexOf(maze.getMaze()[pk][px + x - 1][py + y - 1]);
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
                                indexTable[i][flagNum] = pointArrayList.indexOf(maze.getMaze()[pk][px + x - 1][py + y - 1]);
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
                                indexTable[i][flagNum] = pointArrayList.indexOf(maze.getMaze()[pk][px + x - 1][py + y - 1]);
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
                                indexTable[i][flagNum] = pointArrayList.indexOf(maze.getMaze()[pk][px + x - 1][py + y - 1]);
                                flagNum++;
                            }
                    }
                }
            } else {
                int flagNum = 0;
                for (int x = 0; x < 3; x++)
                    for (int y = 0; y < 3; y++) {
                        if (px + x - 1 == px && py + y - 1 == py) continue;
                        indexTable[i][flagNum] = pointArrayList.indexOf(maze.getMaze()[pk][px + x - 1][py + y - 1]);
                        flagNum++;
                    }
            }
            if(pk==0) indexTable[i][8]=pointArrayList.indexOf(maze.getMaze()[pk+1][px][py]);
            else indexTable[i][9]=pointArrayList.indexOf(maze.getMaze()[pk-1][px][py]);
        }
        //0 1 2  :n*8
        //3   4
        //5 6 7    ↑8  ↓9

        //层数：1
        //     0

    }

    private static void ResetGraph() {
        for (int[] temp : graph)
            Arrays.fill(temp, -1);
        for (int j = 0; j < pointArrayList.size(); j++)
            for (int i = 0; i < 10; i++) {
                if (indexTable[j][i] == -1) {
//                    if (i == 1 || i == 3 || i == 4 || i == 6) {
                        graph[j][i] = -high;
                        Q[j][i] = -high;
//                    } else {
//                        graph[j][i] = (int) (-high * 1.4);
//                        Q[j][i] = (int) (-high * 1.4);
//                    }
                } else {
                    if (i == 1 || i == 3 || i == 4 || i == 6) {
                        graph[j][i] = -mid;
                    }
                    else if(i==8||i==9) graph[j][i]=(int) (-mid*20);
                    else graph[j][i] = (int) (-mid * 1.1);
                }
            }
        updateCost();
    }

    private static void setBlock(int Number){
        pointArrayList.get(Number).setFlag(2);
        setReward(Number,-high);
    }

    private static void RunQlearning() {
        double epsilon = 0.5;
        double alpha = 0.5;
        double gamma = 0.9;
        System.out.println(getStartNum()+"-"+getEndNum());
//        System.out.println("running!");
        for (int episode = 0; episode < MAX_EPISODES; ++episode) {
//            System.out.println("第" + episode + "轮训练...");
            int index = StartNum;
            while (index != EndNum) { // 到达目标状态，结束循环，进行下一轮训练
                int next;
                if (Math.random() < epsilon) next = max(Q[index],index); // 通过 Q 表选择动作
                else next = randomNext(indexTable[index]); // 随机选择可行动作

                int reward = graph[index][next]; // 奖励
                Q[index][next] = (1 - alpha) * Q[index][next] + alpha * (reward + gamma * maxNextQ(Q[indexTable[index][next]],index)); //question!!!
//                Q[index][next] =reward+gamma*maxNextQ(Q[indexTable[index][next]],index);
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
            nextList.add(now);
            System.out.print(next + "->");
            next = indexTable[next][getMaxFlag(Q[next],next)]; // Question!!!
            visitedPoint.add(next);
            if(pointArrayList.get(now).getBack()!=pointArrayList.get(next).getBack()) pointArrayList.get(now).setFlag(5);
            pointArrayList.get(now).nextNode.add(pointArrayList.get(next));
        }
        nextList.clear();
        System.out.println(EndNum);
    }

    private static void updateCost() {
        for (int i = 0; i < pointArrayList.size(); i++) {
            int BlockNum = 0;
            int[] block = new int[8];
            for (int j = 0; j < 10; j++) {
                if (visitedPoint.contains(indexTable[i][j])) {
                    graph[i][j] = -high;
                    Q[i][j]=-high;
                    if (j == 1 || j == 3 || j == 4 || j == 6) {
                        BlockNum++;
                        block[j] = 1;
                    }
                }
            }
            if (BlockNum <= 1) {
            }
            else {
                if (block[1] == 1 && block[4] == 1) graph[i][2] = (int) (-high );
                if (block[1] == 1 && block[3] == 1) graph[i][0] = (int) (-high );
                if (block[3] == 1 && block[6] == 1) graph[i][5] = (int) (-high );
                if (block[4] == 1 && block[6] == 1) graph[i][7] = (int) (-high );
            }
        }
    }


    private static void setReward(int pNumber, int Reward) {
        for (int i = 0; i < mapLength * mapLength; i++)
            for (int j = 0; j < 10; j++) {
                if (indexTable[i][j] == pNumber) {
                    graph[i][j] = Reward;
                    Q[i][j]=Reward;
                }
            }
    }

    private static int randomNext(int[] is) { // 蓄水池抽样，等概率选择流式数据
        int next = 0, n = 1;
        for (int i = 0; i < is.length; ++i) {
            if (is[i] >= 0 && Math.random() < 1.0 / n++) next = i;
        }
        return next;
    }

    private static int max(double[] is,int index) {
        int max = 0;
        while (indexTable[index][max]==-1) max++;
        for (int i = 1; i < is.length; ++i) {
            if (is[i] > is[max]&&indexTable[index][i]!=-1) max = i;
        }
        return max;
    }

    private static double maxNextQ(double[] is,int index) {
        double max;
        int x=0;while (indexTable[index][x]==-1) x++;
        max=is[x];
        for (int i = 1; i < is.length; ++i) {
            if (is[i] > max&&indexTable[index][i]!=-1) max = is[i];
        }
        return max;
    }

    private static int getMaxFlag(double[] matrix,int next) {
        int flag = 0;
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i] > matrix[flag]&&!nextList.contains(indexTable[next][i])&&indexTable[next][i]!=-1) flag = i;
        }
        return flag;
    }

    public static int getEndNum() {
        return EndNum;
    }

    public static void setEndNum(int endNum) {
        EndNum = endNum;
        setReward(endNum, high*10);
        pointArrayList.get(endNum).setFlag(2);
    }

    public static int getStartNum() {
        return StartNum;
    }

    public static void setStartNum(int startNum) {
        StartNum = startNum;
        pointArrayList.get(startNum).setFlag(2);
    }

    public static void read(Maze maze) {
        List<String> list = readFile("C:\\Users\\cailei\\Documents\\WeChat Files\\QQ1115063309\\FileStorage\\File\\2021-05\\PCBBenchmarks-master\\PCBBenchmarks-master\\bm"+id+"\\bm"+id+".unrouted.dsn");
        String name = "";

        for (int i = 0; i < list.size(); i++) {
            String temp = list.get(i);
            System.out.println(temp);
            if (temp.matches("(.*)component(.*)")) name = temp.substring(temp.replaceAll("\"", "").indexOf(":") + 1);
//                name=temp.trim().replaceAll("\"","").substring(temp.indexOf(":")+1);
            try {
                if (temp.matches("(.*)place(.*)U(.*)")) {
                    int x = temp.indexOf("U");
                    int y = temp.indexOf("-");
//                    int a=0,b=0;
                    int uid = 0;
                    int a = Integer.parseInt(temp.substring(x + 3, x + 7).replaceAll("\\D+", "")) - 1400;
                    int b = Integer.parseInt(temp.substring(y + 1, y + 5).replaceAll("\\D+", "")) - 1000;
                    String[] str = temp.trim().split(" ");
                    for (int q = 0; q < str.length; q++) {
                        if (str[q].matches("U(.*)")) {
                            if (Character.isDigit(str[q].charAt(1)))
                                uid = Integer.parseInt(str[q].substring(1)) - 1;
                            else
                                uid = str[q].charAt(1);
                            q++;
                            a = (int) (Float.parseFloat(str[q]) - yuanx) / Maze.rate;
                        }
                        if (str[q].matches("\\-(.*)")) {
                            b = (int) (Float.parseFloat(str[q].replaceAll("\\-", "")) - yuany) / Maze.rate;
                            q += 2;
                            maze.getMaze()[0][a][b].setAngle(Integer.parseInt(str[q].replaceAll("\\)", "")));
                            break;
                        }
                    }
                    maze.Uar.set(uid, maze.getMaze()[0][a][b]);
                    maze.getMaze()[0][a][b].setName(name);
//                    for (int t1 = a - 1; t1 <= a + 1; t1++)
//                        for (int t2 = b - 1; t2 <= b + 1; t2++) {
//                            map.getMaze()[t1][t2][0].setReachValue(Maze.MaxValue);
//                            map.getMaze()[t1][t2][0].setName(name);
//                            map.getMaze()[t1][t2][0].setUid(temp.substring(x, x + 2));
//                            map.getMaze()[t1][t2][0].setMainx(a);
//                            map.getMaze()[t1][t2][0].setMainy(b);
//                        }
                }
                if (temp.matches("(.*)image (.*)")) {
                    name = temp.substring(temp.replaceAll("\"", "").indexOf(":") + 1);
                    temp = list.get(++i);
                    int maxx = 2;
                    int maxy = 2;
                    int mx = 0, my = 0;
                    while (!temp.matches("(.*)image (.*)") && !temp.matches("(.*) \\)(.*)")) {
                        String[] str = temp.trim().split(" ");
                        int cx = 0, cy = 0;
                        String n = "";
                        for (int c = 0; c < str.length; c++) {
                            if ((str[c].matches("(.*)\\_um") && !str[c + 1].matches("\\(rotate(.*)")) || str[c].matches("(.*)0\\)")) {
                                n = str[++c];
                                cx = (int) Float.parseFloat(str[++c]) / Maze.rate;
                                cy = (int) Float.parseFloat(str[++c].replaceAll("\\)", "")) / Maze.rate;
                                maxx = Math.max(maxx, cx);
                                maxy = Math.max(maxy, cy);
                                break;
                            }
                        }
                        while (maze.Uar.contains(null)) maze.Uar.remove(null);
                        for (int z = 0; z < maze.Uar.size(); z++) {
                            if (maze.Uar.get(z) != null && maze.Uar.get(z).getName().equals(name)) {
                                mx = maze.Uar.get(z).getX();
                                my = maze.Uar.get(z).getY();
                                if (maze.getMaze()[0][mx][my].getAngle() / 90 == 0) {
//                                    maze.getMaze()[0][mx + cx][my + cy].setReachValue(Maze.MaxValue);
                                    setReward(getNum(0,mx+cx,my+cy),-high);
                                    maze.getMaze()[0][mx + cx][my + cy].setPin(true);
                                    maze.Uar.get(z).Mpin.put(n, maze.getMaze()[0][mx + cx][my + cy]);
                                } else if (maze.getMaze()[0][mx][my].getAngle() / 90 == 1) {
//                                    maze.getMaze()[0][mx - cy][my + cx].setReachValue(Maze.MaxValue);
                                    setReward(getNum(0,mx-cy,my+cx),-high);
                                    maze.Uar.get(z).Mpin.put(n, maze.getMaze()[0][mx - cy][my + cx]);
                                    maze.getMaze()[0][mx - cy][my + cx].setPin(true);
                                } else if (maze.getMaze()[0][mx][my].getAngle() / 90 == 2) {
//                                    maze.getMaze()[0][mx - cx][my - cy].setReachValue(Maze.MaxValue);
                                    setReward(getNum(0,mx-cx,my-cy),-high);
                                    maze.getMaze()[0][mx - cx][my - cy].setPin(true);
                                    maze.Uar.get(z).Mpin.put(n, maze.getMaze()[0][mx - cx][my - cy]);
                                } else {
//                                    maze.getMaze()[0][mx + cy][my - cx].setReachValue(Maze.MaxValue);
                                    setReward(getNum(0,mx+cy,my-cx),-high);
                                    maze.getMaze()[0][mx + cy][my - cx].setPin(true);
                                    maze.Uar.get(z).Mpin.put(n, maze.getMaze()[0][mx + cy][my - cx]);
                                }
                            }
                        }
                        temp = list.get(++i);
                    }
                    for (int q = 0; q < maze.Uar.size(); q++)
                        if (maze.Uar.get(q).getName().equals(name)) {
                            mx = maze.Uar.get(q).getX();
                            my = maze.Uar.get(q).getY();
                            for (int y = -maxy; y <= maxy; y++) {
                                if (y == -maxy || y == maxy) {
                                    for (int x = -maxx; x <= maxx; x++)
                                        if (maze.getMaze()[0][mx][my].getAngle() / 90 == 0) {

//                                            setReward(getNum(0,mx+x,my+y),mid*6);
                                            maze.getMaze()[0][mx + x][my + y].setFlag(3);
                                        } else if (maze.getMaze()[0][mx][my].getAngle() / 90 == 1) {

//                                            setReward(getNum(0,mx-y,my+x),mid*6);
                                            maze.getMaze()[0][mx - y][my + x].setFlag(3);
                                        } else if (maze.getMaze()[0][mx][my].getAngle() / 90 == 2) {

//                                            setReward(getNum(0,mx-x,my-y),mid*6);
                                            maze.getMaze()[0][mx - x][my - y].setFlag(3);
                                        } else {

//                                            setReward(getNum(0,mx+y,my+x),mid*6);
                                            maze.getMaze()[0][mx + y][my - x].setFlag(3);
                                        }
                                } else {
                                    if (maze.getMaze()[0][mx][my].getAngle() / 90 == 0) {
//                                        setReward(getNum(0,mx+maxx,my+y),mid*6);
//                                        setReward(getNum(0,mx-maxx,my+y),mid*6);
                                        maze.getMaze()[0][mx + maxx][my + y].setFlag(3);
                                        maze.getMaze()[0][mx - maxx][my + y].setFlag(3);
                                    } else if (maze.getMaze()[0][mx][my].getAngle() / 90 == 1) {
//
//                                        setReward(getNum(0,mx-y,my+maxx),mid*6);
//                                        setReward(getNum(0,mx-y,my-maxx),mid*6);
                                        maze.getMaze()[0][mx - y][my + maxx].setFlag(3);
                                        maze.getMaze()[0][mx - y][my - maxx].setFlag(3);
                                    } else if (maze.getMaze()[0][mx][my].getAngle() / 90 == 2) {
//
//                                        setReward(getNum(0,mx-maxx,my-y),mid*6);
//                                        setReward(getNum(0,mx+maxx,my-y),mid*6);
                                        maze.getMaze()[0][mx - maxx][my - y].setFlag(3);
                                        maze.getMaze()[0][mx + maxx][my - y].setFlag(3);
                                    } else {
//
//                                        setReward(getNum(0,mx+y,my-maxx),mid*6);
//                                        setReward(getNum(0,mx+y,my+maxx),mid*6);
                                        maze.getMaze()[0][mx + y][my - maxx].setFlag(3);
                                        maze.getMaze()[0][mx + y][my + maxx].setFlag(3);
                                    }
                                }
                            }
                        }
                    i--;
                }
                if (temp.matches("(.*)\\(net (.*)")) {
                    i++;
                    temp = list.get(i);
                    while (list.get(i + 1).matches("(.*) U(.*)")) {
                        i++;
                        temp.concat(list.get(i));
                    }
                    temp = temp.replaceAll("\\(pins", "");
                    temp = temp.replaceAll("\\)", "");
                    String[] Sar = temp.trim().split(" ");
                    for (int j = 0; j < Sar.length - 1; ) {
                        String s1 = Sar[j];
//                        if (Character.isDigit(str[q].charAt(1)))
//                            uid = Integer.parseInt(str[q].substring(1)) - 1;
//                        else
//                            uid=str[q].charAt(1);
                        int a = 0;
                        if (Character.isDigit(s1.charAt(1)))
                            a = Integer.parseInt(s1.substring(1, s1.indexOf("-")).replaceAll("\\D+", ""));
                        else a=maze.Uar.size();
                        String b = s1.substring(s1.indexOf("-")+1).replaceAll("\\)", "");
                        ResetGraph();
                        int x1=maze.Uar.get(a - 1).Mpin.get(b).getX(),y1=maze.Uar.get(a - 1).Mpin.get(b).getY();
                        setStartNum(getNum(0,x1,y1));

//                        maze.setStartNode(maze.Uar.get(a - 1).Mpin.get(b));
                        s1 = Sar[++j];
                        if (Character.isDigit(s1.charAt(1)))
                            a = Integer.parseInt(s1.substring(1, s1.indexOf("-")).replaceAll("\\D+", ""));
                        else a=maze.Uar.size();
                        b = s1.substring(s1.indexOf("-")+1).replaceAll("\\)", "");
                        int x2=maze.Uar.get(a - 1).Mpin.get(b).getX(),y2=maze.Uar.get(a - 1).Mpin.get(b).getY();
                        setEndNum(getNum(0,x2,y2));

//                        maze.setEndNode(maze.Uar.get(a - 1).Mpin.get(b));
//                        maze.search(maze);
                        RunQlearning();
                    }
                }
            } catch (NumberFormatException | NullPointerException | /*StringIndexOutOfBoundsException| ArrayIndexOutOfBoundsException|*/IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }


    public static java.util.List<String> readFile(String filepath) {
        List<String> list = new ArrayList<String>();
        try {
            String encoding = "UTF-8";
            File file = new File(filepath);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
                BufferedReader br = new BufferedReader(read);
                String linetxt = null;
                while ((linetxt = br.readLine()) != null) list.add(linetxt);
                br.close();
                read.close();
            } else System.out.println("Can't find file!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}

