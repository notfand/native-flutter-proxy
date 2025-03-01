package com.notfand.proxy.native_flutter_proxy

import androidx.annotation.NonNull

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** NativeFlutterProxyPlugin */
class NativeFlutterProxyPlugin: FlutterPlugin, MethodCallHandler {
  private var mMethodChannel: MethodChannel? = null;

  companion object {
    @JvmStatic
    fun registerWith(registrar: Registrar) {
      val instance = FlutterProxyPlugin()
      instance.onAttachedToEngine(registrar.messenger());
    }
  }

  private fun onAttachedToEngine(messenger: BinaryMessenger) {
    mMethodChannel = MethodChannel(messenger, "native_flutter_proxy")
    mMethodChannel!!.setMethodCallHandler(this)
  }

  override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    mMethodChannel = MethodChannel(binding.binaryMessenger, "native_flutter_proxy")
    mMethodChannel!!.setMethodCallHandler(this)
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    mMethodChannel!!.setMethodCallHandler(null)
    mMethodChannel = null
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    if (call.method == "getProxySetting") {
      result.success(getProxySetting())
    } else {
      result.notImplemented()
    }
  }

  private fun getProxySetting(): Any? {
    val map = LinkedHashMap<String, Any?>()
    map["host"] = System.getProperty("http.proxyHost")
    map["port"] = System.getProperty("http.proxyPort")
    return map
  }

}
