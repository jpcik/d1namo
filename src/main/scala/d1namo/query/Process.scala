package d1namo.query

import rsp.engine.cqels.CqelsEngine
import rsp.util.JenaTypes._
import scala.concurrent.Future
import d1namo.vocab.D1na
import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext

class DataProcess {
  
}

object RunProcess {
  val executorService  = Executors.newFixedThreadPool(1000)
    implicit val executionContext = ExecutionContext.fromExecutorService(executorService)

  var count=0
  def getResults(data:Map[String,Any]):Unit={
    //println("tikki: "+data.mkString)
    count+=1
  }
      val cqels=new CqelsEngine

  val ecgWaveform=Map("featureOfInterest"->D1na.skin.value,
                      "observedProperty"->D1na.ecg_waveform.value,
                      "observation"->D1na.ECGObservation.value,
                      "observationValue"->D1na.ECGObservationValue.value)
  val ecgHeartrate=Map("featureOfInterest"->D1na.skin.value,
                      "observedProperty"->D1na.ecg_heartrate.value,
                      "observation"->D1na.ECGObservation.value,
                      "observationValue"->D1na.ECGObservationValue.value)
  val vmu         =Map("featureOfInterest"->D1na.skin.value,
                      "observedProperty"->D1na.vmu.value,
                      "observation"->D1na.ECGObservation.value,
                      "observationValue"->D1na.ECGObservationValue.value)
  val vmuCummulative=Map("featureOfInterest"->D1na.skin.value,
                      "observedProperty"->D1na.vmu_cummulative.value,
                      "observation"->D1na.ECGObservation.value,
                      "observationValue"->D1na.ECGObservationValue.value)
  val breathingPeak=Map("featureOfInterest"->D1na.skin.value,
                      "observedProperty"->D1na.breathing_peak.value,
                      "observation"->D1na.ECGObservation.value,
                      "observationValue"->D1na.ECGObservationValue.value)
  val breathingRate=Map("featureOfInterest"->D1na.skin.value,
                      "observedProperty"->D1na.breathing_rate.value,
                      "observation"->D1na.ECGObservation.value,
                      "observationValue"->D1na.ECGObservationValue.value)

          def runStreams(i:Int)={
       //   import scala.concurrent.ExecutionContext.Implicits.global
//println("index "+i)
       val stream1=new StreamTimeSeries(cqels,ecgWaveform++Map("sensorId"->s"p1_$i"),10)
    val stream2=new StreamTimeSeries(cqels,ecgHeartrate++Map("sensorId"->s"p2_$i"),10)
    val stream3=new StreamTimeSeries(cqels,vmu++Map("sensorId"->s"p3_$i"),10)
    val stream4=new StreamTimeSeries(cqels,breathingPeak++Map("sensorId"->s"p4_$i"),10)
    val stream5=new StreamTimeSeries(cqels,breathingRate++Map("sensorId"->s"p5_$i"),10)
    
    Future{stream1.feedFrom("raw/2015_05_01-05_33_00_ECG.csv")}
    Future{println("coto "+stream2.sensorId);stream2.feedFrom("raw/2015_05_01-05_33_00_ECG.csv")}
    Future{stream3.feedFrom("raw/2015_05_01-05_33_00_ECG.csv")}
    Future{stream4.feedFrom("raw/2015_05_01-05_33_00_ECG.csv")}
    Future{stream5.feedFrom("raw/2015_05_01-05_33_00_ECG.csv")}
    //println("pin "+i)
      }
                      
  def main(args:Array[String]):Unit={
   
    val q=s"""
          PREFIX d1na:<http://hevs.ch/aislab/vocab/d1namo#>
          PREFIX qu:<http://purl.oclc.org/NET/ssnx/qu#>
          PREFIX ssn:<http://purl.oclc.org/NET/ssnx/ssn#> 
          SELECT ?obs   
          WHERE { 
            STREAM <http://hevs.ch/aislab/d1namo/stream> [RANGE 1000ms]  {
              ?obs ssn:observedProperty d1na:ecg_waveform .
              ?obs ssn:observationResult ?obsval .
              ?obsval qu:numericalValue ?val .
              
            }
          }"""
  
    (1 to 5) foreach{i=>
      cqels.registerSelectQuery(q, cqels.selectListener(getResults))
    }
    

        //import scala.concurrent.ExecutionContext.Implicits.global
     
      (1 to 1) foreach{i=>
      Future{runStreams(i)}
    }
      
    
    Thread.sleep(20000)
    println("Count is "+count)
    println("input is "+cqels.inputCount)
    System.exit(0)
  }
}