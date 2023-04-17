package org.shepherd.store

import com.google.common.collect.TreeBasedTable
import org.apache.commons.lang3.math.NumberUtils
import org.shepherd.Logging

import scala.io.Source

class DataStore extends Logging {


  // read static data
  val isoClassRiskModel = TreeBasedTable.create[String, String, Any]()
  val stateRiskModel = TreeBasedTable.create[String, String, Any]()
  val safetechRiskModel = TreeBasedTable.create[String, String, Any]()

  val isoClassData = Source.fromInputStream(getClass.getResourceAsStream("/iso_class_risk_model.csv")).getLines
  val stateData = Source.fromInputStream(getClass.getResourceAsStream("/state_risk_model.csv")).getLines
  val safetechData = Source.fromInputStream(getClass.getResourceAsStream("/safetech_risk_model.csv")).getLines

  // iso class risk model
  isoClassData.drop(1) foreach { line =>
    val row = line.split(",")
    val isoCode = row(0)
    val description = row(1)
    val minYearsOfExperience = row(2).toInt.asInstanceOf[Any]
    val isoClassRiskTier = row(3).asInstanceOf[Any]

    isoClassRiskModel.put(isoCode, "description", description)
    isoClassRiskModel.put(isoCode, "min_experience", minYearsOfExperience)
    isoClassRiskModel.put(isoCode, "risk_tier", isoClassRiskTier)
  }

  // state risk model
  stateData.drop(1) foreach { line =>
    val row = line.split(",")
    val name = row(0)
    val abb = row(1).asInstanceOf[Any]
    val stateRiskTier = row(2).toInt.asInstanceOf[Any]

    stateRiskModel.put(name, "abb", abb)
    stateRiskModel.put(name, "risk_tier", stateRiskTier)
  }

  // safetech risk  model
  safetechData.drop(1) foreach { line =>
    val row = line.split(",")
    val grade = row(0)
    val modifier = row(1).asInstanceOf[Any]

    safetechRiskModel.put("model", grade, modifier)
  }

  /**
   * Calculate the final risk tier for a given contractor identified by the input params.
   *
   * @return Either the final risk tier or "referred" for specific exception cases.
   */
  def getFinalRiskTier(
    someIsoCode: Option[String],
    someYearsOfExpereince: Option[String],
    someState: Option[String],
    someTechGrade: Option[String]): Any = {

    assert(someIsoCode.isDefined && someYearsOfExpereince.isDefined && someState.isDefined && someTechGrade.isDefined)

    val isoCode = someIsoCode.get.toUpperCase
    val yoe = someYearsOfExpereince.get
    val state = someState.get
    val techGrade = someTechGrade.get

    val requiredExperience = isoClassRiskModel.get(isoCode, "min_experience")
    val isoRiskTier = isoClassRiskModel.get(isoCode, "risk_tier")
    val description = isoClassRiskModel.get(isoCode, "description")
    val stateRiskTier = stateRiskModel.get(state, "risk_tier")
    val techModifier = safetechRiskModel.get("model", techGrade)

    // data validation
    assert(NumberUtils.isParsable(yoe) && NumberUtils.isParsable(techGrade))
    if (requiredExperience == null || isoRiskTier == null || stateRiskTier == null || techModifier == null)
      throw new ArithmeticException("Hmm! Our sources currently do not have the information available for the provided inputs.")
    if (yoe.toInt < requiredExperience.asInstanceOf[Integer])
      throw new ArithmeticException(s"Provided years of experience is less than minimum numbers of YOE required for ISO $isoCode.")
    if (!stateRiskModel.rowKeySet().contains(state))
      throw new ArithmeticException(s"Provided state $state isn't supported.")
    if (isoRiskTier.asInstanceOf[String] == "REJECTED")
      throw new ArithmeticException(s"Sorry! A rejection has been encountered for ISO $isoCode.")
    if (techModifier.asInstanceOf[String] == "REJECT")
      throw new ArithmeticException(s"Sorry! A rejection has been encountered for $techGrade tech usage grade.")

    // final result
    if ((state == "New York" && description.asInstanceOf[String].toLowerCase == "crane work") ||
      (state == "Colorado" && description.asInstanceOf[String].toLowerCase == "railroad"))
      return "REFERRED"

    (isoRiskTier.asInstanceOf[String].toFloat + stateRiskTier.asInstanceOf[Integer]) *
      techModifier.asInstanceOf[String].toFloat / 2f
  }

  def shutdown(): Unit = {
    isoClassRiskModel.clear()
    stateRiskModel.clear()
    safetechRiskModel.clear()
  }
}
