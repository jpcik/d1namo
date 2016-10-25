package d1namo.query

import rsp.engine.RspReasoner
import d1namo.TimeSeriesProcess
import d1namo.vocab.D1na
import rsp.vocab.SSN
import rsp.data.Iri
import rsp.data.Triple
import rsp.vocab.Rdf
import org.joda.time.DateTime
import rsp.data.RdfTools._
import rsp.data.Literal._
import rsp.vocab.OwlTime
import rsp.vocab.QU
import scala.collection.mutable.ArrayBuffer
import org.joda.time.format.DateTimeFormat


class StreamTimeSeries(rsp:RspReasoner,props:Map[String,String],rate:Long) extends TimeSeriesProcess {
  val sensorId=props("sensorId")
  val obsProp=Iri(props("observedProperty"))//D1na.ecg_waveform)
  val featureOfInterest=Iri(props("featureOfInterest"))
  val observation=Iri(props("observation"))
  val observationValue=Iri(props("observationValue"))
  
  val timeFormat=DateTimeFormat.forPattern("yyyyMMdd'T'HHmmssSSSZ")
  val sensorIri=Iri(pfx+"sensor_"+sensorId)
  val streamIri=Iri(pfx+"stream")
  
  def process(line:String)={
    val data=split(line)
    //println("datita "+sensorId+" "+data.mkString)
    val time=toTime(data(0))
    val obsIri=Iri(sensorIri.value+"/obs/"+obsProp.localName+"_"+time.toString(timeFormat))
    val triples=obs(obsIri,sensorIri,obsProp,time,data(1).toDouble)
    triples.foreach { t => 
      println("trips "+t )
      rsp.consume(streamIri.value,t )}
    Thread.sleep(rate)
    
  }
  def obs(iri:Iri,sensorIri:Iri,obsProp:Iri,time:DateTime,value:Double)={
    val triples=new ArrayBuffer[Triple]
    
    //val timeIri=Iri(pfx+"instant_"+time.toString(timeFormat))
    //triples+=Triple(timeIri,OwlTime.inXSDDatetime,lit(time))
  
    val obsValue=Iri(iri.value+"/obsvalue")
    triples+=Triple(obsValue,Rdf.a,observationValue)
    triples+=Triple(obsValue,QU.numericalValue,lit(value))
    
    triples+=Triple(iri,Rdf.a,observation)
    triples+=Triple(iri,SSN.observedBy,sensorIri)
    triples+=Triple(iri,SSN.observedProperty,obsProp)
    triples+=Triple(iri,SSN.featureOfInterest,featureOfInterest)
    triples+=Triple(iri,OwlTime.inXSDDatetime,lit(time))
    triples+=Triple(iri,SSN.observationResult,obsValue)
    triples
  }
}

/*
* stream   http://hevs.ch/aislab/d1namo/sensor_p1/stream
* sensor   http://hevs.ch/aislab/d1namo/sensor_p1
* obs      http://hevs.ch/aislab/d1namo/sensor_p1/obs/ecg_waveform_20150501T053300+0200
* obsval   http://hevs.ch/aislab/d1namo/sensor_p1/obs/ecg_waveform_20150501T053300+0200/obsvalue
*/
