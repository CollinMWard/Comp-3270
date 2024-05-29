import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ProgrammingAssignment2 {
   private Map<String, List<String>> graph;

   public ProgrammingAssignment2(String filePath) {
      this.graph = loadGraph(filePath);
   }

   
   private Map<String, List<String>> loadGraph(String filePath) {
      Map<String, List<String>> graph = new HashMap<>();
      // use a try later
      try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
         String line;
         while ((line = reader.readLine()) != null) {
            String[] nodes = line.split(",");
            String node1 = nodes[0].trim();
            String node2 = nodes[1].trim();
         
            graph.putIfAbsent(node1, new ArrayList<>());
            graph.putIfAbsent(node2, new ArrayList<>());
         
            graph.get(node1).add(node2);
            graph.get(node2).add(node1);
         }
      } catch (IOException e) {
         System.err.println("Wacked fr fr" + e.getMessage());
         return null; 
      }
      return graph;
   }

   
   // BFS    
  
   public int bfs(String startNode, String endNode) {
      Deque<String> queue = new ArrayDeque<>();  
      Set<String> visited = new HashSet<>();
      int totalDistance = 0;
   
      queue.add(startNode);  
      visited.add(startNode);
   
      while (!queue.isEmpty()) {
         int currentLevelSize = queue.size();
         for (int i = 0; i < currentLevelSize; i++) {
            String node = queue.poll();  
            if (node.equals(endNode)) {
               return totalDistance;
            }
            totalDistance++;
         
         
            List<String> neighbors = graph.getOrDefault(node, new ArrayList<>());
                       
            for (String neighbor : neighbors) {
               if (!visited.contains(neighbor)) {
                  visited.add(neighbor);
                  queue.add(neighbor);  
               }
            }
         }
      }
      return -1; 
   }

  
   // DFS


   public int dfs(String startNode, String endNode) {
      Deque<String> stack = new ArrayDeque<>();
      Set<String> visited = new HashSet<>();
      int totalDistance = 0; 
   
      stack.push(startNode);
      visited.add(startNode);
     
   
      while (!stack.isEmpty()) {
         String node = stack.pop();
         if (node.equals(endNode)) {
            return totalDistance;
         }
      
         List<String> neighbors = graph.getOrDefault(node, new ArrayList<>());
         for (String neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
               visited.add(neighbor);
               stack.push(neighbor);
               totalDistance++;  
            }
         }
      }
      return -1; 
   }

   public static void main(String[] args) {
      long startTime = 0;
      long endTime = 0;
      long[] totalTime = new long[2];
      ProgrammingAssignment2 pa2 = new ProgrammingAssignment2("Test_Case_Assignment2.txt");
   
      for (int i = 0; i <= 24; i++) { 
         String nodeTest1 = "N_0";
         String nodeTest2 = "N_" + i;
      
         startTime = System.nanoTime(); 
         int bfsResult = pa2.bfs(nodeTest1, nodeTest2);
         endTime = System.nanoTime();
         totalTime[0] = endTime-startTime;
      
         startTime = 0;
         endTime = 0;
      
         startTime = System.nanoTime(); 
         int dfsResult = pa2.dfs(nodeTest1, nodeTest2);
         endTime = System.nanoTime();
         totalTime[1] = endTime-startTime;
      
      
         System.out.println(nodeTest2);
         System.out.println("BFS: Distance = " + bfsResult);
         System.out.println("BFS: Time = " + totalTime[0]);
         System.out.println("DFS: Distance = " + dfsResult);
         System.out.println("DFS: Time = " + totalTime[1] + "\n");
      }
   }
}
