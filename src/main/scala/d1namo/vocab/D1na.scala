package d1namo.vocab

import rsp.vocab.Vocab
import rsp.data.Iri
import rsp.data.RdfTools._

object D1na extends Vocab{
  override val iri:Iri="http://hevs.ch/aislab/vocab/d1namo#"
  val HypoglycemiaEvent=D1na("HypoglycemiaEvent")  
  val HyperglycemiaEvent=D1na("HyperglycemiaEvent") 
  val GlycemicEvent = D1na("GlycemicEvent")
  val ECGObservation=D1na("ECGObservation")
  val ECGObservationValue=D1na("ECGObservationValue")
  val BreathingWaveObservation=D1na("BreathingWaveObservation")
  
  val ecg_waveform=D1na("ecg_waveform")
  val ecg_heartrate=D1na("ecg_heartrate")
  val vmu=D1na("vmu")
  val vmu_cummulative=D1na("vmu_cummulative")
  val breathing_beat=D1na("breathing_beat")
  val breathing_beat_to_beat=D1na("breathing_beat_to_beat")
  val breathing_peak=D1na("breathing_peak")
  val breathing_rate=D1na("breathing_rate")
  
  
  val skin=D1na("skin")
}