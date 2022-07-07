package com.lcy.demo.data;

import javax.xml.soap.Node;

public class SingleLink {

    static class SingleLinkList{
        int value;
        SingleLinkList next;
    }

    //构造一个单向链表
    public static SingleLinkList createSingleLinkList(){
        int[] arrs = new int[]{1,3,4,5,6,7,8,9,11};
        SingleLinkList head = new SingleLinkList();
        head.value=arrs[0];
        SingleLinkList temp = head;
        for(int i =1 ;i<arrs.length;i++){
           SingleLinkList newSingle = new SingleLinkList();
            newSingle.value = arrs[i];
            temp.next = newSingle;
            temp = newSingle;
        }
        return head;
    }


    public static SingleLinkList reverseSingleLinkList(SingleLinkList head){
        SingleLinkList reverList = null;
        SingleLinkList temp = null;
        while (head != null){
            temp = head;
            head = head.next;
            temp.next = null;
            if(reverList == null){
                reverList = temp;
            }else{
                temp.next = reverList;
                reverList = temp;
            }
        }
        return reverList;
    }

    public static void printf(SingleLinkList linkList){
        while (linkList!=null){
            System.out.println(linkList.value);
            linkList = linkList.next;
        }
    }

    public static void main(String[] args) {
        SingleLinkList singleLinkList = createSingleLinkList();
        printf(singleLinkList);
        SingleLinkList singleLinkList1 = reverseSingleLinkList(singleLinkList);
        printf(singleLinkList1);
    }


}
