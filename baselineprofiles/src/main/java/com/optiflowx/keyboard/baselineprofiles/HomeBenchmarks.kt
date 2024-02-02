package com.optiflowx.keyboard.baselineprofiles

import androidx.benchmark.macro.BaselineProfileMode
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This test class benchmarks the speed of app startup.
 * Run this benchmark to verify how effective a Baseline Profile is.
 * It does this by comparing [CompilationMode.None], which represents the app with no Baseline
 * Profiles optimizations, and [CompilationMode.Partial], which uses Baseline Profiles.
 *
 * Run this benchmark to see startup measurements and captured system traces for verifying
 * the effectiveness of your Baseline Profiles. You can run it directly from Android
 * Studio as an instrumentation test, or run all benchmarks for a variant, for example benchmarkRelease,
 * with this Gradle task:
 * ```
 * ./gradlew :baselineprofiles:connectedBenchmarkReleaseAndroidTest
 * ```
 *
 * You should run the benchmarks on a physical device, not an Android emulator, because the
 * emulator doesn't represent real world performance and shares system resources with its host.
 *
 * For more information, see the [Macrobenchmark documentation](https://d.android.com/macrobenchmark#create-macrobenchmark)
 * and the [instrumentation arguments documentation](https://d.android.com/topic/performance/benchmarking/macrobenchmark-instrumentation-args).
 **/
@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeBenchmarks {

    @get:Rule
    val rule = MacrobenchmarkRule()

    @Test
    fun homeCompilationNone() =
        benchmark(CompilationMode.None())

    @Test
    fun homeCompilationBaselineProfiles() =
        benchmark(CompilationMode.Partial(BaselineProfileMode.Require))

    private fun benchmark(compilationMode: CompilationMode) {
        // This example works only with the variant with application id `com.optiflowx.applekeyboard`."
        rule.measureRepeated(
            packageName = "com.optiflowx.applekeyboard",
            metrics = listOf(FrameTimingMetric()),
            compilationMode = compilationMode,
            startupMode = StartupMode.COLD,
            iterations = 2,
            setupBlock = { pressHome() },
            measureBlock = {
                startActivityAndWait()
                addElementsAndScrollDown()

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

private fun MacrobenchmarkScope.addElementsAndScrollDown() {
//     TODO Add elements to benchmark here.
//     For example, you can add a button to benchmark like this:
    val homeList = device.findObject(By.res("home_screen_list"))
//    val keyboards = device.findObject(By.desc("Keyboards"))
//    val keyboardFonts = device.findObject(By.text("Keyboard Fonts"))
//    val textReplacement = device.findObject(By.text("Text Replacement"))

    //Avoid gestures on the edge of the screen
    repeat(2) {
        homeList.setGestureMargin(device.displayWidth / 5)
        homeList.fling(Direction.DOWN, 7200)
        homeList.fling(Direction.UP, 7200)
    }

    device.waitForIdle(150)

//    device.findObject(By.)

//    keyboards.click()
//    device.waitForIdle(150)
//    device.pressBack()
//
//    keyboardFonts.click()
//    device.waitForIdle(150)
//    device.pressBack()
//
//    textReplacement.click()
//    device.waitForIdle(150)
//    device.pressBack()
}