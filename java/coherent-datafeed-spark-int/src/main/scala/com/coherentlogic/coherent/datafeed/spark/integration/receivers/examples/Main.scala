package com.coherentlogic.coherent.datafeed.spark.integration.receivers.examples

import org.springframework.boot.builder.SpringApplicationBuilder

import com.coherentlogic.coherent.datafeed.spark.integration.receivers.ElektronMarketPriceReceiver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ImportResource
import org.springframework.context.annotation.Profile

/**
 * 
 */
object Main extends App {

  def builder = new SpringApplicationBuilder (classOf[ElektronMarketPriceReceiver])

  //val args : Array[String] = Array()

    builder
      .web(false)
      .headless(false)
      .registerShutdownHook(true)
      .run(args : _*)

      Thread.sleep(Long.MaxValue);

      System.exit(-9999);
}