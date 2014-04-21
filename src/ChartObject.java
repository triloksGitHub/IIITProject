//package test;
 
public class ChartObject {
 
 private double readTime;
 private double writeTime;
 private String timeDetails;
  
 public ChartObject(double r, double w, String t)
 {
  this.readTime = r;
  this.writeTime = w;
  this.timeDetails = t;
 }
 public double getReadTime() {
  return readTime;
 }
 
 public void setReadTime(double readTime) {
  this.readTime = readTime;
 }
 
 public double getWriteTime() {
  return writeTime;
 }
 
 public void setWriteTime(double writeTime) {
  this.writeTime = writeTime;
 }
 
 public String getTimeDetails() {
  return timeDetails;
 }
 
 public void setTimeDetails(String timeDetails) {
  this.timeDetails = timeDetails;
 }
}