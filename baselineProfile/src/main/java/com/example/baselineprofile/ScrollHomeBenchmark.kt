package com.example.baselineprofile

import androidx.benchmark.macro.BaselineProfileMode
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ScrollHomeBenchmark {
    @get:Rule
    val rule = MacrobenchmarkRule()

    @Test
    fun startupCompilationNone() =
        scrollHome(CompilationMode.None())

    @Test
    fun startupCompilationBaselineProfiles() =
        scrollHome(CompilationMode.Partial(BaselineProfileMode.Require))

    private fun scrollHome(compilationMode: CompilationMode) {
        // This example works only with the variant with application id `com.optiflowx.applekeyboard`."
        rule.measureRepeated(
            packageName = "com.optiflowx.applekeyboard",
            metrics = listOf(FrameTimingMetric()),
            compilationMode = compilationMode,
            startupMode = StartupMode.COLD,
            iterations = 5,
            setupBlock = {
                this.pressHome()
                this.startActivityAndWait() //TTID

                //Wait until App bar is visible
                this.device.wait(Until.findObject(By.text("Keyboard")), 30000)


            },
            measureBlock = {


                // TODO Add interactions to wait for when your app is fully drawn.
                // The app is fully drawn when Activity.reportFullyDrawn is called.
                // For Jetpack Compose, you can use ReportDrawn, ReportDrawnWhen and ReportDrawnAfter
                // from the AndroidX Activity library.

                // Check the UiAutomator documentation for more information on how to
                // interact with the app.
                // https://d.android.com/training/testing/other-components/ui-automator
            }
        )
    }
}