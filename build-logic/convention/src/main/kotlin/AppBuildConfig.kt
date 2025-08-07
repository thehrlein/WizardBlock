/** Info about target & compile sdk
 *  compileSdkVersion
 *  The compileSdkVersion is the version of the API the app is compiled against. This means you can use
 *  Android API features included in that version of the API (as well as all previous versions, obviously).
 *  If you try and use API 35 features but set compileSdkVersion to 34, you will get a compilation error.
 *  If you set compileSdkVersion to 35 you can still run the app on a API 34 device as long as your app's
 *  execution paths do not attempt to invoke any APIs specific to API 35.
 *
 *  targetSdkVersion
 *  The targetSdkVersion has nothing to do with how your app is compiled or what APIs you can utilize.
 *  The targetSdkVersion is supposed to indicate that you have tested your app on (presumably up to
 *  and including) the version you specify. This is more like a certification or sign off you are
 *  giving the Android OS as a hint to how it should handle your app in terms of OS features.
 */

object AppBuildConfig {
    const val targetAndCompileSdk = 36
    const val minSdk = 24
    const val jvmToolchain = 21
    const val versionName = "3.2.3"
    const val applicationId = "com.tobiashehrlein.tobiswizardblock"
}
