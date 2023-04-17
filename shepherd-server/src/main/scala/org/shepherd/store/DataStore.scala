package org.shepherd.store

import com.google.common.collect.TreeBasedTable
import org.shepherd.Logging

import scala.io.Source

class DataStore extends Logging {

  // read static data
  val isoClassRiskModel = TreeBasedTable.create[String, String, AnyRef]()
  val stateRiskModel = TreeBasedTable.create[String, String, AnyRef]()
  val safetechRiskModel = TreeBasedTable.create[String, Integer, AnyRef]()

  val isoClassData = Source.fromInputStream(getClass.getResourceAsStream("/iso_class_risk_model.csv")).getLines
  val stateData = Source.fromInputStream(getClass.getResourceAsStream("/state_risk_model.csv")).getLines
  val safetechData = Source.fromInputStream(getClass.getResourceAsStream("/safetech_risk_model.csv")).getLines

  // iso class risk model
  isoClassData.drop(1) foreach { line =>
    val row = line.split(",")
    val isoCode = row(0)
    val description = row(1)
    val minYearsOfExperience = row(2).toInt.asInstanceOf[AnyRef]
    val isoClassRiskTier = row(3).asInstanceOf[AnyRef]

    isoClassRiskModel.put(isoCode, "description", description)
    isoClassRiskModel.put(isoCode, "min_experience", minYearsOfExperience)
    isoClassRiskModel.put(isoCode, "risk_tier", isoClassRiskTier)
  }

  // state risk model
  stateData.drop(1) foreach { line =>
    val row = line.split(",")
    val name = row(0)
    val abb = row(1).asInstanceOf[AnyRef]
    val stateRiskTier = row(2).toInt.asInstanceOf[AnyRef]

    stateRiskModel.put(name, "abbreviation", abb)
    stateRiskModel.put(name, "risk_tier", stateRiskTier)
  }

  // safetech risk  model
  safetechData.drop(1) foreach { line =>
    val row = line.split(",")
    val grade = row(0).toInt
    val modifier = row(1).asInstanceOf[AnyRef]

    safetechRiskModel.put("model", grade, modifier)
  }

  def shutdown(): Unit = {
    isoClassRiskModel.clear()
    stateRiskModel.clear()
    safetechRiskModel.clear()
  }
}
