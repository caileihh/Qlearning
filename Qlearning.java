package Trial1;
import java.text.DecimalFormat;
import java.util.Arrays;
public class Qlearning {
    public static void main(String[] args) {
//        double[][] Q = new double[][] {
////                {-1,-1,0,-1,0,-1},
////                {-1,-1,-1,0,-1,0},
////                {0,-1,-1,0,-1,-1},
////                {-1,0,0,-1,0,-1},
////                {0,-1,-1,0,-1,0},
////                {-1,0,-1,-1,0,-1}
//
//                {0, 0, 0, 0,0,0},
//                {0, 0, 0, 0,0,0},
//                {0, 0, 0, 0,0,0},
//                {0, 0, 0, 0,0,0},
//                {0,0,0,0,0,0},
//                {0,0,0,0,0,0}
//        };

//        int[][] graph = new int[][] {
//                {-10,-10,-1,-10,-1,-10},
//                {-10,-10,-10,0,-10,0},
//                {-1,-10,-10,-1,-10,-10},
//                {-10,100,-1,-10,-1,-10},
//                {-1,-10,-10,10,-10,-1},
//                {-10,100,-10,-10,-1,-10}
//
//        };
//上为0 左1 下2 右3
        int[][] indexTable=new int[][]{
                {-1,-1,4,1},
                {-1,0,5,2},
                {-1,1,6,3},
                {-1,2,7,-1},
                {0,-1,8,5},
                {1,4,9,6},
                {2,5,10,7},
                {3,6,11,-1},
                {4,-1,12,9},
                {5,8,13,10},
                {6,9,14,11},
                {7,10,15,-1},
                {8,-1,-1,13},
                {9,12,-1,14},
                {10,13,-1,15},
                {11,14,-1,-1}
        };

        int [][]graph=new int[16][4];
        double [][]Q=new double[16][4];
        for(int []temp:graph)
            Arrays.fill(temp,-1);
        for(int j=0;j<16;j++)
            for(int i=0;i<4;i++)
            {
                if(indexTable[j][i]==-1)
                {
                    graph[j][i]=-100;
                    Q[j][i]=-100;
                }
            }
        graph[2][0]=-10;graph[2][1]=-10;
//        for(int []temp:graph)
//            System.out.println(Arrays.toString(temp));
        graph[15][3]=100;
            graph[0][0]=-10;


        double epsilon = 0.8;
        double alpha = 0.2;
        double gamma = 0.8;
        int EndNum=15;
        int MAX_EPISODES = 400; // 一般都通过设置最大迭代次数来控制训练轮数
        for(int episode = 0; episode < MAX_EPISODES; ++episode) {
            System.out.println("第"+episode+"轮训练...");
            int index = 0;
            while(index != EndNum) { // 到达目标状态，结束循环，进行下一轮训练
                int next;
                if(Math.random() < epsilon) next = max(Q[index]); // 通过 Q 表选择动作
                else next = randomNext(indexTable[index]); // 随机选择可行动作

                int reward =graph[index][next]; // 奖励
//                int reward=maxReward(graph[next]);
                Q[index][next] = (1-alpha)*Q[index][next] + alpha*(reward+gamma*maxNextQ(Q[indexTable[index][next]]));
//                Q[index][next] =reward+gamma*maxNextQ(Q[indexTable[index][next]]);
                index = indexTable[index][next]; // 更新状态
            }
        }
        int line=0;
        for(double []temp:Q)
        {
            System.out.println(Integer.toString(line)+":"+Arrays.toString(temp));
            line++;
        }
        int next=0;
        while (next!=EndNum)
        {
            System.out.print(Integer.toString(next)+"->");
            next=indexTable[next][getMaxFlag(Q[next])];
        }
        System.out.print(EndNum);
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
}
