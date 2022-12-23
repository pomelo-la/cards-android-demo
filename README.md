# Getting started

* [System Requirements](#system-requirements)

* [Adding Identity](#adding-identity)
  1. Import dependency
  2. Register SDK into your Application
  3. Authorization
  4. Create an user and a session
  5. Initialize Identity
     * [On Result Listener](#on-result-listener)
     * [Auth Token Listener](#auth-token-listener)
* [Styling](#styling)
   * [Icons and images customization](#icons-and-images-customization)
* [Release Notes](#release-otes)

&nbsp;
&nbsp;

# System Requirements

**Minimum SDK version:** 21 (Android 5.0)
**Compile SDK version:** 32

&nbsp;
# Adding Identity
## 1. Import dependency
Pomelo Identity is hosted in GitHub Packages. For more information refeer to their [site](https://docs.github.com/es/packages/learn-github-packages/installing-a-package)

*settings.gradle*

``` groovy
maven {  
    name = "Pomelo GitHubPackages Releases"  
  url = uri("https://maven.pkg.github.com/pomelo-la/android-public")  
    credentials {  
        username = "YOUR_USER_NAME" 
        password = "YOUR_ACCESS_TOKEN"
    }  
}
```
*build.gradle*

``` groovy
dependencies {
    com.pomelo:identity:1.0.0
}
```
Refer to *Release notes* for the latest version

&nbsp;

## 2. Register SDK into your Application
Inside your Application's `onCreate` function call `PomeloIdentity`.register passing the Application reference
``` kotlin
class YourApplication: Application() {  

   override fun onCreate() {  
     super.onCreate()  
     PomeloIdentity.register(this)  
}  
```
&nbsp;

## 3. Authorization
To communicate with Pomelo's API you must [generate an Access Token](https://developers.pomelo.la/api-reference/general/autorizacion/solicitar-token) to create/get an **User ID** and generate a **Session ID**. Usually this bussiness logic should live in your backend.

To communicate with Pomelo's Identity SDK you must generate an **End User Token** which is a JWT token that expires in a certain amount of time and it has to be generated again and set in the `AuthTokenListener`

<img src="https://user-images.githubusercontent.com/9848247/187751184-6aa86f71-0941-4dc6-876b-6dd717ceca43.png"/>

*Click image to view full screen*

&nbsp;

## 4. Create an user and a session 
To initiate Pomelo's Identity, first you have to:
1. [Create an User](https://developers.pomelo.la/api-reference/general/usuarios/crear-usuario).
   * If you already have an User, [retrieve the User ID](https://developers.pomelo.la/api-reference/general/usuarios/obtener-usuario)
2. With the User ID, [create a Session](https://developers.pomelo.la/api-reference/identity/identity/crear-sesion)
   * If you already have a Session created, [retrieve it](https://developers.pomelo.la/api-reference/identity/identity/obtener-sesion)

&nbsp;
  
## 5.  Initialize Identity
After your session has been created, you are ready to call `init()` function to start Pomelo's Identity flow. For example inside a Button's onClickListener
``` kotlin
PomeloIdentity.init(Configuration(sessionId = "SESSION_ID"
                                  onResultListener = { result, message ->  },
                                  authTokenListener = { "A_NEW_TOKEN" }))
```

### On Result Listener
This listener returns the final result from the user's onboarding when the flow ends and come back to your application. 
You can set your own logic on what to do after Pomelo's Identity is closed.

The following values are the different results 
```kotlin
enum class Result {
    NOT_VALIDATED,
    SESSION_EXPIRED,
    REJECTED,
    SUCCESS,
    FINISHED_BY_USER
}
```

### Auth Token Listener
*In future versions this is going to be deprecated*

To communicate with Pomelo's Identity SDK you have to provide a valid JWT token. This token expires in a certain amount of time and it have to be refreshed.

You should place your logic inside the `AuthTokenListener` to provide a new token every time the current token expires. Usually you will call a **repository**

&nbsp;

# Styling
Pomelo's Identity is developed to inherit your app's theme by default. It uses Material Design 2 for theming and styling but can work with AppCompat's theme with a few adjustments. 
If your app doesn't have an Application class you will need create and set in in your `Manifest.xml`
``` xml
<application android:name=".YourApplication"
             android:label="Your App Name"
             android:icon="@drawable/your_icon"
             android:theme="@style/Theme.MaterialComponents.Light.NoActionBar"> // Or your theme inheriting from Material Design
```

*We don't support Dark Mode at the moment*

### Button styling example
If any of your components doesn't look and feel as your app's theme you can customize it creating a style and setting it in your theme.

`styles.xml`
``` xml
<style name="Widget.YourAppName.Button" parent="@style/Widget.MaterialComponents.Button.UnelevatedButton">
  <item name="iconPadding">0dp</item>
  <item name="android:insetTop">0dp</item>
  <item name="android:insetBottom">0dp</item>
</style>
```
`theme.xml`
``` xml
<style name="Theme.YourAppName" parent="Theme.MaterialComponents.Light.NoActionBar">
   <item name="materialButtonStyle">@style/Widget.YourAppName.Button</item>
</style>
```
For more information on how Material Design works and how to customize views please refer to their [site](https://material.io/develop/android).

## Icons and images customization
You can replace icons and images that suits your application theme.

In your theme's xml add the following tags with your own images
```xml
<style name="Theme.YourAppName" parent="Theme.MaterialComponents.Light.NoActionBar">
  <item name="pomeloFeedbackErrorDrawable">@drawable/your_error_image</item>
  <item name="pomeloFeedbackInfoDrawable">@drawable/your_info_image</item>
  <item name="pomeloFeedbackSuccessDrawable">@drawable/your_success_image</item>
  <item name="pomeloFeedbackWaitingDrawable">@drawable/your_waiting_image</item>
  <item name="pomeloFeedbackWarningDrawable">@drawable/your_warning_image</item>
  <item name="pomeloIdentityCameraDrawable">@drawable/your_camera_image</item>
  <item name="pomeloIdentityCardIdDrawable">@drawable/your_card_id_image</item>
  <item name="pomeloIdentityCameraFlashActiveDrawable">@drawable/your_camera_flash_active_icon</item>
  <item name="pomeloIdentityCameraFlashInactiveDrawable">@drawable/your_camera_flash_inactive_icon</item>
</style>
```

&nbsp;
&nbsp;
# Release Notes
For a full list of versions refeer to this [link](https://github.com/pomelo-la/android-public/packages/1641499/versions)
## 1.1.0
##### Date 29/NOV/2022
### Overview
### Fixes
UI Fixes
### Improvements
Update 3rd party libraries version

## 1.0.0
##### Date 5/OCT/2022
### Overview
Initial version of Pomelo Identity SDK for Android.
Only for Argentina 🇦🇷
### Fixes
### Improvements
