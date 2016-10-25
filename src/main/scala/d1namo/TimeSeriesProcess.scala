package d1namo

import org.joda.time.format.DateTimeFormat
import scala.io.Source
import org.joda.time.DateTime

trait TimeSeriesProcess {
  val format=DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss.SSS")  
  val rootDir=System.getProperty("user.home")+"/data/p1/d1/"
  val pfx="http://hevs.ch/aislab/d1namo/"

  def split(line:String)=line.split(",")
  var count=0

  def toTime(timeStr:String)=format.parseDateTime(timeStr)
  def process(line:String):Unit
  // 01/05/2015 05:33:00.423,3798
  
  def feedFrom(file:String)={
     val lines=Source.fromFile(rootDir+file).getLines
    var last=0
    var lastTime=DateTime.now().minusYears(10)
    lines.foreach { line =>
      if (count>0){
        process(line)
      }
      count+=1  
    }
  }
  
  def summarize(file:String)={
   feedFrom(file)
  } 
}