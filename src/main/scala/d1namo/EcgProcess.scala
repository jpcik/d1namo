package d1namo

import scala.io.Source
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormatter
import org.joda.time.format.DateTimeFormat

class EcgProcess extends TimeSeriesProcess{
  
  // 01/05/2015 05:33:00.423,3798
  def process(line:String)={
    //val lines=Source.fromFile(rootDir+"raw/2015_05_01-05_33_00_ECG.csv").getLines
    var last=0
    var lastTime=DateTime.now().minusYears(10)
        val array=split(line)
        val (time,ecg)=(toTime(array(0)),array(1).toInt)
        if (lastTime.plusSeconds(1).isBefore(time)) {
          println(time+","+ecg)
                  lastTime=time

        }      
    }    
  
}


class AccelerometerProcess extends TimeSeriesProcess{
  def process(line:String)={
    var last=0
    var lastTime=DateTime.now().minusYears(10)
        val array=split(line)
        val (time,vert,lat,sag)=(toTime(array(0)),array(1).toInt,array(2).toInt,array(3).toInt)
        if (lastTime.plusSeconds(1).isBefore(time)) {
          println(s"$time,$vert,$lat,$sag")
          lastTime=time

        }      
    }    
}


class BreathingProcess extends TimeSeriesProcess{
  def process(line:String)={
    var last=0
    var lastTime=DateTime.now().minusYears(10)
        val array=split(line)
        val (time,ecg)=(toTime(array(0)),array(1).toInt)
        if (lastTime.plusSeconds(1).isBefore(time)) {
          println(time+","+ecg)
                  lastTime=time

        }      
    }    
}


