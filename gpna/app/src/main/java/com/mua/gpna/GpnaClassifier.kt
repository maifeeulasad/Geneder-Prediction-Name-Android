package com.mua.gpna

import android.app.Activity
import android.util.Log
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class GpnaClassifier(activity: Activity) {

    private var tflite: Interpreter

    init {
        tflite = Interpreter(loadModelFile(activity))
    }

    private fun loadModelFile(
        activity: Activity,
        modelPath: String = "converted_model.tflite"
    ): MappedByteBuffer {
        val fileDescriptor = activity.assets.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }


    fun runInference(input: String):Float {
        val output = Array(1){FloatArray(1)}
        val encodedInput = shittyEncode(input)
        Log.d("d--mua", "Inference run: $input")
        Log.d("d--mua", "Inference run: $output")
        encodedInput.forEach{
            Log.d("d--mua","$it,")
        }
        tflite.run(encodedInput, output)
        return output[0][0]
    }

    private fun shittyEncode(input: String): FloatArray {
        val maxWordLen = 15
        val encoded = FloatArray(maxWordLen){0f}
        input.forEachIndexed { i, c ->
            encoded[maxWordLen - i - 1] = (c - 'a').toFloat()
        }
        return encoded
    }

}