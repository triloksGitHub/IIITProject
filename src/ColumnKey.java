//package test;
 
public class ColumnKey implements Comparable<String> {
 private String time;
 public ColumnKey(String time)
 {
  this.time = time;
 }
 
 public int compareTo(String o) {
  return time.compareTo(o);
 }
  
 @Override
 public String toString()
 {
  return this.time;
 }
}