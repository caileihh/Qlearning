package demo1;

import java.util.ArrayList;
import java.util.HashMap;

public class Node {
    private int x;
    private int y;
    private String value;
    private double FValue=0;
    private double GValue=0;
    private double HValue=0;
    private double ReachValue=0;
//    private boolean Reachable=false;
    private Node PNode;
    private int back=0;
    private String name=new String();
    private String Uid;
    public ArrayList<Node> pin=new ArrayList<Node>();
    public HashMap<String,Node> Mpin= new HashMap<String,Node>();
    public ArrayList<Node> nextNode=new ArrayList<Node>();
    private int mainx,mainy;
    boolean isPin=false;//判断pin是否遍历过
    boolean Visited=false;
    private int angle=0;
    private int flag=0;//1:U    2:pin    3:边框    4.line    5.交叉点

    public int getBack() {
        return back;
    }

    public void setBack(int back) {
        this.back = back;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public boolean isVisited() {
        return Visited;
    }

    public void setVisited(boolean visited) {
        Visited = visited;
    }

    public boolean isPin() {
        return isPin;
    }

    public void setPin(boolean pin) {
        isPin = pin;
    }

    public int getMainy() {
        return mainy;
    }

    public void setMainy(int mainy) {
        this.mainy = mainy;
    }

    public int getMainx() {
        return mainx;
    }

    public void setMainx(int mainx) {
        this.mainx = mainx;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



//    public boolean isReachable() {
//        return Reachable;
//    }
//
//    public void setReachable(boolean reachable) {
//        Reachable = reachable;
//    }

//    public Node getNextNode() {
//        return NextNode;
//    }
//
//    public void setNextNode(Node nextNode) {
//        NextNode = nextNode;
//    }

    private boolean IsStart=false;

    public boolean isStart() {
        return IsStart;
    }

    public void setStart(boolean reachable) {
        IsStart = reachable;
    }

    public Node(int x, int y, String value, int reachValue,int back){
        super();
        this.x=x;this.y=y;this.value=value;ReachValue=reachValue;
    }
//    public Node(){super();}

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public double getFValue() {
        return FValue;
    }

    public void setFValue(double FValue) {
        this.FValue = FValue;
    }

    public double getGValue() {
        return GValue;
    }

    public void setGValue(double GValue) {
        this.GValue = GValue;
    }

    public double getHValue() {
        return HValue;
    }

    public void setHValue(double HValue) {
        this.HValue = HValue;
    }

    public double getReachValue() {
        return ReachValue;
    }

    public void setReachValue(double reachValue) {
        ReachValue = reachValue;
    }

    public Node getPNode() {
        return PNode;
    }

    public void setPNode(Node PNode) {
        this.PNode = PNode;
    }
}
