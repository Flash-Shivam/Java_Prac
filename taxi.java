import javafx.util.Pair;
import java.util.*;
import java.lang.*;
import java.io.*;


class graph{

  public int total_vertices;
  public Vector < Vector < Pair <Integer, Integer> > > vertices;
  public Vector < Vector < Pair <Integer, Integer> > > distance;
  public Vector < Vector < Pair <Integer, Integer> > > for_path;
  public Vector <String> names;

  public graph()
  {
    this.total_vertices = 0;
    this.vertices = new  Vector < Vector <Pair <Integer, Integer>> >();
    this.distance = new  Vector < Vector <Pair <Integer, Integer>> >();
    this.for_path = new  Vector < Vector <Pair <Integer, Integer>> >();
    this.names = new Vector <String>();

  }

  public boolean check_vertex(String s){
    for(int i=0;i<this.names.size();i++)
    {
      if(this.names.get(i)==s)
      {
        return true;
      }
    }
    return false;
  }

  public void add_vertex(String s){
    this.total_vertices++;
    this.names.add(s);
    Vector <Pair <Integer, Integer> > temp = new Vector <Pair <Integer, Integer> >();
    vertices.add(temp);


  }

  public int get_index(String s){
    for(int i=0;i<this.names.size();i++)
    {
      if(this.names.get(i)==s)
      {
        return i;
      }
    }

    return 0;
  }

  public void add_edge(String s, String t, int time){

    if(!check_vertex(s)){
      add_vertex(s);
    }

    if(!check_vertex(t)){
      add_vertex(t);
    }

    int index1 = get_index(s);
    int index2 = get_index(t);

    Pair <Integer, Integer> temp = new Pair <Integer, Integer> (index1 , time);
    Pair <Integer, Integer> temp1 = new Pair <Integer, Integer> (index2 , time);

    this.vertices.get(index1).add(temp1);
    this.vertices.get(index2).add(temp);


  }

  public void find_min_dist(String source)
  {
    //Triple <Integer, Integer, Integer> x = new Triple <Integer, Integer, Integer>();
    int total = this.total_vertices;
    int [] dist = new int[total];
    int [] prev_vertex = new int[total];
    int visited_size = 0;
    boolean [] visited = new boolean[total];
    int index = get_index(source);
    for(int i=0;i<total;i++)
    {
      if(i==index){
          dist[i] = 0;

      }
      else{
        dist[i] = 1000000;

      }
      visited[i] = false;
      prev_vertex[i] = -1;
    }

    while(visited_size!=total){
        int min = 1000000;
        int pivot = -1;
        for(int i=0;i<total;i++)
        {
          if(dist[i]<min && visited[i]==false)
          {
            pivot = i;
            min = dist[i];
          }
        }
        if(visited_size==0)
        {
          pivot = index;
        }
        visited[pivot] = true;
        visited_size++;

        for(int i=0;i<this.vertices.get(pivot).size();i++)
        {

            Pair <Integer,Integer> temp = this.vertices.get(pivot).get(i);
            int temp1 = temp.getKey();
            int temp2 = temp.getValue();

            if(dist[temp1]==1000000 && visited[temp1]==false)
            {
              dist[temp1] = dist[pivot] + temp2;
              prev_vertex[temp1] = pivot;
            }
            else if(dist[pivot] + temp2	< dist[temp1] && visited[temp1]==false)
            {
              dist[temp1] = dist[pivot] + temp2;
              prev_vertex[temp1] = pivot;
            }

        }


    }
      Vector <Pair <Integer, Integer> > temp = new Vector <Pair <Integer, Integer> >();
      Vector <Pair <Integer, Integer> > temp3 = new Vector <Pair <Integer, Integer> >();
    for(int i=0;i<total;i++)
    {
      if(i!=index)
      {
        Pair <Integer, Integer> temp1 = new Pair <Integer, Integer> (i , dist[i]);
        Pair <Integer, Integer> temp2 = new Pair <Integer, Integer> (i , prev_vertex[i]);
        temp.add(temp1);
        temp3.add(temp2);
      }
    }

    this.distance.add(temp);
    this.for_path.add(temp3);


  }

  public void distance_graph_formation()
  {
    for(int i=0;i<this.total_vertices;i++)
    {
      find_min_dist(this.names.get(i));
    }
  }

  public int timeTaken()
  {
    int index = get_index(source);
    int index1 = get_index(destination);
    Vector <Pair <Integer, Integer> > temp = this.distance.get(index);
    for(int i=0;i<temp1.size();i++)
    {
      if(temp.get(i).getKey()==index1)
      {
        return temp.get(i).getValue();
      }
    }
    return -1;
  }

  public Vector <String> findPath(String source, String destination)
  {
    int index = get_index(source);
    int index1 = get_index(destination);
    //System.out.println(index + " " + index1);
    Vector <Pair <Integer, Integer> > temp = this.for_path.get(index);
    Vector <Pair <Integer, Integer> > temp1 = this.distance.get(index);
    Vector <String> path = new Vector <String>();
    int pivot = -1;
    path.add(destination);
    for(int i=0;i<temp1.size();i++)
    {
      if(temp1.get(i).getKey()==index1)
      {
        if(temp1.get(i).getValue()==1000000)
        {
          return path;
        }
        else{
          pivot = i;
        }
      }
    }


    while(true)
    {
      int x = temp.get(pivot).getValue();
      path.add(this.names.get(x));
      for(int i=0;i<temp1.size();i++)
      {
        if(temp1.get(i).getKey()==x)
        {
            pivot = i;
        }
      }
      if(x == index)
      {
        break;
      }
    }
    path.add(source);

    return path;

  }


}

public class taxi{

  public static void main (String[] args) throws java.lang.Exception
  {

      graph Uber = new graph();
      Uber.add_edge("A","B",6);
      Uber.add_edge("A","D",1);
      Uber.add_edge("B","E",2);
      Uber.add_edge("B","C",5);
      Uber.add_edge("C","E",5);
      Uber.add_edge("D","E",1);
      Uber.add_edge("D","B",2);
      //Uber.find_min_dist("A");
      for(int i=0;i<Uber.names.size();i++)
      {
        System.out.print(Uber.names.get(i)+", ");
      }
      System.out.println("");
      for(int i=0;i<Uber.names.size();i++)
      {
        Vector <Pair <Integer, Integer> > temp = Uber.vertices.get(i);
        System.out.println(Uber.names.get(i));
        for(int j=0;j<temp.size();j++)
        {
          System.out.println(temp.get(j).getKey() + " --> " + temp.get(j).getValue());
        }
      }

      Uber.distance_graph_formation();
      for(int i=0;i<Uber.total_vertices;i++)
      {
        Vector <Pair <Integer, Integer> > temp = Uber.distance.get(i);
        Vector <Pair <Integer, Integer> > temp2 = Uber.for_path.get(i);
        System.out.println(Uber.names.get(i));
        for(int j=0;j<temp.size();j++)
        {
          System.out.println(temp.get(j).getKey() + " --> " + temp.get(j).getValue());
          System.out.println(temp2.get(j).getKey() + " --> " + temp2.get(j).getValue());
        }
      }

      Vector <String> x = Uber.findPath("A","B");
      for(int i=0;i<x.size();i++)
      {
        System.out.print(x.get(i) + ", ");
      }

  }


}
