package cdu.lft.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author 刘 福桃
 * TODO：
 * date 2020-03-30 12:52
 */
public class TreeNodeBuilder {

    public static List<TreeNode> builder(List<TreeNode> treeNodes,Integer topPid){

        List<TreeNode> nodes=new ArrayList();
        for (TreeNode tn1:treeNodes){
            if (tn1.getPid()==topPid){
                nodes.add(tn1);
            }
            for (TreeNode tn2:treeNodes){
                if (tn1.getId()==tn2.getPid()){
                    tn1.getChildren().add(tn2);
                }
            }
        }
        return nodes;
    }


}
