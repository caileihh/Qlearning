package demo1;


import java.util.ArrayList;
import java.util.Arrays;
public class Qlearning {
//    private static final int pointNum=12;
    private static int EndNum=99;
//    private static final int mapLength=10;
    public static final int n = 10;
    public static int [][]graph=new int[n*n][8];
    public static double [][]Q=new double[n*n][8];
    public static int[][] indexTable=new int[n*n][8];

    public static void main(String[] args) {
        ArrayList<Node> pointArrayList=new ArrayList<>();

        Maze maze=new Maze(n);
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++){
                Node temp=maze.getMaze()[i][j];
                pointArrayList.add(temp);
            }

        for(int []temp:indexTable)
            Arrays.fill(temp,-1);
        for(int i=0;i<pointArrayList.size();i++){
            Node curP=pointArrayList.get(i);
            int px=curP.getX(),py=curP.getY();

            if(px==0||py==0||px==n-1||py==n-1) {
                if(px==0) {
                    if(py>0&&py<n-1) {
                        int flagNum = 3;
                        for (int x = 1; x < 3; x++)
                            for (int y = 0; y < 3; y++) {
                                if (px + x - 1 == px && py + y - 1 == py) continue;
                                indexTable[i][flagNum] = pointArrayList.indexOf(maze.getMaze()[px + x - 1][py + y - 1]);
                                flagNum++;
                            }
                    }
                    else if(py==0){
                        indexTable[0][4]=n;
                        indexTable[0][6]=1;
                        indexTable[0][7]=n+1;
                    }
                    else{
                        indexTable[n*(n-1)][3]=n*(n-2);
                        indexTable[n*(n-1)][5]=n*(n-2)+1;
                        indexTable[n*(n-1)][6]=n*(n-1)+1;
                    }
                }
                if(px==n-1){
                    if(py>0&&py<n-1) {
                        int flagNum = 0;
                        for (int x = 0; x < 2; x++)
                            for (int y = 0; y < 3; y++) {
                                if (px + x - 1 == px && py + y - 1 == py) continue;
                                indexTable[i][flagNum] = pointArrayList.indexOf(maze.getMaze()[px + x - 1][py + y - 1]);
                                flagNum++;
                            }
                    }
                    else if(py==0){
                        indexTable[n-1][1]=n-2;
                        indexTable[n-1][2]=2*n-2;
                        indexTable[n-1][4]=2*n-1;
                    }
                    else{
                        indexTable[n*n-1][1]=n*n-2;
                        indexTable[n*n-1][0]=n*(n-1)-2;
                        indexTable[n*n-1][3]=n*(n-1)-1;
                    }
                }
                if(py==0){
                    if(px>0&&px<n-1) {
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
                if(py==n-1){
                    if(px>0&&px<n-1) {
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
            }


            else {
                int flagNum = 0;
                for (int x = 0; x < 3; x++)
                    for (int y = 0; y < 3; y++) {
                        if (px + x - 1 == px && py + y - 1 == py) continue;
                        indexTable[i][flagNum] = pointArrayList.indexOf(maze.getMaze()[px + x - 1][py + y - 1]);
                        flagNum++;
                    }
            }
        }
//上为0 左1 下2 右3  :n*4
        //0 3 5  :n*8
        //1   6
        //2 4 7
//        int[][] indexTable=new int[][]{
//                {-1,-1,-1,-1,3,-1,1,4},
//                {-1,0,3,-1,4,-1,2,5},
//                {-1,1,4,-1,5,-1,-1,-1},
//                {-1,-1,-1,0,6,1,4,7},
//                {0,3,6,1,7,2,5,8},
//                {1,4,7,2,8,-1,-1,-1},
//                {-1,-1,-1,3,9,4,7,10},
//                {3,6,9,4,10,5,8,11},
//                {4,7,10,5,11,-1,-1,-1},
//                {-1,-1,-1,6,-1,7,10,-1},
//                {6,9,-1,7,-1,8,11,-1},
//                {7,10,-1,8,-1,-1,-1,-1}
//        };


        for(int []temp:graph)
            Arrays.fill(temp,-1);
        for(int j=0;j<n*n;j++)
            for(int i=0;i<8;i++)
            {
                if(indexTable[j][i]==-1)
                {
                    graph[j][i]=-100;
                    Q[j][i]=-100;
                }
            }
//        graph[2][0]=-10;graph[2][1]=-10;

//        graph[78][6]=100;graph[77][7]=100;graph[87][4]=100;
        setReward(99,100);
        for(int []temp:graph)
            System.out.println(Arrays.toString(temp));
        System.out.println("IndexTable:");
        for(int []temp:indexTable)
            System.out.println(Arrays.toString(temp));
//        graph[0][7]=-20;graph[0][4]=-10;

        double epsilon = 0.8;
        double alpha = 0.2;
        double gamma = 0.8;

        int MAX_EPISODES = 40000; // 一般都通过设置最大迭代次数来控制训练轮数
        for(int episode = 0; episode < MAX_EPISODES; ++episode) {
            System.out.println("第"+episode+"轮训练...");
            int index = 11;
            while(index != EndNum) { // 到达目标状态，结束循环，进行下一轮训练
                int next;
                if(Math.random() < epsilon) next = max(Q[index]); // 通过 Q 表选择动作
                else next = randomNext(indexTable[index]); // 随机选择可行动作

                int reward =graph[index][next]; // 奖励
                Q[index][next] = (1-alpha)*Q[index][next] + alpha*(reward+gamma*maxNextQ(Q[indexTable[index][next]]));
//                Q[index][next] =reward+gamma*maxNextQ(Q[indexTable[index][next]]);
                index = indexTable[index][next]; // 更新状态
            }
        }
        int line=0;
        for(double []temp:Q)
        {
            System.out.println(line+":"+Arrays.toString(temp));
            line++;
        }
        int next=0;
        while (next!=EndNum)
        {
            System.out.print(next+"->");
            next=indexTable[next][getMaxFlag(Q[next])];
        }
        System.out.print(EndNum);
    }

    private static void setReward(int pNumber,int Reward){    //judge: 1:100   2:-100
        for(int i=0;i<n*n;i++)
            for(int j=0;j<8;j++)
            {
                if(indexTable[i][j]==pNumber)
                    graph[i][j]=Reward;
            }
    }

    private static int randomNext(int[] is) { // 蓄水池抽样，等概率选择流式数据
        int next = 0, n = 1;
        for(int i = 0; i < is.length; ++i) {
            if(is[i] >= 0 && Math.random() < 1.0/n++) next = i;
        }
        return next;
    }

    private static int max(double[] is) {
        int max = 0;
        for(int i = 1; i < is.length; ++i) {
            if(is[i] > is[max]) max = i;
        }
        return max;
    }

    private static double maxNextQ(double[] is) {
        double max = is[0];
        for(int i = 1; i < is.length; ++i) {
            if(is[i] > max) max = is[i];
        }
        return max;
    }

    private static int getMaxFlag(double[] matrix){
        int flag=0;
        for(int i=1;i<matrix.length;i++)
        {
            if(matrix[i]>matrix[flag]) flag=i;
        }
        return flag;
    }

    public static int getEndNum() {
        return EndNum;
    }

    public static void setEndNum(int endNum) {
        EndNum = endNum;
    }
}

