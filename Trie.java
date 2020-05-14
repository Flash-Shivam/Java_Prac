
import javafx.util.Pair;
import java.util.*;
import java.lang.*;
import java.io.*;
//import org.javatuples.Pair;

class Node{

  public char content;
  public Vector < Node > children;
  public Node(char s)
  {
    this.content = s;
    this.children = new Vector < Node >();
  }

}


class Tree{

  Node root;
  public Vector < Node > leaves;


public Tree()
{
  this.root = new Node('*');
  this.leaves = new Vector < Node >();
}

public void add_child(Node x, char s){
    x.children.add(new Node(s));
}



public Node get_root()
{
  return this.root;
}

public int count_leaves(boolean val, Node x)
{
  Queue<Node> q = new LinkedList<>();
  q.add(x);
  int count = 0;
  int level = 0;
  while(!q.isEmpty())
  {

      int len = q.size();
      for(int j=0;j<len;j++)
      {
        Node head = q.remove();
        if(head.content == '#')
        {
          count++;
          //leaves.add(head);
        }
        for(int k=0;k<head.children.size();k++)
        {
          q.add(head.children.get(k));
        }
        if(val){
          System.out.print(head.content);
          System.out.print(" // ");
        }

      }
      if(val){
      System.out.println("Level : " + level);
    }
      level++;

  }

  return count;

}


}

public class Trie
{

  public Tree t;
  public Trie()
  {
    this.t = new Tree();
  }

  public Pair<Integer,Node> check_string(String s){

    Node pseudo_root = this.t.get_root();

    for(int i=0;i<s.length();i++)
    {
        char p = s.charAt(i);
        Vector < Node > pseudo_child = pseudo_root.children;
        int flag = 0;

        for(int j=0;j<pseudo_child.size();j++)
        {
          if(pseudo_child.get(j).content == p)
          {
            pseudo_root = pseudo_child.get(j);
            flag = -1;
            break;
          }
        }

        if(flag == 0)
        {
            Pair <Integer, Node> ans = new Pair <Integer, Node >(i, pseudo_root);
            return ans;
        }


    }

    Pair <Integer, Node > ans = new Pair <Integer, Node >(s.length(), pseudo_root);
    return ans;

  }

  public void insert_string(String s)
  {
    Pair <Integer, Node > res = check_string(s);

    int start = res.getKey();
    boolean res1 = true;
    if(start == s.length())
    {
      res1 = false;
    }
    Node to_be_added = res.getValue();

    if(res1)
    {
      while(start!=s.length())
      {
        this.t.add_child(to_be_added,s.charAt(start));

        start++;

        to_be_added = to_be_added.children.get(to_be_added.children.size()-1);
      }
      this.t.add_child(to_be_added,'#');
      //System.out.println("----");
    }
    else{
      this.t.add_child(to_be_added,'#');
      //System.out.println("----");
      //System.out.println("String Already exist");
    }

  }

  public void build_suffix_tree(String s)
  {
      Node p_root = this.t.get_root();
      //this.t.leaves.add(p_root);
      System.out.println(p_root);
      System.out.println(p_root.content);
      System.out.println(p_root.children.size());


      for(int i=0;i<s.length();i++)
      {
        char p = s.charAt(i);

        int flag = 0;
        for(int j=0;j<p_root.children.size();j++)
        {
          if(p_root.children.get(j).content == p)
          {
            flag = -1;
            break;
          }
        }
        if(flag==0)
        {
          p_root.children.add(new Node('#'));
          this.t.leaves.add(p_root.children.get(p_root.children.size()-1));
        }


        for(int j=0;j<this.t.leaves.size();j++)
        {
          Node temp = this.t.leaves.get(j);
          temp.content = p;
          this.t.add_child(temp,'#');
          Node temp2 = temp.children.get(temp.children.size()-1);
          this.t.leaves.set(j,temp2);
          if(i==0)
          {
            System.out.println(p_root);
            System.out.println(p_root.content);
            System.out.println(p_root.children.size());
            System.out.println(p_root.children.get(0).content);
          }
        }

      }

  }

  public int isSubstring(String s)
  {
    Pair <Integer, Node > res = check_string(s);
    if(res.getKey()!=s.length())
    {
      return 0;
    }
    return 1;
  }

  public int numSubstrings(String s){
    Pair <Integer, Node > res = check_string(s);
    Node p_root = res.getValue();
    return this.t.count_leaves(false,p_root);
  }



	public static void main (String[] args) throws java.lang.Exception
	{
    Trie string_trie = new Trie();
    String test = "banana";
    // Build suffix tree O(n*n)
    for(int i=0;i<test.length();i++)
    {


      String feed = test.substring(i);
      //System.out.print(feed + ", ");
      string_trie.insert_string(feed);
    }
    int leaves = string_trie.t.count_leaves(true,string_trie.t.get_root());
    String query = "ana";

    System.out.println(string_trie.isSubstring(query));

    System.out.println(string_trie.numSubstrings(query));

    
	}

}
